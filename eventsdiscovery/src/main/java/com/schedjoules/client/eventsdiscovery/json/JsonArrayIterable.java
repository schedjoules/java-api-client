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

package com.schedjoules.client.eventsdiscovery.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;


/**
 * An {@link Iterable} to iterate the {@link JSONObject}s in a {@link JSONArray}.
 *
 * @author Marten Gajda
 */
public final class JsonArrayIterable implements Iterable<JSONObject>
{
    private final JSONArray mJsonArray;


    public JsonArrayIterable(JSONArray jsonArray)
    {
        // hmmm ... JSONArrays are not immutable but they don't offer any way to clone them either :-|
        mJsonArray = jsonArray;
    }


    @Override
    public Iterator<JSONObject> iterator()
    {
        return new JsonArrayIterator(mJsonArray);
    }
}
