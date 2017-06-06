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

package com.schedjoules.client.eventsdiscovery.locations;

import com.schedjoules.client.eventsdiscovery.GeoLocation;
import org.dmfs.iterables.Split;

import java.net.URI;
import java.util.Iterator;
import java.util.Locale;


/**
 * A {@link GeoLocation} based on a {@code geo:} URI.
 *
 * @author Marten Gajda
 */
/*
 * TODO: improve efficiency
 */
public final class UriGeoLocation implements GeoLocation
{
    private final Iterable<CharSequence> mValues;


    public UriGeoLocation(String geoLocation)
    {
        this(URI.create(geoLocation));
    }


    public UriGeoLocation(URI geoLocation)
    {
        mValues = new Split(geoLocation.getSchemeSpecificPart(), ',');
    }


    @Override
    public float latitude()
    {
        return Float.parseFloat(mValues.iterator().next().toString());
    }


    @Override
    public float longitude()
    {
        Iterator<CharSequence> parts = mValues.iterator();
        parts.next();
        return Float.parseFloat(parts.next().toString());
    }


    @Override
    public String toString()
    {
        return String.format(Locale.US, "%f,%f", latitude(), longitude());
    }


    @Override
    public int hashCode()
    {
        return 31 * (latitude() != +0.0f ? Float.floatToIntBits(latitude()) : 0) + (longitude() != +0.0f ? Float.floatToIntBits(longitude()) : 0);
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!(obj instanceof GeoLocation))
        {
            return false;
        }
        return Float.compare(latitude(), ((GeoLocation) obj).latitude()) == 0 && Float.compare(longitude(), ((GeoLocation) obj).longitude()) == 0;
    }
}
