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
import com.schedjoules.client.actions.http.LinksArrayResponseHandler;
import com.schedjoules.client.eventsdiscovery.http.GetRequest;
import com.schedjoules.client.utils.ApiVersionHeaders;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.types.Link;

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
    private final static String API_VERSION = "1";

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
        return api.queryResult(new URI(null, null, String.format(PATH_TEMPLATE, mEventId), null, null),
                new GetRequest<>(new ApiVersionHeaders(API_VERSION), new LinksArrayResponseHandler()));
    }

}
