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
import com.schedjoules.client.insights.Step;
import org.dmfs.httpessentials.types.Token;

import java.net.URI;


/**
 * A {@link Step} that indicates a screen was presented to the user.
 *
 * @author Marten Gajda
 */
public final class Screen extends AbstractStep implements Step
{
    private final static URI SCREEN_URI = URI.create("http://schedjoules.com/insights/screen/");

    private final Token mName;
    private final Event mEvent;


    public Screen(Token name)
    {
        this(name, null);
    }


    public Screen(Token name, Event event)
    {
        mName = name;
        mEvent = event;
    }


    @Override
    public CharSequence category()
    {
        return "screen";
    }


    @Override
    public URI data()
    {
        return SCREEN_URI.resolve(mName.toString());
    }


    @Override
    public Event event()
    {
        return null;
    }

}
