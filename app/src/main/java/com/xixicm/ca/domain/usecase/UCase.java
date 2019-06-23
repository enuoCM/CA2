package com.xixicm.ca.domain.usecase;

/**
 * single-threaded type UseCase.
 *
 * @param <Request>  Data passed to a request.
 * @param <Response> Data received from a request.
 * @param <Error>    Data received Error.
 * @author mc
 */
public interface UCase<Request, Response, Error extends Throwable> extends UC {
    Response execute(Request param) throws Error;

    Response execute() throws Error;

    UCase<Request, Response, Error> requestParams(Request requestParams);

    Request getRequestParams();
}
