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
import com.schedjoules.client.eventsdiscovery.Envelope;
import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.EventsDiscovery;
import com.schedjoules.client.eventsdiscovery.GeoLocation;
import com.schedjoules.client.eventsdiscovery.MapQueryString;
import com.schedjoules.client.eventsdiscovery.ResultPage;
import com.schedjoules.client.eventsdiscovery.http.GetRequest;
import com.schedjoules.client.eventsdiscovery.http.MultiEventsResponseHandler;
import com.schedjoules.client.utils.ApiVersionHeaders;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.rfc5545.DateTime;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Marten Gajda
 */
public final class SimpleEventsDiscovery implements EventsDiscovery
{
    private final static String QUERY_PATH = "/events";

    // TODO: get rid of this map and use something immutable
    private final Map<String, String> mParams;


    public SimpleEventsDiscovery()
    {
        this(new HashMap<String, String>());
    }


    private SimpleEventsDiscovery(Map<String, String> params)
    {
        mParams = params;
    }


    @Override
    public EventsDiscovery withSearchQuery(String searchQuery)
    {
        Map<String, String> newParams = new HashMap<>(mParams);
        newParams.put("q", searchQuery);
        return new SimpleEventsDiscovery(newParams);
    }


    @Override
    public EventsDiscovery withGeoLocation(GeoLocation geoLocation)
    {
        Map<String, String> newParams = new HashMap<>(mParams);
        newParams.put("latlng", geoLocation.toString());
        return new SimpleEventsDiscovery(newParams);
    }


    @Override
    public EventsDiscovery withGeoLocation(GeoLocation geoLocation, int radius)
    {
        Map<String, String> newParams = new HashMap<>(mParams);
        newParams.put("latlng", geoLocation.toString());
        newParams.put("radius", Integer.toString(radius));
        return new SimpleEventsDiscovery(newParams);
    }


    @Override
    public EventsDiscovery withResultsLimit(Integer resultsLimit)
    {
        Map<String, String> newParams = new HashMap<>(mParams);
        newParams.put("results", Integer.toString(resultsLimit));
        return new SimpleEventsDiscovery(newParams);
    }


    @Override
    public EventsDiscovery withStartBefore(DateTime startBefore)
    {
        Map<String, String> newParams = new HashMap<>(mParams);
        newParams.put("start_before", renderDateTime(startBefore));
        return new SimpleEventsDiscovery(newParams);
    }


    @Override
    public EventsDiscovery withStartAtOrAfter(DateTime startAtOrAfter)
    {
        Map<String, String> newParams = new HashMap<>(mParams);
        newParams.put("start_at_or_after", renderDateTime(startAtOrAfter));
        return new SimpleEventsDiscovery(newParams);
    }


    private String renderDateTime(DateTime dateTime)
    {
        DateTime utcDate = dateTime.shiftTimeZone(DateTime.UTC);
        return String.format("%04d-%02d-%02dT%02d:%02d:%02dZ", utcDate.getYear(), utcDate.getMonth() + 1, utcDate.getDayOfMonth(),
                utcDate.getHours(), utcDate.getMinutes(), utcDate.getSeconds());
    }


    @Override
    public ResultPage<Envelope<Event>> queryResult(Api api) throws IOException, ProtocolError, ProtocolException
    {
        try
        {
            Map<String, String> map = new HashMap<>(mParams);
            return api.queryResult(new URI(null, null, QUERY_PATH, new MapQueryString(map).toString(), null),
                    new GetRequest<>(new ApiVersionHeaders("1"), new MultiEventsResponseHandler()));
        }
        catch (URISyntaxException e)
        {
            throw new ProtocolException("Can't build URL", e);
        }
    }
}
