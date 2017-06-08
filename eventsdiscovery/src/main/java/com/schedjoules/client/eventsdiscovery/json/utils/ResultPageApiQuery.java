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

package com.schedjoules.client.eventsdiscovery.json.utils;

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.State;
import com.schedjoules.client.eventsdiscovery.Envelope;
import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.ResultPage;
import com.schedjoules.client.eventsdiscovery.http.GetRequest;
import com.schedjoules.client.eventsdiscovery.http.MultiEventsResponseHandler;
import com.schedjoules.client.utils.ApiVersionHeaders;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.types.Link;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * An {@link ApiQuery} for the previous or next page identified by a {@link Link}.
 *
 * @author Marten Gajda
 */
final class ResultPageApiQuery implements ApiQuery<ResultPage<Envelope<Event>>>
{
    private final static String API_VERSION = "1";

    private final Link mLink;


    public ResultPageApiQuery(Link link)
    {
        mLink = link;
    }


    @Override
    public ResultPage<Envelope<Event>> queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException
    {
        return api.queryResult(new URI(null, null, mLink.target().getPath(), mLink.target().getQuery(), null),
                new GetRequest<>(new ApiVersionHeaders(API_VERSION), new MultiEventsResponseHandler()));
    }


    @Override
    public State<ApiQuery<ResultPage<Envelope<Event>>>> serializable()
    {
        try
        {
            return new ResultPageState(new URI(null, null, mLink.target().getPath(), mLink.target().getQuery(), null));
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * The {@link State} of an {@link ApiQuery} for a {@link ResultPage}.
     */
    private final static class ResultPageState implements State<ApiQuery<ResultPage<Envelope<Event>>>>
    {
        private final URI mPageUri;


        private ResultPageState(URI pageUri)
        {
            mPageUri = pageUri;
        }


        @Override
        public ApiQuery<ResultPage<Envelope<Event>>> restored()
        {
            return new ApiQuery<ResultPage<Envelope<Event>>>()
            {
                @Override
                public ResultPage<Envelope<Event>> queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException
                {
                    return api.queryResult(mPageUri, new GetRequest<>(new ApiVersionHeaders(API_VERSION), new MultiEventsResponseHandler()));
                }


                @Override
                public State<ApiQuery<ResultPage<Envelope<Event>>>> serializable()
                {
                    return ResultPageState.this;
                }
            };
        }
    }
}
