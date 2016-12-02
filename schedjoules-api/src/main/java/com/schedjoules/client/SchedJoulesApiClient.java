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

import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.decoration.Decoration;
import org.dmfs.httpessentials.decoration.HeaderDecorated;
import org.dmfs.httpessentials.headers.BasicSingletonHeaderType;
import org.dmfs.httpessentials.headers.HeaderType;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.typedentity.EntityConverter;


/**
 * An implementation of a client to the SchedJoules API.
 *
 * @author Marten Gajda
 */
public final class SchedJoulesApiClient implements ApiClient
{
    private final HeaderType<AccessToken> mAuthorizationHeaderType = new BasicSingletonHeaderType<>("Authorization", new EntityConverter<AccessToken>()
    {
        @Override
        public AccessToken value(String valueString)
        {
            throw new UnsupportedOperationException("Parsing Authorization headers is not supported.");
        }


        @Override
        public String valueString(AccessToken value)
        {
            return String.format("Token token=\"%s\"", value);
        }
    });

    private final AccessToken mAccessToken;


    public SchedJoulesApiClient(AccessToken accessToken)
    {
        mAccessToken = accessToken;
    }


    @Override
    public <T> HttpRequest<T> authorizedRequest(HttpRequest<T> request)
    {
        final HeaderType<AccessToken> authorizationHeaderType = mAuthorizationHeaderType;
        final AccessToken accessToken = mAccessToken;
        // just add the authorization header
        return new HeaderDecorated<>(request, new Decoration<Headers>()
        {
            @Override
            public Headers decorated(Headers original)
            {
                return original.withHeader(authorizationHeaderType.entity(accessToken));
            }
        });
    }
}
