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

package com.schedjoules.client.eventsdiscovery.queries;

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.eventsdiscovery.Envelope;
import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.http.GetRequest;
import com.schedjoules.client.eventsdiscovery.http.SingleEventResponseHandler;
import com.schedjoules.client.utils.ApiVersionHeaders;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.types.Token;

import java.io.IOException;
import java.net.URI;


/**
 * An {@link ApiQuery} to retrieve a single event by its UID.
 *
 * @author Marten Gajda
 */
public final class EventByUid implements ApiQuery<Envelope<Event>>
{
    private final static String QUERY_PATH = "/events/";
    private final static String API_VERSION = "1";

    private final Token mUid;


    private EventByUid(Token uid)
    {
        mUid = uid;
    }


    @Override
    public Envelope<Event> queryResult(Api api) throws IOException, ProtocolError, ProtocolException
    {
        return api.queryResult(URI.create(QUERY_PATH).resolve(mUid.toString()),
                new GetRequest<>(new ApiVersionHeaders(API_VERSION), new SingleEventResponseHandler()));
    }
}
