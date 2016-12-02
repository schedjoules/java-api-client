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

package com.schedjoules.client.http;

import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseEntity;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.converters.PlainStringHeaderConverter;
import org.dmfs.httpessentials.converters.QuotedStringConverter;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.BasicListHeaderType;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.headers.ListHeaderType;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link HttpRequest} decorator to add transparent <code>gzip</code> compression to the response.
 * <p/>
 * Note: This is not ideal in that it assumes that only one content-encoding is applied on the server response or the server applies the same order that we
 * provided. If the client adds more accepted encodings and the server applies multiple encodings this might fail, since In that case the encodings must be
 * decoded in reverse order. However, in practice this is unlikely to be a problem.
 * <p/>
 * TODO: add a more sophisticated transparent decoding mechanism.
 *
 * @param <T>
 *         The expected type of the response.
 *
 * @author Marten Gajda
 */
public final class GzipRequest<T> implements HttpRequest<T>
{
    /*
     * temporary workaround. HttpHeaders#ACCEPT_ENCODING appears to be broken as is always quotes tokens
     */
    private final static ListHeaderType<String> ACCEPT_ENCODING = new BasicListHeaderType<>("accept-encoding",
            PlainStringHeaderConverter.INSTANCE);

    /*
     * temporary workaround. HttpUrlConnection headers are not case agnostic and HttpUrlConnectionExecutor's are neither, so we have to be specific about the case
     */
    private final static ListHeaderType<String> CONTENT_ENCODING = new BasicListHeaderType<>("Content-Encoding",
            QuotedStringConverter.INSTANCE);

    private final HttpRequest<T> mDecorated;


    /**
     * Add transparent gzip compression to the given request.
     *
     * @param decorated
     *         The decorated {@link HttpRequest}.
     */
    public GzipRequest(final HttpRequest<T> decorated)
    {
        mDecorated = decorated;
    }


    @Override
    public HttpMethod method()
    {
        return mDecorated.method();
    }


    @Override
    public Headers headers()
    {
        // add an accept-encoding header.
        // TODO: check if there already is an accept-encoding header and append gzip if it's not in the value
        return mDecorated.headers().withHeader(ACCEPT_ENCODING.entity(Collections.singletonList("gzip")));
    }


    @Override
    public HttpRequestEntity requestEntity()
    {
        // return the original request
        return mDecorated.requestEntity();
    }


    @Override
    public HttpResponseHandler<T> responseHandler(final HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        final Headers responseHeaders = response.headers();

        if (!responseHeaders.contains(CONTENT_ENCODING))
        {
            // no content-encoding, pass the original response to the handler
            return mDecorated.responseHandler(response);
        }

        List<String> encodings = responseHeaders.header(CONTENT_ENCODING).value();

        if (encodings.isEmpty() || !encodings.contains("gzip"))
        {
            // not gzipped, pass the original response to the handler
            return mDecorated.responseHandler(response);
        }

        if (!"gzip".equals(encodings.get(encodings.size() - 1)))
        {
            // gzip is not the last encoding that has been applied, we can't decode the response
            throw new IOException(String.format("can't decode encoded gzip stream: content-encoding=%s", responseHeaders.header(CONTENT_ENCODING)
                    .toString()));
        }

        // decorate the response handler with something that passes the unzipped response to the decorated one
        return new HttpResponseHandler<T>()
        {
            @Override
            public T handleResponse(final HttpResponse response) throws IOException, ProtocolError, ProtocolException
            {
                // the content is gzipped, create an HttpResponse that unzips the content
                final HttpResponse unzippedResponse = new UnzippedHttpResponse(response);
                return mDecorated.responseHandler(unzippedResponse).handleResponse(unzippedResponse);
            }
        };
    }


    private static class UnzippedHttpResponse implements HttpResponse
    {

        private final HttpResponse mResponse;


        public UnzippedHttpResponse(HttpResponse response)
        {
            mResponse = response;
        }


        @Override
        public HttpStatus status()
        {
            // return the original status
            return mResponse.status();
        }


        @Override
        public URI responseUri()
        {
            // return the original response URI
            return mResponse.responseUri();
        }


        @Override
        public HttpResponseEntity responseEntity()
        {
            // return an entity that unzips the original entity
            return new GzippedResponseEntity(mResponse.responseEntity());
        }


        @Override
        public URI requestUri()
        {
            // return the original request URI
            return mResponse.requestUri();
        }


        @Override
        public Headers headers()
        {
            Headers headers = mResponse.headers();

            // no need to perform the sanity checks again
            List<String> encodings = headers.header(CONTENT_ENCODING).value();

            // return the modified headers
            return headers.withHeader(CONTENT_ENCODING.entity(encodings.subList(0, encodings.size() - 1)));
        }
    }
}
