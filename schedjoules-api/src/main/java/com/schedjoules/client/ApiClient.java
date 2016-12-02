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


/**
 * A client to the SchedJoules API. An instance of this knows how to authorize requests.
 *
 * @author Marten Gajda
 */
public interface ApiClient
{
    /**
     * Return an authorized version of the given {@link HttpRequest}.
     *
     * @param request
     *         The {@link HttpRequest} to authorize.
     * @param <T>
     *
     * @return An authorized copy of the given request.
     */
    <T> HttpRequest<T> authorizedRequest(HttpRequest<T> request);
}
