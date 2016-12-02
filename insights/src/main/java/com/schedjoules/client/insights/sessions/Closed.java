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

package com.schedjoules.client.insights.sessions;

import com.schedjoules.client.UserIdentifier;
import com.schedjoules.client.insights.Session;
import org.dmfs.rfc5545.DateTime;


/**
 * A decorator that closes a {@link Session}.
 *
 * @author Marten Gajda
 */
public final class Closed implements Session
{
    private final Session mOpenSession;
    private final DateTime mClosed;


    public Closed(Session openSession)
    {
        mOpenSession = openSession;
        mClosed = DateTime.now();
    }


    @Override
    public CharSequence id()
    {
        return mOpenSession.id();
    }


    @Override
    public UserIdentifier user()
    {
        return mOpenSession.user();
    }


    @Override
    public DateTime start()
    {
        return mOpenSession.start();
    }


    @Override
    public DateTime end()
    {
        return mClosed;
    }
}
