/*
 * Copyright (C) 2016 mc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xixicm.ca.presentation.mvp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * An Activity that uses an {@link MvpPresenter} to implement a Model-View-Presenter architecture.
 *
 * @author mc
 */
public abstract class MvpActivity<M, V extends MvpView, P extends MvpPresenter<V, M>> extends AppCompatActivity implements MvpView {
    protected P mPresenter;
    private static String VIEW_MODE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VIEW_MODE_KEY = getClass().getCanonicalName() + "$ViewModel";
        onCreatePresenter(savedInstanceState);
        onInitializePresenter(savedInstanceState);
    }

    /**
     * Called in {@link #onCreate} before {@link #onInitializePresenter}
     *
     * @param savedInstanceState
     */
    protected void onCreatePresenter(Bundle savedInstanceState) {
        if (isRetainPresenterByNonConfigurationInstance()) {
            mPresenter = getPresenterFromNonConfigurationInstance();
        }
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
    }

    /**
     * Called in {@link #onCreate} after {@link #onCreatePresenter}
     *
     * @param savedInstanceState
     */
    protected void onInitializePresenter(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mPresenter.attachView((V) this, null);
        } else {
            M viewModel;
            if (mPresenter.getViewModel() != null) {
                // no need to restore from the savedInstanceState, if mPresenter.getViewModel() is not null.
                viewModel = mPresenter.getViewModel();
            } else {
                viewModel = (M) savedInstanceState.get(VIEW_MODE_KEY);
            }
            mPresenter.attachView((V) this, viewModel);
        }
    }

    /**
     * Whether retain {@link #mPresenter} by NonConfigurationInstance.
     * If return true, should override {@link #getPresenterFromNonConfigurationInstance()} to get {@link #mPresenter} if need.
     * If return false,  {@link #mPresenter} will be destroyed when this activity {@link #onDestroy}
     *
     * @return default true
     */
    protected boolean isRetainPresenterByNonConfigurationInstance() {
        return true;
    }

    /**
     * If {@link #isRetainPresenterByNonConfigurationInstance} return true and {@link #getLastCustomNonConfigurationInstance} doesn't return mPresenter directly,
     * should override this function to get {@link #mPresenter}.
     *
     * @return {@link #mPresenter} or null
     */
    @Nullable
    protected P getPresenterFromNonConfigurationInstance() {
        if (mPresenter != null) {
            // this only happens when getPresenter() is called before MvpActivity#onCreatePresenter
            return mPresenter;
        }
        Object o = getLastCustomNonConfigurationInstance();
        if (o instanceof MvpPresenter) {
            //noinspection unchecked
            return (P) o;
        }
        return null;
    }

    /**
     * Default to return the {@link #mPresenter}.
     *
     * @return
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Even it's isRetainPresenterByNonConfigurationInstance, still save to outState.
        // For runtime permission change or kill process case. RetainNonConfigurationInstance doesn't work.
        M viewModel = mPresenter.getViewModel();
        if (viewModel != null) {
            if (viewModel instanceof Serializable) {
                outState.putSerializable(VIEW_MODE_KEY, (Serializable) viewModel);
            } else if (viewModel instanceof Parcelable) {
                outState.putParcelable(VIEW_MODE_KEY, (Parcelable) viewModel);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (!isRetainPresenterByNonConfigurationInstance() || null == getPresenterFromNonConfigurationInstance()) {
            mPresenter.onDestroy();
        } else if (isFinishing()) {
            mPresenter.onDestroy();
        }
    }

    @NonNull
    protected abstract P createPresenter();

    /**
     * Get {@link #mPresenter}. If mPresenter is null, try to get it from {@link #getPresenterFromNonConfigurationInstance} first,
     * if it's till null, create it by {@link #createPresenter}, otherwise return it directly
     *
     * @return
     */
    @NonNull
    public P getPresenter() {
        if (mPresenter == null) {
            // when recreate the whole activity(restore the process), this function will be called from sub fragment
            // before creating the presenter on the onCreate of the activity. Usually getPresenterFromNonConfigurationInstance() is null,
            // but if it's not null, keep to use it first.
            P presenter = getPresenterFromNonConfigurationInstance();
            if (presenter != null) {
                mPresenter = presenter;
            } else {
                mPresenter = createPresenter();
            }
        }
        return mPresenter;
    }
}
