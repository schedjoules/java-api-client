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

package com.schedjoules.client.actions.queries;

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.eventsdiscovery.json.JsonArrayIterator;
import com.schedjoules.client.eventsdiscovery.json.JsonLink;
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
import org.dmfs.httpessentials.methods.Method;
import org.dmfs.httpessentials.responsehandlers.FailResponseHandler;
import org.dmfs.httpessentials.responsehandlers.StringResponseHandler;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.EmptyIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;


/**
 * A SchedJoules API query to retrieve a set of action links that are available for an event.
 *
 * @author Marten Gajda
 */
public final class ActionsQuery implements ApiQuery<Iterator<Link>>
{
    private final static String PATH_TEMPLATE = "/events/%s/actions";

    private final String mEventId;


    /**
     * Creates an {@link ActionsQuery} for the event with the given event id.
     *
     * @param eventId
     */
    public ActionsQuery(String eventId)
    {
        mEventId = eventId;
    }


    @Override
    public Iterator<Link> queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException
    {
        return api.queryResult(new URI(null, null, String.format(PATH_TEMPLATE, mEventId), null, null), new HttpRequest<Iterator<Link>>()
        {
            @Override
            public HttpMethod method()
            {
                return Method.GET;
            }


            @Override
            public Headers headers()
            {
                return EmptyHeaders.INSTANCE;
            }


            @Override
            public HttpRequestEntity requestEntity()
            {
                return EmptyHttpRequestEntity.INSTANCE;
            }


            @Override
            public HttpResponseHandler<Iterator<Link>> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
            {
                if (!HttpStatus.OK.equals(response.status()))
                {
                    return FailResponseHandler.getInstance();
                }
                return new HttpResponseHandler<Iterator<Link>>()
                {
                    @Override
                    public Iterator<Link> handleResponse(final HttpResponse response) throws IOException, ProtocolError, ProtocolException
                    {
                        final JSONArray linksArray = new JSONObject(new StringResponseHandler().handleResponse(response)).optJSONArray("links");
                        return linksArray == null ?
                                EmptyIterator.<Link>instance() :
                                new ConvertedIterator<>(
                                        new JsonArrayIterator(linksArray),
                                        new AbstractConvertedIterator.Converter<Link, JSONObject>()
                                        {
                                            @Override
                                            public Link convert(JSONObject element)
                                            {
                                                return new JsonLink(element);
                                            }
                                        });
                    }
                };
            }
        });
    }
}
