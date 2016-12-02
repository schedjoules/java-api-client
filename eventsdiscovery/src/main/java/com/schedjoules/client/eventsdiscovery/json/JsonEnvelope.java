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

package com.schedjoules.client.eventsdiscovery.json;

import com.schedjoules.client.eventsdiscovery.Envelope;
import org.json.JSONObject;


/**
 * An {@link Envelope} passed as a JSON object.
 *
 * @author Marten Gajda
 */
public final class JsonEnvelope<T> implements Envelope<T>
{

    public interface JsonPayloadFactory<T>
    {
        T payload(JSONObject jsonObject);
    }


    private final JSONObject mJsonObject;

    private final JsonPayloadFactory<T> mJsonPayloadFactory;


    public JsonEnvelope(JSONObject jsonObject, JsonPayloadFactory<T> jsonPayloadFactory)
    {
        mJsonObject = jsonObject;
        mJsonPayloadFactory = jsonPayloadFactory;
    }


    @Override
    public String etag()
    {
        return mJsonObject.optString("etag");
    }


    @Override
    public String uid()
    {
        return mJsonObject.optString("uid");
    }


    @Override
    public boolean hasPayload()
    {
        // TODO generalize the name of the property
        return mJsonObject.has("eventData");
    }


    @Override
    public T payload()
    {
        // TODO generalize the name of the property
        return mJsonPayloadFactory.payload(mJsonObject.getJSONObject("eventData"));
    }
}
