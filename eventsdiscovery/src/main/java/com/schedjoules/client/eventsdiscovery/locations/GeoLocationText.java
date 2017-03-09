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

package com.schedjoules.client.eventsdiscovery.locations;

import com.schedjoules.client.eventsdiscovery.GeoLocation;

import java.util.Locale;


/**
 * A comma separated text representation of a {@link GeoLocation}.
 *
 * @author Marten Gajda
 */
public final class GeoLocationText implements CharSequence
{

    private final GeoLocation mGeoLocation;
    private String mString;


    public GeoLocationText(GeoLocation geoLocation)
    {
        mGeoLocation = geoLocation;
    }


    @Override
    public int length()
    {
        return toString().length();
    }


    @Override
    public char charAt(int i)
    {
        return toString().charAt(i);
    }


    @Override
    public CharSequence subSequence(int i, int i1)
    {
        return toString().subSequence(i, i1);
    }


    @Override
    public String toString()
    {
        if (mString == null)
        {
            mString = String.format(Locale.US, "%f,%f", mGeoLocation.latitude(), mGeoLocation.longitude());
        }
        return mString;
    }
}
