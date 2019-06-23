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
package com.xixicm.ca.presentation.handler;

/**
 * Created by min.chen on 2017/6/30.
 */

import com.xixicm.ca.domain.handler.DefaultUseCaseHandler;
import com.xixicm.ca.domain.handler.UseCaseHandler;
import com.xixicm.ca.domain.scheduler.UseCaseAsyncRequestScheduler;
import com.xixicm.ca.presentation.scheduler.UseCaseUIResponseScheduler;

/**
 * @author mc
 */
public class AndroidHandlers {
    private static UseCaseHandler sAsyncSerialReqUIRes;
    private static UseCaseHandler sAsyncParallelReqUIRes;

    /**
     * Request in serial background thread and response on UI main thread.
     *
     * @return
     */
    public static UseCaseHandler asyncSerialReqSyncRes() {
        if (sAsyncSerialReqUIRes == null) {
            sAsyncSerialReqUIRes = new DefaultUseCaseHandler(UseCaseAsyncRequestScheduler.getSerialInstance(), UseCaseUIResponseScheduler.getInstance());
        }
        return sAsyncSerialReqUIRes;
    }

    /**
     * Request in serial background thread and response on UI main thread.
     *
     * @return
     */
    public static UseCaseHandler asyncParallelReqSyncRes() {
        if (sAsyncParallelReqUIRes == null) {
            sAsyncParallelReqUIRes = new DefaultUseCaseHandler(UseCaseAsyncRequestScheduler.getParallelInstance(), UseCaseUIResponseScheduler.getInstance());
        }
        return sAsyncParallelReqUIRes;
    }
}
