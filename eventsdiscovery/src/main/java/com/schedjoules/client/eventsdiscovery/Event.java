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

import org.dmfs.httpessentials.types.Link;
import org.dmfs.optional.Optional;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;


/**
 * @author Marten Gajda
 */
public interface Event
{
    String uid();

    DateTime start();

    /**
     * The {@link Optional} {@link Duration} of the event. This might be absent if the actual duration is unknown.
     *
     * @return An {@link Optional} {@link Duration}.
     */
    Optional<Duration> duration();

    String title();

    String description();

    Iterable<Location> locations();

    Iterable<Link> links();
}
