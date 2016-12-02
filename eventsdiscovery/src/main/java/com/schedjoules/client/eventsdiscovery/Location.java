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

package com.schedjoules.client.eventsdiscovery;

import java.util.TimeZone;


/**
 * @author Marten Gajda
 */
public interface Location
{
    /**
     * Returns the name of the location.
     *
     * @return A String containing the location name or {@code null} if no name is present.
     */
    String name();

    String rel();

    /**
     * Returns the {@link TimeZone} of the location of known.
     *
     * @return A {@link TimeZone} of the location or {@code null} if unknown.
     */
    TimeZone timeZone();

    Address address();

    GeoLocation geoLocation();
}
