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

package com.schedjoules.client.eventsdiscovery.json;

import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.Location;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.EmptyIterator;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * @author Marten Gajda
 */
public final class JsonEvent implements Event
{
    private final JSONObject mJsonObject;


    public JsonEvent(JSONObject jsonObject)
    {
        mJsonObject = jsonObject;
    }


    @Override
    public DateTime start()
    {
        if (mJsonObject.optBoolean("isAllDay", false))
        {
            return DateTime.parse(mJsonObject.optString("start").replaceAll("[-:]", "")).toAllDay();
        }
        else if (mJsonObject.has("timeZone"))
        {
            return DateTime.parse(mJsonObject.optString("timeZone"), mJsonObject.optString("start").replaceAll("[-:]", ""));
        }
        else
        {
            return DateTime.parse(mJsonObject.optString("start").replaceAll("[-:]", ""));
        }
    }


    @Override
    public Duration duration()
    {
        String durationString = mJsonObject.optString("duration", "P0D");
        return Duration.parse(durationString);
    }


    @Override
    public String title()
    {
        return mJsonObject.isNull("title") ? null : mJsonObject.optString("title", null);
    }


    @Override
    public String description()
    {
        return mJsonObject.isNull("description") ? null : mJsonObject.optString("description", null);
    }


    @Override
    public Iterable<Location> locations()
    {
        final JSONObject jsonObject = mJsonObject;
        return new Iterable<Location>()
        {
            @Override
            public Iterator<Location> iterator()
            {
                final JSONObject locations = jsonObject.optJSONObject("locations");
                return locations == null ? EmptyIterator.<Location>instance() : new ConvertedIterator<>(locations.keys(),
                        new AbstractConvertedIterator.Converter<Location, String>()
                        {
                            @Override
                            public Location convert(String element)
                            {
                                return new JsonLocation(locations.getJSONObject(element));
                            }
                        });
            }
        };
    }


    @Override
    public Iterable<Link> links()
    {
        final JSONObject jsonObject = mJsonObject;
        return new Iterable<Link>()
        {
            @Override
            public Iterator<Link> iterator()
            {
                final JSONArray links = jsonObject.optJSONArray("links");
                return links == null ? EmptyIterator.<Link>instance() : new ConvertedIterator<>(new JsonArrayIterator(links),
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


    @Override
    public String uid()
    {
        return mJsonObject.optString("uid");
    }
}
