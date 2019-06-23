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
package com.xixicm.ca.domain.scheduler;

import io.reactivex.Observer;
import io.reactivex.Scheduler;

/**
 * Schedulers for Observable and Observers
 *
 * @author mc
 */
public interface RxUseCaseSchedulers {
    /**
     * Return the specified {@link Scheduler} that the ObservableSource to perform its emissions and notifications on
     *
     * @return the {@link Scheduler} to notify {@link Observer}s on
     */
    Scheduler observeOn();

    /**
     * Return the specified {@link Scheduler} that asynchronously subscribes Observers to this ObservableSource on .
     *
     * @return the {@link Scheduler} to perform subscription actions on
     */
    Scheduler subscribeOn();
}
