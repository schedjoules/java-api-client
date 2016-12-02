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

import java.util.UUID;


/**
 * A session.
 *
 * @author Marten Gajda
 */
public final class SimpleSession implements Session
{
    private final CharSequence mId;
    private final DateTime mStart;
    private final UserIdentifier mUser;


    public SimpleSession(UserIdentifier user)
    {
        mId = UUID.randomUUID().toString();
        mStart = DateTime.now();
        mUser = user;
    }


    @Override
    public CharSequence id()
    {
        return mId;
    }


    @Override
    public UserIdentifier user()
    {
        return mUser;
    }


    @Override
    public DateTime start()
    {
        return mStart;
    }


    @Override
    public DateTime end()
    {
        return null;
    }
}
