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

package com.schedjoules.client.eventsdiscovery.queries;

import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.eventsdiscovery.GeoLocation;

import java.util.Locale;


/**
 * Interface of an {@link ApiQuery} to check whether the user's current country is supported by the API.
 *
 * @author Marten Gajda
 */
public interface FluentCoverage extends ApiQuery<Boolean>
{
    /**
     * Updates the Coverage {@link ApiQuery} with a specific {@link Locale}.
     *
     * @param locale
     *         The current user's {@link Locale}.
     *
     * @return A new {@link FluentCoverage} instance with the updated value.
     */
    FluentCoverage withLocale(Locale locale);

    /**
     * Update the Coverage {@link ApiQuery} with a SIM card country.
     *
     * @param simCountry
     *         The 2 letter SIM card country code.
     *
     * @return A new {@link FluentCoverage} instance with the updated value.
     */
    FluentCoverage withSimCountry(CharSequence simCountry);

    /**
     * Update the Coverage {@link ApiQuery} with a network operator country.
     *
     * @param operatorCountry
     *         The 2 letter network operator country code.
     *
     * @return A new {@link FluentCoverage} instance with the updated value.
     */
    FluentCoverage withOperatorCountry(CharSequence operatorCountry);

    /**
     * Update the Coverate {@link ApiQuery} with the user's {@link GeoLocation}.
     *
     * @param geoLocation
     *         The current {@link GeoLocation} of the user.
     *
     * @return A new {@link FluentCoverage} instance with the updated value.
     */
    FluentCoverage withGeoLocation(GeoLocation geoLocation);
}
