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

package com.schedjoules.client.eventsdiscovery.json.utils;

import com.schedjoules.client.eventsdiscovery.Event;
import com.schedjoules.client.eventsdiscovery.json.JsonEvent;
import com.schedjoules.client.eventsdiscovery.utils.Cached;
import org.dmfs.iterators.Function;
import org.json.JSONObject;


/**
 * A {@link Function} which returns an {@link Event} for a given {@link JSONObject}.
 *
 * @author Marten Gajda
 */
public final class JsonEventFactory implements Function<JSONObject, Event>
{
    @Override
    public Event apply(JSONObject jsonObject)
    {
        return new Cached(new JsonEvent(jsonObject));
    }
}
