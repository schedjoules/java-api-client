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

package com.schedjoules.client.utils;

import com.schedjoules.client.UserIdentifier;
import org.dmfs.httpessentials.types.StringToken;


/**
 * A {@link UserIdentifier} that's derived from a {@link String}.
 *
 * @author Marten Gajda
 */
public final class StringUserIdentifier implements UserIdentifier
{
    private final StringToken mDelegate;


    public StringUserIdentifier(String token)
    {
        this(new StringToken(token));
    }


    public StringUserIdentifier(StringToken delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public int length()
    {
        return mDelegate.length();
    }


    @Override
    public char charAt(int index)
    {
        return mDelegate.charAt(index);
    }


    @Override
    public CharSequence subSequence(int start, int end)
    {
        return mDelegate.subSequence(start, end);
    }


    @Override
    public String toString()
    {
        return mDelegate.toString();
    }
}
