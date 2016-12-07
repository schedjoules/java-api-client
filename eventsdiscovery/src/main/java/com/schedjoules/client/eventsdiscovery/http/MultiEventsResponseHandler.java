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

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.eventsdiscovery.Envelope;
import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.ResultPage;
import com.schedjoules.client.eventsdiscovery.json.JsonArrayIterator;
import com.schedjoules.client.eventsdiscovery.json.JsonEnvelope;
import com.schedjoules.client.eventsdiscovery.json.JsonEventFactory;
import com.schedjoules.client.utils.ApiVersionHeaders;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.headers.HttpHeaders;
import org.dmfs.httpessentials.responsehandlers.StringResponseHandler;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.AbstractFilteredIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.EmptyIterator;
import org.dmfs.iterators.FilteredIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;


/**
 * An {@link HttpResponseHandler} that parses a JSON response containing {@link Envelope} objects.
 *
 * @author Marten Gajda
 */
public final class MultiEventsResponseHandler implements HttpResponseHandler<ResultPage<Envelope<Event>>>
{
    private final static String API_VERSION = "1";


    @Override
    public ResultPage<Envelope<Event>> handleResponse(final HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        final JSONArray jsonArray = new JSONArray(new StringResponseHandler().handleResponse(response));

        return new ResultPage<Envelope<Event>>()
        {
            @Override
            public Iterable<Envelope<Event>> items()
            {
                return new Iterable<Envelope<Event>>()
                {
                    @Override
                    public Iterator<Envelope<Event>> iterator()
                    {
                        return new ConvertedIterator<>(new JsonArrayIterator(jsonArray), new AbstractConvertedIterator.Converter<Envelope<Event>, JSONObject>()
                        {
                            @Override
                            public Envelope<Event> convert(JSONObject jsonObject)
                            {
                                return new JsonEnvelope<>(jsonObject, new JsonEventFactory());
                            }
                        });
                    }
                };
            }


            @Override
            public boolean isFirstPage()
            {
                return !linkWithRel("prev").hasNext();
            }


            @Override
            public boolean isLastPage()
            {
                return !linkWithRel("next").hasNext();
            }


            @Override
            public ApiQuery<ResultPage<Envelope<Event>>> previousPageQuery() throws IllegalStateException
            {
                return queryLinkedPage("prev");
            }


            @Override
            public ApiQuery<ResultPage<Envelope<Event>>> nextPageQuery() throws IllegalStateException
            {
                return queryLinkedPage("next");
            }


            private Iterator<Link> linkWithRel(final String rel)
            {
                Headers headers = response.headers();
                if (!headers.contains(HttpHeaders.LINK))
                {
                    return EmptyIterator.instance();
                }
                return new FilteredIterator<>(headers.header(HttpHeaders.LINK).value().iterator(),
                        new AbstractFilteredIterator.IteratorFilter<Link>()
                        {
                            @Override
                            public boolean iterate(Link element)
                            {
                                return element.relationTypes().contains(rel);
                            }
                        });
            }


            private ApiQuery<ResultPage<Envelope<Event>>> queryLinkedPage(String rel) throws IllegalStateException
            {
                Iterator<Link> links = linkWithRel(rel);
                if (!links.hasNext())
                {
                    throw new IllegalStateException(String.format("Response didn't contain %s page.", rel));
                }

                final Link link = links.next();

                return new ApiQuery<ResultPage<Envelope<Event>>>()
                {
                    @Override
                    public ResultPage<Envelope<Event>> queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException
                    {
                        return api.queryResult(new URI(null, null, link.target().getPath(), link.target().getQuery(), null),
                                new GetRequest<>(new ApiVersionHeaders(API_VERSION), new MultiEventsResponseHandler()));
                    }
                };
            }

        };
    }
}
