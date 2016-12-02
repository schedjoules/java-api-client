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

package com.schedjoules.client.insights.utils;

import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.types.MediaType;
import org.dmfs.httpessentials.types.StructuredMediaType;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


/**
 * An {@link HttpRequestEntity} that contains a {@link JSONObject}.
 *
 * @author Marten Gajda
 */
public final class JsonRequestEntity implements HttpRequestEntity
{
    private final static MediaType MEDIA_TYPE = new StructuredMediaType("application", "json");

    private final JSONObject mJsonObject;


    public JsonRequestEntity(JSONObject jsonObject)
    {
        mJsonObject = jsonObject;
    }


    @Override
    public MediaType contentType()
    {
        return MEDIA_TYPE;
    }


    @Override
    public long contentLength() throws IOException
    {
        return data().length;
    }


    @Override
    public void writeContent(OutputStream out) throws IOException
    {
        out.write(data());
    }


    private byte[] data() throws UnsupportedEncodingException
    {
        return mJsonObject.toString().getBytes("UTF-8");
    }
}
