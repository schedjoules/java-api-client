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
import com.schedjoules.client.eventsdiscovery.Category;
import com.schedjoules.client.utils.HeaderTypes;

import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.headers.SingletonHeaders;
import org.dmfs.httpessentials.mockutils.entities.StaticMockResponseEntity;
import org.dmfs.httpessentials.mockutils.responses.StaticMockResponse;
import org.dmfs.httpessentials.types.StructuredMediaType;
import org.dmfs.rfc3986.uris.Text;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.net.URI;
import java.util.Iterator;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Functional test for {@link CategoriesQuery}.
 *
 * @author Gabor Keszthelyi
 */
public class CategoriesQueryTest
{
    @Test
    public void queryResult() throws Exception
    {
        Api mockApi = mock(Api.class);

        new CategoriesQuery(Locale.GERMANY).queryResult(mockApi);

        ArgumentCaptor<HttpRequest> request = ArgumentCaptor.forClass(HttpRequest.class);

        // check that the correct path is called and get the request
        verify(mockApi).queryResult(eq(URI.create("/categories")), request.capture());

        // check the request
        assertEquals(HttpMethod.GET, request.getValue().method());
        assertEquals("1", request.getValue().headers().header(HeaderTypes.API_VERSION).toString());
        assertEquals("de-DE", request.getValue().headers().header(HeaderTypes.ACCEPT_LANGUAGE).toString());

        // test the response handler of the request
        HttpResponse mockResponse = new StaticMockResponse(HttpStatus.OK, new SingletonHeaders(HeaderTypes.ETAG.entityFromString("\"1234\"")),
                new StaticMockResponseEntity(new StructuredMediaType("application", "json"),
                        "[\n" +
                                "   {\n" +
                                "      \"name\":\"http://schedjoules.com/categories/music/r-b\",\n" +
                                "      \"label\":\"R&b\",\n" +
                                "      \"filterable\":true\n" +
                                "   },\n" +
                                "   {\n" +
                                "      \"name\":\"http://schedjoules.com/categories/sports/american-football\",\n" +
                                "      \"label\":\"American football\",\n" +
                                "      \"filterable\":true\n" +
                                "   },\n" +
                                "   {\n" +
                                "      \"name\":\"http://schedjoules.com/categories/movies\",\n" +
                                "      \"label\":\"Movies\",\n" +
                                "      \"filterable\":true\n" +
                                "   },\n" +
                                "   {\n" +
                                "      \"name\":\"http://schedjoules.com/categories/museums\",\n" +
                                "      \"label\":\"Museums\",\n" +
                                "      \"filterable\":true\n" +
                                "   }\n" +
                                "]"));

        Iterator<Category> result = (Iterator<Category>) request.getValue().responseHandler(mockResponse).handleResponse(mockResponse);
        Category c1 = result.next();
        assertEquals("http://schedjoules.com/categories/music/r-b", new Text(c1.name()).toString());
        assertEquals("R&b", c1.label());
        result.next();
        result.next();
        Category c4 = result.next();
        assertEquals("http://schedjoules.com/categories/museums", new Text(c4.name()).toString());
        assertEquals("Museums", c4.label());
        assertFalse(result.hasNext());

        // test with empty json array response
        HttpResponse emptyMockResponse = new StaticMockResponse(HttpStatus.OK, new SingletonHeaders(HeaderTypes.ETAG.entityFromString("\"1234\"")),
                new StaticMockResponseEntity(new StructuredMediaType("application", "json"), "[]"));
        Iterator<Category> emptyResult = (Iterator<Category>) request.getValue().responseHandler(emptyMockResponse).handleResponse(emptyMockResponse);
        assertFalse(emptyResult.hasNext());
    }

}