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

import com.schedjoules.client.eventsdiscovery.Address;
import com.schedjoules.client.eventsdiscovery.GeoLocation;
import com.schedjoules.client.eventsdiscovery.Location;
import com.schedjoules.client.eventsdiscovery.locations.UriGeoLocation;
import org.json.JSONObject;

import java.util.TimeZone;


/**
 * A {@link Location} that's represented by a JSON object.
 *
 * @author Marten Gajda
 */
public final class JsonLocation implements Location
{
    private final JSONObject mJsonObject;


    public JsonLocation(JSONObject jsonObject)
    {
        mJsonObject = jsonObject;
    }


    @Override
    public String name()
    {
        return mJsonObject.isNull("name") ? null : mJsonObject.optString("name", null);
    }


    @Override
    public String rel()
    {
        return mJsonObject.optString("rel");
    }


    @Override
    public TimeZone timeZone()
    {
        String timeZone = mJsonObject.isNull("timeZone") ? null : mJsonObject.optString("timeZone");
        return timeZone == null || timeZone.isEmpty() ? null : TimeZone.getTimeZone(timeZone);
    }


    @Override
    public Address address()
    {
        JSONObject address = mJsonObject.getJSONObject("address");
        return address == null ? null : new JsonAddress(address);
    }


    @Override
    public GeoLocation geoLocation()
    {
        String geoLocation = mJsonObject.isNull("coordinates") ? null : mJsonObject.optString("coordinates", null);
        return geoLocation == null || geoLocation.isEmpty() ? null : new UriGeoLocation(geoLocation);
    }
}
