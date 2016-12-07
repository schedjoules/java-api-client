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

import org.dmfs.httpessentials.headers.Header;
import org.dmfs.httpessentials.headers.HeaderType;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.headers.ListHeaderType;
import org.dmfs.httpessentials.headers.SingletonHeaderType;
import org.dmfs.httpessentials.headers.SingletonHeaders;
import org.dmfs.httpessentials.types.StringToken;
import org.dmfs.httpessentials.types.Token;
import org.dmfs.iterators.SingletonIterator;

import java.util.Iterator;
import java.util.List;


/**
 * {@link Headers} that only contain an API-VERSION header.
 *
 * @author Marten Gajda
 */
public final class ApiVersionHeaders implements Headers
{
    private final Headers mDelegate;


    public ApiVersionHeaders(String apiVersion)
    {
        this(new StringToken(apiVersion));
    }


    public ApiVersionHeaders(Token apiVersion)
    {
        mDelegate = new SingletonHeaders(HeaderTypes.API_VERSION.entity(apiVersion));
    }


    @Override
    public boolean contains(HeaderType<?> headerType)
    {
        return mDelegate.contains(headerType);
    }


    @Override
    public <T> Header<T> header(SingletonHeaderType<T> headerType)
    {
        return mDelegate.header(headerType);
    }


    @Override
    public <T> Header<List<T>> header(ListHeaderType<T> headerType)
    {
        return mDelegate.header(headerType);
    }


    @Override
    public <T> Headers withHeader(Header<T> header)
    {
        return mDelegate.withHeader(header);
    }


    @Override
    public <T> Headers withoutHeaderType(HeaderType<T> headerType)
    {
        return mDelegate.withoutHeaderType(headerType);
    }


    @Override
    public Iterator<Header<?>> iterator()
    {
        return new SingletonIterator<Header<?>>(header(HeaderTypes.API_VERSION));
    }
}
