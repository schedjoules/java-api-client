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

import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.eventsdiscovery.Envelope;
import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.ResultPage;
import com.schedjoules.client.eventsdiscovery.json.JsonArrayIterable;
import com.schedjoules.client.eventsdiscovery.json.JsonEnvelope;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterables.decorators.Mapped;
import org.dmfs.iterators.Function;
import org.dmfs.optional.First;
import org.dmfs.optional.Optional;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A {@link ResultPage} derived from a {@link JSONArray} of envelopes and a set of {@link Link}s.
 *
 * @author Marten Gajda
 */
public final class JsonArrayResultPage implements ResultPage<Envelope<Event>>
{
    private final JSONArray mJsonArray;
    private final Iterable<Link> mLinks;


    public JsonArrayResultPage(JSONArray jsonArray, Iterable<Link> links)
    {
        mJsonArray = jsonArray;
        mLinks = links;
    }


    @Override
    public Iterable<Envelope<Event>> items()
    {
        return new Mapped<>(new JsonArrayIterable(mJsonArray), new Function<JSONObject, Envelope<Event>>()
        {
            @Override
            public Envelope<Event> apply(JSONObject jsonObject)
            {
                return new JsonEnvelope<>(jsonObject, new OptionalJsonEventFactory());
            }
        });
    }


    @Override
    public Optional<ApiQuery<ResultPage<Envelope<Event>>>> previousPageQuery()
    {
        return new First<>(new Mapped<>(new RelTyped(mLinks, "prev"), new LinkApiQueryFactory()));
    }


    @Override
    public Optional<ApiQuery<ResultPage<Envelope<Event>>>> nextPageQuery()
    {
        return new First<>(new Mapped<>(new RelTyped(mLinks, "next"), new LinkApiQueryFactory()));
    }


    /**
     * {@link Function} to convert links to prev and next result pages to {@link ApiQuery}s.
     */
    private final static class LinkApiQueryFactory implements Function<Link, ApiQuery<ResultPage<Envelope<Event>>>>
    {
        @Override
        public ApiQuery<ResultPage<Envelope<Event>>> apply(Link link)
        {
            return new ResultPageApiQuery(link);
        }
    }
}
