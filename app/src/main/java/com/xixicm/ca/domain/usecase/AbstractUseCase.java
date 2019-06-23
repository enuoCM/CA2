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

import com.xixicm.ca.domain.handler.Handlers;
import com.xixicm.ca.domain.handler.UseCaseHandler;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author mc
 */
public abstract class AbstractUseCase<Request, Response, Error> implements UseCase<Request, Response, Error> {
    private Request mRequestValue;
    private UseCaseCallback<Response, Error> mUseCaseCallback;
    private UseCaseHandler mUseCaseHandler;
    private AtomicReference<Boolean> mIsCancelled = new AtomicReference<Boolean>(false);

    /**
     * set the request params.
     *
     * @param requestValue
     * @return
     */
    @Override
    public AbstractUseCase<Request, Response, Error> setRequestValue(Request requestValue) {
        mRequestValue = requestValue;
        return this;
    }

    /**
     * same as {@link #setRequestValue}.
     *
     * @param requestValue
     * @return
     */
    public AbstractUseCase<Request, Response, Error> requestParams(Request requestValue) {
        return setRequestValue(requestValue);
    }

    @Override
    public Request getRequestValue() {
        return mRequestValue;
    }

    /**
     * Set the callback.
     *
     * @param useCaseCallback
     * @return
     */
    @Override
    public AbstractUseCase<Request, Response, Error> setUseCaseCallback(UseCaseCallback<Response, Error> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
        return this;
    }

    /**
     * same as {@link #setUseCaseCallback}
     *
     * @param useCaseCallback
     * @return
     */
    public AbstractUseCase<Request, Response, Error> callback(UseCaseCallback<Response, Error> useCaseCallback) {
        return setUseCaseCallback(useCaseCallback);
    }

    @Override
    public UseCaseCallback<Response, Error> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    /**
     * Set the use case handler. If handler is null, using default  {@link Handlers#asyncParallelReqSyncRes}
     *
     * @param handler {@link Handlers#asyncSerialReqSyncRes}
     *                {@link Handlers#asyncParallelReqSyncRes}
     *                {@link Handlers#asyncParallelReqSyncRes}
     *                {@link com.xixicm.ca.presentation.handler.AndroidHandlers#asyncParallelReqSyncRes}
     *                {@link com.xixicm.ca.presentation.handler.AndroidHandlers#asyncSerialReqSyncRes()}
     * @return
     */
    @Override
    public UseCase<Request, Response, Error> handler(UseCaseHandler handler) {
        if (handler == null) {
            handler = Handlers.asyncParallelReqSyncRes();
        }
        mUseCaseHandler = handler;
        return this;
    }

    @Override
    public UseCase<Request, Response, Error> execute() {
        return execute(mUseCaseHandler);
    }

    @Override
    public UseCase<Request, Response, Error> execute(UseCaseHandler handler) {
        return execute(handler, mRequestValue);
    }

    @Override
    public UseCase<Request, Response, Error> execute(UseCaseHandler handler, Request requestValue) {
        return execute(handler, requestValue, mUseCaseCallback);
    }

    @Override
    public UseCase<Request, Response, Error> execute(UseCaseHandler handler, Request requestValue, UseCaseCallback<Response, Error> useCaseCallback) {
        handler(handler);
        requestParams(requestValue);
        callback(useCaseCallback);
        handler.execute(this);
        return this;
    }

    /**
     * Just mark as cancelled. For special use case, can do more cancel work.
     */
    @Override
    public void cancel() {
        mIsCancelled.set(Boolean.TRUE);
    }

    @Override
    public boolean isCancelled() {
        return mIsCancelled.get();
    }
}
