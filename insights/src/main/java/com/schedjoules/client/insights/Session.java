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

import com.schedjoules.client.UserIdentifier;
import org.dmfs.rfc5545.DateTime;


/**
 * Holds information about the current session.
 *
 * @author Marten Gajda
 */
public interface Session
{
    /**
     * A unique session id.
     *
     * @return
     */
    CharSequence id();

    /**
     * The user who started this session.
     *
     * @return
     */
    UserIdentifier user();

    /**
     * The start of the session.
     *
     * @return
     */
    DateTime start();

    /**
     * The end of the session.
     *
     * @return
     */
    DateTime end();
}
