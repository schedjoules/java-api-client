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

package com.schedjoules.client.http;

import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.HttpHeaders;

import java.io.IOException;
import java.net.URI;


/**
 * An {@link HttpResponseHandler} that returns the target of an HTTP redirect. Make sure that the response is actually a redirect response before using this
 * {@link HttpResponseHandler}.
 *
 * @author Marten Gajda
 */
public final class RedirectUrlResponseHandler implements HttpResponseHandler<URI>
{
    @Override
    public URI handleResponse(final HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        // this assumes the response has already been verified to be a redirect response
        return response.headers().header(HttpHeaders.LOCATION).value();
    }
}
