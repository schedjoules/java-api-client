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
import org.dmfs.iterators.Function;
import org.dmfs.optional.Optional;
import org.dmfs.optional.decorators.Mapped;
import org.json.JSONObject;


/**
 * A factory to create {@link Optional} {@link Event}s objects from {@link Optional} {@link JSONObject}s.
 *
 * @author Marten Gajda
 */
public final class OptionalJsonEventFactory implements Function<Optional<JSONObject>, Optional<Event>>
{
    @Override
    public Optional<Event> apply(Optional<JSONObject> jsonObject)
    {
        return new Mapped<>(new JsonEventFactory(), jsonObject);
    }
}
