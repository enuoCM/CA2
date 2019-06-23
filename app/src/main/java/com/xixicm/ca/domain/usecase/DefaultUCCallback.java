package com.xixicm.ca.domain.usecase;

/**
 * Created by mc on 2018/4/9.
 */

public class DefaultUCCallback<Request, Response> implements UseCase.UseCaseCallback<Request, Response> {

    @Override
    public void onSuccess(Request request) {

    }

    @Override
    public void onError(Response response) {

    }
}