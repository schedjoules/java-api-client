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

package com.schedjoules.client.http;

import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.headers.HttpHeaders;
import org.dmfs.httpessentials.headers.SingletonHeaders;
import org.dmfs.httpessentials.mockutils.entities.StaticMockResponseEntity;
import org.dmfs.httpessentials.mockutils.responses.StaticMockResponse;
import org.dmfs.httpessentials.types.StringMediaType;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;


/**
 * @author Marten Gajda
 */
public class RedirectUrlResponseHandlerTest
{
    @Test
    public void handleResponse() throws Exception
    {
        assertEquals(URI.create("http://example.com"),
                new RedirectUrlResponseHandler()
                        .handleResponse(
                                new StaticMockResponse(
                                        HttpStatus.FOUND,
                                        new SingletonHeaders(HttpHeaders.LOCATION.entityFromString("http://example.com")),
                                        new StaticMockResponseEntity(new StringMediaType("text/plain"), ""))));
    }

}