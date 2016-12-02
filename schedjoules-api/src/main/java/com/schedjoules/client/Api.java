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

package com.schedjoules.client;

import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;

import java.io.IOException;
import java.net.URI;


/**
 * Interface of a generic API.
 *
 * @author Marten Gajda
 */
public interface Api
{
    /**
     * Returns the result of the given {@link HttpRequest} on his API.
     *
     * @param path
     *         The path to the end point to access. This must not have any domain component.
     * @param request
     *         The request to execute on the given endpoint.
     * @param <T>
     *         The type of the expected result.
     *
     * @return
     *
     * @throws ProtocolException
     * @throws ProtocolError
     * @throws IOException
     */
    <T> T queryResult(URI path, HttpRequest<T> request) throws ProtocolException, ProtocolError, IOException;
}
