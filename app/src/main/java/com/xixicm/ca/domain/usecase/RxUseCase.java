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

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;

/**
 * Rx type UseCase.
 * Please note: RxUseCase is not thread safe. When reusing RxUseCase,
 * should consider the thread issue of same Observer.
 * Best practice: 1. Dispose and reset a new or the Observer for a re-used RxUseCase
 * 2. Or always create new RxUseCase.
 *
 * @param <Request>  Data passed to a request.
 * @param <Response> Data received from a request.
 * @param <Error>    Data received Error.
 * @author mc
 */
public interface RxUseCase<Request, Response> extends UC {
    RxUseCase<Request, Response> requestParams(Request requestParams);

    Request getRequestParams();

    RxUseCase<Request, Response> setObserver(DisposableObserver<Response> observer);

    Observer<Response> getObserver();

    RxUseCase<Request, Response> scheduler(RxUseCaseSchedulers schedulers);

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    Observable<Response> buildObservable(Request params);

    RxUseCase<Request, Response> execute();

    RxUseCase<Request, Response> execute(RxUseCaseSchedulers schedulers);

    RxUseCase<Request, Response> execute(RxUseCaseSchedulers schedulers, Request requestValue);

    RxUseCase<Request, Response> execute(RxUseCaseSchedulers schedulers, Request requestValue, DisposableObserver<Response> observer);

    /**
     * Cancel the use case. Callback will never be invoked if have not gotten response.
     */
    void cancel();
}
