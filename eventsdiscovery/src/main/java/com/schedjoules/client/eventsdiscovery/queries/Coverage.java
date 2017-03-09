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

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.eventsdiscovery.GeoLocation;
import com.schedjoules.client.eventsdiscovery.http.CustomHandled;
import com.schedjoules.client.eventsdiscovery.http.GetRequest;
import com.schedjoules.client.eventsdiscovery.http.OkStatusResponseHandler;
import com.schedjoules.client.eventsdiscovery.utils.GeoLocationValueType;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.rfc3986.encoding.XWwwFormUrlEncoded;
import org.dmfs.rfc3986.parameters.FluentParameterList;
import org.dmfs.rfc3986.parameters.ParameterType;
import org.dmfs.rfc3986.parameters.parametersets.BasicParameterList;
import org.dmfs.rfc3986.parameters.parametersets.Fluent;
import org.dmfs.rfc3986.parameters.parametertypes.BasicParameterType;
import org.dmfs.rfc3986.parameters.valuetypes.TextValueType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;


/**
 * An {@link ApiQuery} to check whether the user's current country is supported by the event discovery API.
 *
 * @author Marten Gajda
 */
public final class Coverage implements FluentCoverage
{
    private final static String QUERY_PATH = "/coverage?";

    private final static ParameterType<CharSequence> SIM_COUNTRY = new BasicParameterType<>("sim_country", TextValueType.INSTANCE);
    private final static ParameterType<CharSequence> OPERATOR_COUNTRY = new BasicParameterType<>("operator_country", TextValueType.INSTANCE);
    private final static ParameterType<CharSequence> CLIENT_REGION = new BasicParameterType<>("client_region", TextValueType.INSTANCE);
    private final static ParameterType<GeoLocation> GEO_LOCATION = new BasicParameterType<>("latlng", new GeoLocationValueType());

    private final FluentParameterList mParameters;


    public Coverage()
    {
        this(Locale.getDefault());
    }


    public Coverage(Locale locale)
    {
        this(new Fluent(new BasicParameterList(CLIENT_REGION.parameter(locale.getCountry()))));
    }


    private Coverage(FluentParameterList parameters)
    {
        mParameters = parameters;
    }


    @Override
    public FluentCoverage withLocale(Locale locale)
    {
        return new Coverage(mParameters.ratherWith(CLIENT_REGION.parameter(locale.getCountry())));
    }


    @Override
    public FluentCoverage withSimCountry(CharSequence simCountry)
    {
        return new Coverage(mParameters.ratherWith(SIM_COUNTRY.parameter(simCountry)));
    }


    @Override
    public FluentCoverage withOperatorCountry(CharSequence operatorCountry)
    {
        return new Coverage(mParameters.ratherWith(OPERATOR_COUNTRY.parameter(operatorCountry)));
    }


    @Override
    public FluentCoverage withGeoLocation(GeoLocation geoLocation)
    {
        return new Coverage(mParameters.ratherWith(GEO_LOCATION.parameter(geoLocation)));
    }


    @Override
    public Boolean queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException
    {
        // as long as we have to deal with Java's URI class we have to make sure commas are not encoded otherwise URI.create will double encode the "%2C" to "%252C".
        // this will no longer be an issue when we switch to uri-toolkit
        URI uri = URI.create(QUERY_PATH + new XWwwFormUrlEncoded(mParameters).toString().replace("%2C", ","));

        HttpResponseHandler<Boolean> responseHandler = new OkStatusResponseHandler();
        return api.queryResult(uri, new CustomHandled<>(new GetRequest<>(responseHandler), responseHandler));
    }
}
