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

import com.schedjoules.client.ApiQuery;
import org.dmfs.rfc5545.DateTime;


/**
 * Represents an API query to discover events.
 *
 * @author Marten Gajda
 */
public interface EventsDiscovery extends ApiQuery<ResultPage<Envelope<Event>>>
{
    /**
     * Returns this {@link EventsDiscovery} query with the given search query.
     *
     * @param searchQuery
     *
     * @return
     */
    EventsDiscovery withSearchQuery(String searchQuery);

    /**
     * Limit the result to events at the given locations.
     *
     * @param geoLocation
     *
     * @return
     */
    EventsDiscovery withGeoLocation(GeoLocation geoLocation);

    /**
     * Limit the result to events at the given locations within the given radius.
     *
     * @param geoLocation
     * @param radius
     *         The radius in meters to consider around the given locations.
     *
     * @return
     */
    EventsDiscovery withGeoLocation(GeoLocation geoLocation, int radius);

    /**
     * Sets a limit on the results to return.
     *
     * @param resultsLimit
     *         The maximum number of results to return.
     *
     * @return
     */
    EventsDiscovery withResultsLimit(Integer resultsLimit);

    /**
     * Sets the limit for the latest start date to return.
     *
     * @param startBefore
     *
     * @return
     */
    EventsDiscovery withStartBefore(DateTime startBefore);

    /**
     * Sets the earliest start date to return.
     *
     * @param startAtOrAfter
     *
     * @return
     */
    EventsDiscovery withStartAtOrAfter(DateTime startAtOrAfter);
}
