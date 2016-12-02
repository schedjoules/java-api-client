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

import java.util.Locale;


/**
 * A structured {@link GeoLocation}.
 *
 * @author Marten Gajda
 */
public final class StructuredGeoLocation implements GeoLocation
{
    private final float mLatitude;
    private final float mLongitude;


    public StructuredGeoLocation(float latitude, float longitude)
    {
        mLatitude = latitude;
        mLongitude = longitude;
    }


    @Override
    public float latitude()
    {
        return mLatitude;
    }


    @Override
    public float longitude()
    {
        return mLongitude;
    }


    @Override
    public String toString()
    {
        return String.format(Locale.US, "%f,%f", mLatitude, mLongitude);
    }


    @Override
    public int hashCode()
    {
        return 31 * (mLatitude != +0.0f ? Float.floatToIntBits(mLatitude) : 0) + (mLongitude != +0.0f ? Float.floatToIntBits(mLongitude) : 0);
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
        return Float.compare(mLatitude, ((GeoLocation) obj).latitude()) == 0 && Float.compare(mLongitude, ((GeoLocation) obj).longitude()) == 0;
    }
}
