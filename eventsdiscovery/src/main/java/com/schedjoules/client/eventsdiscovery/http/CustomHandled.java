/*
 * Copyright 2017 SchedJoules
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
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.Headers;

import java.io.IOException;


/**
 * An {@link HttpRequest} decorator that overrides the {@link HttpResponseHandler} of the decorated {@link HttpRequest}.
 *
 * @author Marten Gajda
 */
public final class CustomHandled<T> implements HttpRequest<T>
{
    private final HttpRequest<T> mDelegate;
    private final HttpResponseHandler<T> mResponseHandler;


    public CustomHandled(HttpRequest<T> delegate, HttpResponseHandler<T> responseHandler)
    {
        mDelegate = delegate;
        mResponseHandler = responseHandler;
    }


    @Override
    public HttpMethod method()
    {
        return mDelegate.method();
    }


    @Override
    public Headers headers()
    {
        return mDelegate.headers();
    }


    @Override
    public HttpRequestEntity requestEntity()
    {
        return mDelegate.requestEntity();
    }


    @Override
    public HttpResponseHandler<T> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        return mResponseHandler;
    }
}
