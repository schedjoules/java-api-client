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

package com.schedjoules.client.eventsdiscovery.queries;

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.State;
import com.schedjoules.client.eventsdiscovery.Category;
import com.schedjoules.client.eventsdiscovery.http.CategoriesArrayResponseHandler;
import com.schedjoules.client.eventsdiscovery.http.GetRequest;
import com.schedjoules.client.utils.ApiVersionHeaders;
import com.schedjoules.client.utils.HeaderTypes;

import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;


/**
 * {@link ApiQuery} to retrieve the supported categories with their translated labels for a given language.
 *
 * @author Gabor Keszthelyi
 */
public final class CategoriesQuery implements ApiQuery<Iterable<Category>>
{
    private final static String API_VERSION = "1";
    private final static String PATH = "/categories";

    private final Locale mLocale;


    public CategoriesQuery()
    {
        this(Locale.getDefault());
    }


    public CategoriesQuery(Locale locale)
    {
        mLocale = locale;
    }


    @Override
    public Iterable<Category> queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException
    {
        return api.queryResult(
                new URI(null, null, PATH, null, null),
                new GetRequest<>(
                        new ApiVersionHeaders(API_VERSION)
                                .withHeader(HeaderTypes.ACCEPT_LANGUAGE.entity(mLocale)),
                        new CategoriesArrayResponseHandler()));
    }


    @Override
    public State<ApiQuery<Iterable<Category>>> serializable()
    {
        throw new UnsupportedOperationException("Serialization of CategoriesQuery is not implemented");
    }
}
