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
import com.schedjoules.client.eventsdiscovery.locations.TextGeoLocation;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.EmptyHeaders;
import org.dmfs.httpessentials.mockutils.entities.StaticMockResponseEntity;
import org.dmfs.httpessentials.mockutils.responses.CustomUrisMockResponse;
import org.dmfs.httpessentials.mockutils.responses.StaticMockResponse;
import org.dmfs.httpessentials.types.StringMediaType;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class CoverageTest
{
    @Test
    public void testPositiveQueryResult() throws Exception
    {
        Api mockApi = new Api()
        {
            @Override
            public <T> T queryResult(URI path, HttpRequest<T> request) throws ProtocolException, ProtocolError, IOException
            {
                assertEquals("/coverage?client_region=DE&sim_country=de&operator_country=fr&latlng=0.000000,0.000000", path.toString());
                HttpResponse response = new CustomUrisMockResponse(
                        new StaticMockResponse(HttpStatus.OK, EmptyHeaders.INSTANCE, new StaticMockResponseEntity(new StringMediaType("text/plain"), "")), path,
                        path);
                return request.responseHandler(response).handleResponse(response);
            }
        };
        assertTrue(
                new Coverage(Locale.GERMANY).withSimCountry("de").withOperatorCountry("fr").withGeoLocation(new TextGeoLocation("0,0")).queryResult(mockApi));
    }


    @Test
    public void testNegativeQueryResult() throws Exception
    {
        Api mockApi = new Api()
        {
            @Override
            public <T> T queryResult(URI path, HttpRequest<T> request) throws ProtocolException, ProtocolError, IOException
            {
                assertEquals("/coverage?client_region=DE&sim_country=de&operator_country=fr&latlng=0.000000,0.000000", path.toString());
                HttpResponse response = new CustomUrisMockResponse(new StaticMockResponse(HttpStatus.NOT_FOUND, EmptyHeaders.INSTANCE,
                        new StaticMockResponseEntity(new StringMediaType("text/plain"), "")), path, path);
                return request.responseHandler(response).handleResponse(response);
            }
        };
        assertFalse(
                new Coverage(Locale.GERMANY).withSimCountry("de").withOperatorCountry("fr").withGeoLocation(new TextGeoLocation("0,0")).queryResult(mockApi));
    }

}