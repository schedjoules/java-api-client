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

import com.schedjoules.client.eventsdiscovery.Address;
import org.json.JSONObject;


/**
 * An {@link Address} passed in a JSON object.
 *
 * @author Marten Gajda
 */
public final class JsonAddress implements Address
{

    private final JSONObject mJsonObject;


    public JsonAddress(JSONObject jsonObject)
    {
        mJsonObject = jsonObject;
    }


    @Override
    public String street()
    {
        return mJsonObject.isNull("street") ? null : mJsonObject.optString("street", null);
    }


    @Override
    public String region()
    {
        return mJsonObject.isNull("region") ? null : mJsonObject.optString("region", null);
    }


    @Override
    public String locality()
    {
        return mJsonObject.isNull("locality") ? null : mJsonObject.optString("locality", null);
    }


    @Override
    public String postCode()
    {
        return mJsonObject.isNull("postCode") ? null : mJsonObject.optString("postCode", null);
    }


    @Override
    public String country()
    {
        return mJsonObject.isNull("country") ? null : mJsonObject.optString("country", null);
    }
}
