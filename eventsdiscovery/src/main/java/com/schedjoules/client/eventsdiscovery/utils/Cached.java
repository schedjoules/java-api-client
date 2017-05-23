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

package com.schedjoules.client.eventsdiscovery.utils;

import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.Location;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.optional.Optional;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.Duration;


/**
 * A caching decorator for {@link Event}s.
 *
 * @author Marten Gajda
 */
public final class Cached implements Event
{
    private final Event mDelegate;
    private String mUid;
    private DateTime mStart;
    private Optional<Duration> mDuration;
    private String mTitle;
    private String mDescription;


    public Cached(Event delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public String uid()
    {
        if (mUid == null)
        {
            mUid = mDelegate.uid();
        }
        return mUid;
    }


    @Override
    public DateTime start()
    {
        if (mStart == null)
        {
            mStart = mDelegate.start();
        }
        return mStart;
    }


    @Override
    public Optional<Duration> duration()
    {
        if (mDuration == null)
        {
            mDuration = mDelegate.duration();
        }
        return mDuration;
    }


    @Override
    public String title()
    {
        if (mTitle == null)
        {
            mTitle = mDelegate.title();
        }
        return mTitle;
    }


    @Override
    public String description()
    {
        if (mDescription == null)
        {
            mDescription = mDelegate.description();
        }
        return mDescription;
    }


    @Override
    public Iterable<Location> locations()
    {
        return mDelegate.locations();
    }


    @Override
    public Iterable<Link> links()
    {
        return mDelegate.links();
    }
}
