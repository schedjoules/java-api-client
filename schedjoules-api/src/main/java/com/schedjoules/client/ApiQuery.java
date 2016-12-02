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

import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * Represents a query on an {@link Api}.
 *
 * @author Marten Gajda
 */
public interface ApiQuery<T>
{
    /**
     * Returns the result of this query on the given {@link Api}.
     *
     * @param api
     *         The {@link Api} to execute this query on.
     *
     * @return The result of the query.
     *
     * @throws IOException
     * @throws URISyntaxException
     * @throws ProtocolError
     * @throws ProtocolException
     */
    T queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException;
}
