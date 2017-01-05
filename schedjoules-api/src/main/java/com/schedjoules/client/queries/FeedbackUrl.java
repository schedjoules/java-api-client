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

package com.schedjoules.client.queries;

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.http.RedirectUrlResponseHandler;
import com.schedjoules.client.utils.ApiVersionHeaders;
import com.schedjoules.client.utils.MapQueryString;
import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.entities.EmptyHttpRequestEntity;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.Headers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * An {@link ApiQuery} to retrieve the feedback URL of the current client + Locale.
 *
 * @author Marten Gajda
 */
public final class FeedbackUrl implements ApiQuery<URI>
{
    private final static String QUERY_PATH = "/feedback";
    private final static String API_VERSION = "1";

    private final Locale mLocale;


    public FeedbackUrl()
    {
        this(Locale.getDefault());
    }


    public FeedbackUrl(Locale locale)
    {
        mLocale = locale;
    }


    @Override
    public URI queryResult(Api api) throws IOException, ProtocolError, ProtocolException
    {
        Map<String, String> paramMap = new HashMap<>(4);
        if (mLocale.getCountry() != null)
        {
            paramMap.put("location", mLocale.getCountry());
        }
        if (mLocale.getLanguage() != null)
        {
            paramMap.put("locale", mLocale.getLanguage());
        }
        try
        {
            return api.queryResult(
                    new URI(null, null, QUERY_PATH, new MapQueryString(paramMap).toString(), null),
                    new HttpRequest<URI>()
                    {
                        @Override
                        public HttpMethod method()
                        {
                            return HttpMethod.GET;
                        }


                        @Override
                        public Headers headers()
                        {
                            // TODO: add a header that instructs any `Following` decorator not to follow redirects
                            return new ApiVersionHeaders(API_VERSION);
                        }


                        @Override
                        public HttpRequestEntity requestEntity()
                        {
                            return EmptyHttpRequestEntity.INSTANCE;
                        }


                        @Override
                        public HttpResponseHandler<URI> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
                        {
                            if (!response.status().isRedirect())
                            {
                                throw new ProtocolException(String.format("Redirect expected but got status %s", response.status().toString()));
                            }
                            return new RedirectUrlResponseHandler();
                        }
                    });
        }
        catch (URISyntaxException e)
        {
            throw new ProtocolException(String.format("Can't build request URI."), e);
        }
    }
}
