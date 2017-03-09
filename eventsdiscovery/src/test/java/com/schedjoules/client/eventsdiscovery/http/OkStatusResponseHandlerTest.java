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

package com.schedjoules.client.eventsdiscovery.http;

import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.headers.EmptyHeaders;
import org.dmfs.httpessentials.mockutils.entities.StaticMockResponseEntity;
import org.dmfs.httpessentials.mockutils.responses.StaticMockResponse;
import org.dmfs.httpessentials.types.StringMediaType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author marten
 */
public class OkStatusResponseHandlerTest
{
    @Test
    public void testHandleOkResponse() throws Exception
    {
        assertTrue(new OkStatusResponseHandler().handleResponse(
                new StaticMockResponse(HttpStatus.OK, EmptyHeaders.INSTANCE, new StaticMockResponseEntity(new StringMediaType("text/plain"), ""))));
    }


    @Test
    public void testHandleNokResponse() throws Exception
    {
        assertFalse(new OkStatusResponseHandler().handleResponse(
                new StaticMockResponse(HttpStatus.NOT_FOUND, EmptyHeaders.INSTANCE, new StaticMockResponseEntity(new StringMediaType("text/plain"), ""))));
    }

}