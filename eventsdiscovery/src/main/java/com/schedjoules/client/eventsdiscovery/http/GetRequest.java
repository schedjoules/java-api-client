/*
 * Copyright 2016 SchedJoules
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.schedjoules.client.eventsdiscovery.http;

import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.entities.EmptyHttpRequestEntity;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.EmptyHeaders;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.responsehandlers.FailResponseHandler;

import java.io.IOException;


/**
 * A generic {@link HttpMethod#GET} request. It takes an {@link HttpResponseHandler} to handle the actual response.
 *
 * @author Marten Gajda
 */
public final class GetRequest<T> implements HttpRequest<T>
{
    private final Headers mHeaders;
    private final HttpResponseHandler<T> mResponseHandler;


    public GetRequest(HttpResponseHandler<T> responseHandler)
    {
        this(EmptyHeaders.INSTANCE, responseHandler);
    }


    public GetRequest(Headers headers, HttpResponseHandler<T> responseHandler)
    {
        mHeaders = headers;
        mResponseHandler = responseHandler;
    }


    @Override
    public HttpMethod method()
    {
        return HttpMethod.GET;
    }


    @Override
    public Headers headers()
    {
        return mHeaders;
    }


    @Override
    public HttpRequestEntity requestEntity()
    {
        return EmptyHttpRequestEntity.INSTANCE;
    }


    @Override
    public HttpResponseHandler<T> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        if (!HttpStatus.OK.equals(response.status()))
        {
            return FailResponseHandler.getInstance();
        }
        return mResponseHandler;
    }
}
