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

package com.schedjoules.client.insights;

import com.schedjoules.client.eventsdiscovery.Event;
import org.dmfs.rfc5545.DateTime;

import java.net.URI;


/**
 * A step is a date point captured during runtime triggered by user interactions with the app.
 *
 * @author Marten Gajda
 */
public interface Step
{
    /**
     * {@link DateTime} of when this {@link Step} has been captured.
     *
     * @return
     */
    DateTime timeStamp();

    /**
     * The category of this {@link Step}.
     *
     * @return
     */
    CharSequence category();

    /**
     * The data of this {@link Step}.
     *
     * @return A {@link URI} that encodes the data.
     */
    URI data();

    /**
     * The event that this {@link Step} is related to.
     *
     * @return An {@link Event} or {@code null} if this {@link Step} is not related to a specific {@link Event}.
     */
    Event event();
}
