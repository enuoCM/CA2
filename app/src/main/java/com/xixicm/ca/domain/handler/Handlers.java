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
package com.xixicm.ca.domain.handler;

/**
 * @author mc
 */
public class Handlers {
    private static UseCaseHandler sAsyncSerialReqSyncRes;
    private static UseCaseHandler sAsyncParallelReqSyncRes;
    private static UseCaseHandler sSyncReqSyncRes;

    /**
     * Request in serial background thread and response on the same background thread
     *
     * @return
     */
    public static UseCaseHandler asyncSerialReqSyncRes() {
        if (sAsyncSerialReqSyncRes == null) {
            sAsyncSerialReqSyncRes = DefaultUseCaseHandler.createSerialUCHandler();
        }
        return sAsyncSerialReqSyncRes;
    }

    /**
     * Request in parallel background thread and response on the same background thread
     *
     * @return
     */
    public static UseCaseHandler asyncParallelReqSyncRes() {
        if (sAsyncParallelReqSyncRes == null) {
            sAsyncParallelReqSyncRes = DefaultUseCaseHandler.createParallelUCHandler();
        }
        return sAsyncParallelReqSyncRes;
    }

    /**
     * Request in current thread and response on current thread
     *
     * @return
     */
    public static UseCaseHandler syncReqSyncRes() {
        if (sSyncReqSyncRes == null) {
            sSyncReqSyncRes = DefaultUseCaseHandler.createSyncUCHandler();
        }
        return sSyncReqSyncRes;
    }
}
