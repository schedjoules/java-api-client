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

package com.schedjoules.client.eventsdiscovery;

import com.schedjoules.client.ApiQuery;
import org.dmfs.optional.Optional;


/**
 * A page of the result of an {@link ApiQuery}. It contains an {@link Iterable} of items and optionally returns two more {@link ApiQuery}s to load any previous
 * or next page.
 *
 * @author Marten Gajda
 */
public interface ResultPage<E>
{
    Iterable<E> items();

    /**
     * Returns an {@link Optional} {@link ApiQuery} to retrieve the previous {@link ResultPage}, if any.
     *
     * @return
     */
    Optional<ApiQuery<ResultPage<E>>> previousPageQuery();

    /**
     * Returns an {@link Optional} {@link ApiQuery} to retrieve the previous {@link ResultPage}, if any.
     *
     * @return
     */
    Optional<ApiQuery<ResultPage<E>>> nextPageQuery();
}
