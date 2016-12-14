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

package com.schedjoules.client.insights.steps;

import com.schedjoules.client.eventsdiscovery.Event;
import org.dmfs.httpessentials.types.Token;

import java.net.URI;


/**
 * An insight step that indicates that the event discovery entry point was or is about to be presented to the user.
 *
 * @author Marten Gajda
 */
public final class EntryPoint extends AbstractStep
{
    private final static URI ENTRY_POINT = URI.create("http://schedjoules.com/insights/entrypoint/");

    private final Token mName;


    /**
     * Create an EntryPoint insight with the given entry.point name.
     *
     * @param name
     *         The name of the entry point as a {@link Token}.
     */
    public EntryPoint(Token name)
    {
        mName = name;
    }


    @Override
    public CharSequence category()
    {
        return "entry-point";
    }


    @Override
    public URI data()
    {
        return ENTRY_POINT.resolve(mName.toString());
    }


    @Override
    public Event event()
    {
        // no event attached to this
        return null;
    }
}
