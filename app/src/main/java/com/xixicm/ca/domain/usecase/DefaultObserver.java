package com.xixicm.ca.domain.usecase;

/**
 * Created by mc on 2018/4/9.
 */

import io.reactivex.observers.DisposableObserver;

/**
 * Default {@link DisposableObserver} base class.
 */
public class DefaultObserver<T> extends DisposableObserver<T> {
    @Override
    public void onNext(T t) {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable exception) {
    }
}
