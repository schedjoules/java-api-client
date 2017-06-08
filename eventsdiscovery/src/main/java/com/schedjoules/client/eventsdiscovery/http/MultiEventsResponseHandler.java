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

import com.schedjoules.client.eventsdiscovery.Envelope;
import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.ResultPage;
import com.schedjoules.client.eventsdiscovery.json.utils.JsonArrayResultPage;
import com.schedjoules.client.eventsdiscovery.json.utils.ResponseLinks;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.responsehandlers.StringResponseHandler;
import org.json.JSONArray;

import java.io.IOException;


/**
 * An {@link HttpResponseHandler} that parses a JSON response containing {@link Envelope} objects.
 *
 * @author Marten Gajda
 */
public final class MultiEventsResponseHandler implements HttpResponseHandler<ResultPage<Envelope<Event>>>
{
    @Override
    public ResultPage<Envelope<Event>> handleResponse(final HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        return new JsonArrayResultPage(new JSONArray(new StringResponseHandler().handleResponse(response)), new ResponseLinks(response));
    }
}
