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

package com.schedjoules.client;

import org.dmfs.httpessentials.types.Token;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @author Marten Gajda
 */
public final class SimpleQueryParams implements CharSequence
{
    private final Token mKey;
    private final String mValue;


    public SimpleQueryParams(Token key, String value)
    {
        mKey = key;
        mValue = value;
    }


    @Override
    public int length()
    {
        return toString().length();
    }


    @Override
    public char charAt(int i)
    {
        return toString().charAt(i);
    }


    @Override
    public CharSequence subSequence(int i, int i1)
    {
        return toString().subSequence(i, i1);
    }


    @Override
    public String toString()
    {
        try
        {
            return mKey + "=" + URLEncoder.encode(mValue, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException("UTF-8 not supported by Runtime", e);
        }
    }
}
