package com.xixicm.ca.domain.usecase;

public abstract class AbstractUCase<Request, Response, Error extends Throwable> implements UCase<Request, Response, Error> {
    private Request mRequestValue;

    /**
     * set the request params.
     *
     * @param requestParams
     * @return
     */
    @Override
    public AbstractUCase<Request, Response, Error> requestParams(Request requestParams) {
        mRequestValue = requestParams;
        return this;
    }

    @Override
    public Request getRequestParams() {
        return mRequestValue;
    }

    @Override
    public Response execute() throws Error {
        return execute(mRequestValue);
    }
}
