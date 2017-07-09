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

import org.dmfs.httpessentials.converters.PlainStringHeaderConverter;
import org.dmfs.httpessentials.headers.BasicSingletonHeaderType;
import org.dmfs.httpessentials.headers.HeaderType;
import org.dmfs.httpessentials.headers.SingletonHeaderType;
import org.dmfs.httpessentials.types.Token;

import java.util.Locale;


/**
 * Some static {@link HeaderType} definitions we use.
 *
 * @author Marten Gajda
 */
public final class HeaderTypes
{

    public final static SingletonHeaderType<Token> API_VERSION = new BasicSingletonHeaderType<>("api-version", TokenConverter.INSTANCE);
    public final static SingletonHeaderType<String> ETAG = new BasicSingletonHeaderType<>("etag", PlainStringHeaderConverter.INSTANCE);
    public final static SingletonHeaderType<Locale> ACCEPT_LANGUAGE = new BasicSingletonHeaderType<>("Accept-Language", LocaleToStringConverter.INSTANCE);


    private HeaderTypes()
    {
    }
}
