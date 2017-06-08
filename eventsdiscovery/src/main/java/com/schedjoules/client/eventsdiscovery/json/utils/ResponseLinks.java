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

import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.headers.HttpHeaders;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterators.EmptyIterator;

import java.util.Iterator;


/**
 * {@link Iterable} of all {@link Link}s in the headers of a {@link HttpResponse}.
 *
 * @author Marten Gajda
 */
public final class ResponseLinks implements Iterable<Link>
{
    private final HttpResponse mResponse;


    /**
     * Creates an {@link Iterable} of {@link Link} which are specified in the headers of the given {@link HttpResponse}.
     *
     * @param response
     *         The {@link HttpResponse}.
     */
    public ResponseLinks(HttpResponse response)
    {
        mResponse = response;
    }


    @Override
    public Iterator<Link> iterator()
    {
        Headers headers = mResponse.headers();
        return headers.contains(HttpHeaders.LINK) ? headers.header(HttpHeaders.LINK).value().iterator() : EmptyIterator.<Link>instance();
    }
}
