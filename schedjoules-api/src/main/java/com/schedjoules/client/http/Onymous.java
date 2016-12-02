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

package com.schedjoules.client.http;

import com.schedjoules.client.SimpleQueryParams;
import com.schedjoules.client.UserIdentifier;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.converters.PlainStringHeaderConverter;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.exceptions.RedirectionException;
import org.dmfs.httpessentials.exceptions.UnexpectedStatusException;
import org.dmfs.httpessentials.parameters.BasicParameterType;
import org.dmfs.httpessentials.parameters.ParameterType;
import org.dmfs.httpessentials.types.StringToken;
import org.dmfs.httpessentials.types.UrlFormEncodedKeyValues;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author Marten Gajda
 */
// TODO: refactor to something more generic (like a "Parametrized" decorator that takes a key-value pair and adds it to the URL of each request), which is then used by the Onymous decorator.
public final class Onymous implements HttpRequestExecutor
{
    private final static ParameterType<String> PARAM_U = new BasicParameterType<>("u", PlainStringHeaderConverter.INSTANCE);

    private final HttpRequestExecutor mDelegate;
    private final UserIdentifier mUserIdentifier;


    public Onymous(HttpRequestExecutor delegate, UserIdentifier userIdentifier)
    {
        mDelegate = delegate;
        mUserIdentifier = userIdentifier;
    }


    @Override
    public <T> T execute(URI uri, HttpRequest<T> request) throws IOException, ProtocolError, ProtocolException, RedirectionException, UnexpectedStatusException
    {
        String query = uri.getRawQuery();
        if (query == null || query.isEmpty())
        {
            query = new SimpleQueryParams(new StringToken("u"), mUserIdentifier.toString()).toString();
        }
        else if (!new UrlFormEncodedKeyValues(query).hasParameter(PARAM_U))
        {
            query = String.format("%s&%s", query, new SimpleQueryParams(new StringToken("u"), mUserIdentifier.toString()).toString());
        }
        try
        {
            return mDelegate.execute(new URI(uri.getScheme(), uri.getAuthority(), uri.getRawPath(),
                    query, null), request);
        }
        catch (URISyntaxException e)
        {
            throw new ProtocolException("Can't build valid URI", e);
        }
    }
}
