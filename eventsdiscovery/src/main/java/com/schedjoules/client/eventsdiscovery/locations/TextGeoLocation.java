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


/**
 * A {@link GeoLocation} parsed from comma separated latitude and longitude.
 *
 * @author Marten Gajda
 */
public final class TextGeoLocation implements GeoLocation
{

    private final CharSequence mLocationText;
    private int mCommaPos = -1;


    public TextGeoLocation(CharSequence locationText)
    {
        mLocationText = locationText;
    }


    @Override
    public float latitude()
    {
        return Float.parseFloat(mLocationText.subSequence(0, commaPos()).toString());
    }


    @Override
    public float longitude()
    {
        return Float.parseFloat(mLocationText.subSequence(commaPos() + 1, mLocationText.length()).toString());
    }


    private int commaPos()
    {
        if (mCommaPos < 0)
        {
            int i = 1;
            while (i < mLocationText.length())
            {
                if (mLocationText.charAt(i) == ',')
                {
                    return mCommaPos = i;
                }
                ++i;
            }
        }
        return mCommaPos;
    }
}
