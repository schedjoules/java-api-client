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

package com.schedjoules.client.eventsdiscovery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Marten Gajda
 */
public final class MapQueryString
{
    private final Map<String, String> mParams;


    public MapQueryString(Map<String, String> params)
    {
        mParams = new HashMap<>(params);
    }


    @Override
    public String toString()
    {
        try
        {
            StringBuilder stringBuilder = new StringBuilder(mParams.size() * 64);
            boolean first = true;
            for (Map.Entry<String, String> entry : mParams.entrySet())
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                    stringBuilder.append('&');
                }
                stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                stringBuilder.append('=');
                stringBuilder.append(entry.getValue());
//                stringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return stringBuilder.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("Runtime doesn't support UTF-8", e);
        }
    }
}
