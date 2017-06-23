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

package com.schedjoules.client.eventsdiscovery.utils;

import java.util.Iterator;


/**
 * {@link CharSequence} created by joining the given {@link CharSequence} elements' toString() value using the given separator.
 *
 * @author Gabor Keszthelyi
 */
public final class Joined implements CharSequence
{
    private final Iterator<CharSequence> mElements;
    private final CharSequence mSeparator;

    private CharSequence mCachedValue;


    public Joined(CharSequence separator, Iterator<CharSequence> elements)
    {
        mElements = elements;
        mSeparator = separator;
    }


    public Joined(CharSequence separator, Iterable<CharSequence> elements)
    {
        this(separator, elements.iterator());
    }


    @Override
    public int length()
    {
        return cachedValue().length();
    }


    @Override
    public char charAt(int index)
    {
        return cachedValue().charAt(index);
    }


    @Override
    public CharSequence subSequence(int start, int end)
    {
        return cachedValue().subSequence(start, end);
    }


    @Override
    public String toString()
    {
        return cachedValue().toString();
    }


    private CharSequence cachedValue()
    {
        if (mCachedValue == null)
        {
            StringBuilder sb = new StringBuilder(128);
            boolean isFirst = true;

            while (mElements.hasNext())
            {
                Object element = mElements.next();
                if (!isFirst)
                {
                    sb.append(mSeparator);
                }
                isFirst = false;

                sb.append(element);
            }
            mCachedValue = sb;
        }
        return mCachedValue;
    }

}
