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
import org.dmfs.httpessentials.types.Link;

import java.net.URI;


/**
 * An interaction {@link Step} triggered by the intent to start an action.
 *
 * @author Marten Gajda
 */
public final class ActionInteraction extends AbstractStep implements Step
{
    private final Link mActionLink;
    private final Event mEvent;


    public ActionInteraction(Link actionLink, Event event)
    {
        super();
        mActionLink = actionLink;
        mEvent = event;
    }


    @Override
    public CharSequence category()
    {
        return "interaction";
    }


    @Override
    public URI data()
    {
        return URI.create(mActionLink.relationTypes().iterator().next());
    }


    @Override
    public Event event()
    {
        return mEvent;
    }
}
