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
package com.xixicm.ca.domain.usecase;

import com.xixicm.ca.domain.scheduler.RxUseCaseSchedulers;
import com.xixicm.ca.domain.util.Preconditions;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @author mc
 */
public abstract class AbstractRxUseCase<Request, Response> implements RxUseCase<Request, Response> {
    private Request mRequestValue;
    private DisposableObserver<Response> mObserver;
    private RxUseCaseSchedulers mSchedulers;
    private CompositeDisposable mDisposables;

    /**
     * set the request params.
     *
     * @param requestParams
     * @return
     */
    @Override
    public RxUseCase<Request, Response> requestParams(Request requestParams) {
        mRequestValue = requestParams;
        return this;
    }

    @Override
    public Request getRequestParams() {
        return mRequestValue;
    }

    /**
     * Set the observer.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     *                 by {@link #buildObservable(Request)} ()} method.
     * @return
     */
    @Override
    public RxUseCase<Request, Response> setObserver(DisposableObserver<Response> observer) {
        mObserver = observer;
        return this;
    }

    @Override
    public Observer<Response> getObserver() {
        return mObserver;
    }

    @Override
    public RxUseCase<Request, Response> scheduler(RxUseCaseSchedulers schedulers) {
        mSchedulers = schedulers;
        return this;
    }

    @Override
    public final Observable<Response> buildObservable(Request params) {
        requestParams(params);
        return buildObservable();
    }

    public abstract Observable<Response> buildObservable();

    @Override
    public RxUseCase<Request, Response> execute() {
        return execute(mSchedulers);
    }

    @Override
    public RxUseCase<Request, Response> execute(RxUseCaseSchedulers schedulers) {
        return execute(schedulers, mRequestValue);
    }

    @Override
    public RxUseCase<Request, Response> execute(RxUseCaseSchedulers schedulers, Request requestValue) {
        return execute(schedulers, requestValue, mObserver);
    }

    @Override
    public RxUseCase<Request, Response> execute(RxUseCaseSchedulers schedulers, Request requestValue, DisposableObserver<Response> observer) {
        Preconditions.checkNotNull(observer);
        setObserver(observer);
        scheduler(schedulers);
        Observable<Response> observable = buildObservable(requestValue);
        Preconditions.checkNotNull(observable);
        if (schedulers != null) {
            if (schedulers.subscribeOn() != null) {
                observable.subscribeOn(schedulers.subscribeOn());
            }
            if (schedulers.observeOn() != null) {
                observable.observeOn(schedulers.observeOn());
            }
        }
        addDisposable(observable.subscribeWith(observer));
        return this;
    }

    /**
     * add to {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    /**
     * Just dispose the DisposableObserver. For special use case, can do more cancel work.
     */
    @Override
    public void cancel() {
        if (mDisposables != null) {
            mDisposables.dispose();
            mDisposables = null;
        }
    }
}
