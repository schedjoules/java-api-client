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

package com.schedjoules.client.eventsdiscovery.utils;

import com.schedjoules.client.eventsdiscovery.GeoLocation;
import com.schedjoules.client.eventsdiscovery.locations.GeoLocationText;
import com.schedjoules.client.eventsdiscovery.locations.TextGeoLocation;
import org.dmfs.rfc3986.parameters.ValueType;


/**
 * A {@link ValueType} for {@link GeoLocation}s.
 *
 * @author Marten Gajda
 */
public final class GeoLocationValueType implements ValueType<GeoLocation>
{
    @Override
    public GeoLocation parsedValue(CharSequence valueText)
    {
        return new TextGeoLocation(valueText);
    }


    @Override
    public CharSequence serializedValue(GeoLocation value)
    {
        return new GeoLocationText(value);
    }
}
