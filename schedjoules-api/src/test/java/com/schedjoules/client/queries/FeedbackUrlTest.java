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

package com.schedjoules.client.queries;

import com.schedjoules.client.Api;
import com.schedjoules.client.utils.HeaderTypes;
import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.headers.HttpHeaders;
import org.dmfs.httpessentials.headers.SingletonHeaders;
import org.dmfs.httpessentials.mockutils.entities.StaticMockResponseEntity;
import org.dmfs.httpessentials.mockutils.responses.StaticMockResponse;
import org.dmfs.httpessentials.types.StringMediaType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.net.URI;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author Marten Gajda
 */
public class FeedbackUrlTest
{
    @Test
    public void queryResult() throws Exception
    {
        ArgumentCaptor<URI> uriArgumentCaptor = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);

        Api mockApi = mock(Api.class);
        when(mockApi.queryResult(uriArgumentCaptor.capture(), requestArgumentCaptor.capture())).thenReturn(URI.create("http://example.com"));

        URI uri = new FeedbackUrl(Locale.GERMANY).queryResult(mockApi);

        assertEquals(URI.create("http://example.com"), uri);
        // TODO: this test is a bit fragile, because the actual order of the parameters depends on how the internal map returns it, but in reality it doesn't matter
        assertEquals(URI.create("/feedback?locale=de&location=DE"), uriArgumentCaptor.getValue());
        assertEquals(HttpMethod.GET, requestArgumentCaptor.getValue().method());
        assertEquals("1", requestArgumentCaptor.getValue().headers().header(HeaderTypes.API_VERSION).value().toString());

        HttpResponse mockResponse = new StaticMockResponse(
                HttpStatus.FOUND,
                new SingletonHeaders(HttpHeaders.LOCATION.entityFromString("http://example.com")),
                new StaticMockResponseEntity(new StringMediaType("text/plain"), ""));

        Assert.assertEquals(URI.create("http://example.com"), requestArgumentCaptor.getValue().responseHandler(mockResponse).handleResponse(mockResponse));
    }

}