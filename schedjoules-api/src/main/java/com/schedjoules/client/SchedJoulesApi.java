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

import com.schedjoules.client.http.Gzipping;
import com.schedjoules.client.http.Onymous;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestExecutor;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.executors.useragent.Branded;
import org.dmfs.httpessentials.types.Product;
import org.dmfs.httpessentials.types.VersionedProduct;

import java.io.IOException;
import java.net.URI;


/**
 * An implementation of the SchedJoules {@link Api}.
 *
 * @author Marten Gajda
 */
public final class SchedJoulesApi implements Api
{
    private final Product PRODUCT = new VersionedProduct(BuildConfig.NAME, BuildConfig.VERSION);
    private final URI API_BASE_URI = URI.create("https://api.schedjoules.com");

    private final ApiClient mClient;
    private final HttpRequestExecutor mExecutor;


    public SchedJoulesApi(ApiClient client, HttpRequestExecutor executor, UserIdentifier userIdentifier)
    {
        mClient = client;
        mExecutor = new Onymous(new Branded(new Gzipping(executor), PRODUCT), userIdentifier);
    }


    @Override
    public <T> T queryResult(URI path, HttpRequest<T> request) throws ProtocolException, ProtocolError, IOException
    {
        if (path.isAbsolute())
        {
            throw new IllegalArgumentException(String.format("Absolute URIs are not supported: %s", path.toASCIIString()));
        }
        return mExecutor.execute(API_BASE_URI.resolve(path), mClient.authorizedRequest(request));
    }
}
