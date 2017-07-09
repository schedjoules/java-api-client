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

package com.schedjoules.client.utils;

import org.dmfs.httpessentials.typedentity.EntityConverter;

import java.util.Locale;


/**
 * {@link EntityConverter} from {@link Locale} to {@link String}.
 * <p>
 * (The other direction is not supported by this class.)
 *
 * @author Gabor Keszthelyi
 */
public final class LocaleToStringConverter implements EntityConverter<Locale>
{
    public static final LocaleToStringConverter INSTANCE = new LocaleToStringConverter();


    private LocaleToStringConverter()
    {
    }


    @Override
    public Locale value(String valueString)
    {
        /*
        Java7 has Locale.forLanguageTag() and corresponding locale.toLanguageTag() to comply with a standard,
        but it's not available under Android API 21.
        No standard method to parse the string to Local on Java6.
        See for example: https://stackoverflow.com/questions/2522248/how-to-get-locale-from-its-string-representation-in-java
         */
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    public String valueString(Locale locale)
    {
        return locale.toString().replace("_", "-");
    }
}
