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

package com.schedjoules.client.eventsdiscovery.queries;

import com.schedjoules.client.Api;
import com.schedjoules.client.eventsdiscovery.Envelope;
import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.utils.HeaderTypes;
import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.headers.SingletonHeaders;
import org.dmfs.httpessentials.mockutils.entities.StaticMockResponseEntity;
import org.dmfs.httpessentials.mockutils.responses.StaticMockResponse;
import org.dmfs.httpessentials.types.StringToken;
import org.dmfs.httpessentials.types.StructuredMediaType;
import org.dmfs.rfc5545.DateTime;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.net.URI;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * @author Marten Gajda
 */
public class EventByUidTest
{
    @Test
    public void queryResult() throws Exception
    {
        Api mockApi = mock(Api.class);

        new EventByUid(new StringToken("xxxx")).queryResult(mockApi);

        ArgumentCaptor<HttpRequest> request = ArgumentCaptor.forClass(HttpRequest.class);

        // check that the correct path is called and get the request
        verify(mockApi).queryResult(eq(URI.create("/events/xxxx")), request.capture());

        // check the request
        assertEquals(HttpMethod.GET, request.getValue().method());
        assertEquals("1", request.getValue().headers().header(HeaderTypes.API_VERSION).value().toString());

        // test the response handler of the request
        HttpResponse mockResponse = new StaticMockResponse(HttpStatus.OK, new SingletonHeaders(HeaderTypes.ETAG.entityFromString("\"1234\"")),
                new StaticMockResponseEntity(new StructuredMediaType("application", "json"), "{\n" +
                        "  \"uid\": \"xxxx\",\n" +
                        "  \"title\": \"test\",\n" +
                        "  \"start\": \"2016-01-02T03:04:05\",\n" +
                        "  \"timeZone\": \"Europe/Berlin\"\n" +
                        "}"));

        Envelope<Event> result = ((HttpRequest<Envelope<Event>>) request.getValue()).responseHandler(mockResponse).handleResponse(mockResponse);
        assertEquals("xxxx", result.uid());
        assertTrue(result.payload().isPresent());
        assertEquals("\"1234\"", result.etag());
        assertEquals("test", result.payload().value().title());
        assertEquals(DateTime.parse("Europe/Berlin", "20160102T030405"), result.payload().value().start());
    }

}