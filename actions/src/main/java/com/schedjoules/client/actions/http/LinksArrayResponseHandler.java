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

package com.schedjoules.client.actions.http;

import com.schedjoules.client.eventsdiscovery.json.JsonArrayIterator;
import com.schedjoules.client.eventsdiscovery.json.JsonLink;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.responsehandlers.StringResponseHandler;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.EmptyIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;


/**
 * An {@link HttpResponseHandler} for responses that contain an array of {@link Link}s.
 *
 * @author Marten Gajda
 */
public final class LinksArrayResponseHandler implements HttpResponseHandler<Iterator<Link>>
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
}
