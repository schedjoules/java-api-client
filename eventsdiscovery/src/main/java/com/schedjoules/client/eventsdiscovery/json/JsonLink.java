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

package com.schedjoules.client.eventsdiscovery.json;

import org.dmfs.httpessentials.parameters.Parameter;
import org.dmfs.httpessentials.parameters.ParameterType;
import org.dmfs.httpessentials.types.Link;
import org.dmfs.httpessentials.types.MediaType;
import org.dmfs.httpessentials.types.StringMediaType;
import org.dmfs.iterators.AbstractConvertedIterator;
import org.dmfs.iterators.ConvertedIterator;
import org.dmfs.iterators.EmptyIterator;
import org.dmfs.iterators.FilteredIterator;
import org.dmfs.iterators.filters.AnyOf;
import org.json.JSONObject;

import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;


/**
 * A {@link Link} based on a JSON link object.
 *
 * @author Marten Gajda
 * @see <a href="http://tools.ietf.org/html/rfc7033#section-4.4.4">RFC 7033, section 4.4.4</a>
 */
public final class JsonLink implements Link
{
    private static final String KEY_HREF = "href";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PROPERTIES = "properties";
    private static final String KEY_TYPE = "type";
    private static final String KEY_REL = "rel";

    private final JSONObject mJsonObject;


    public JsonLink(JSONObject jsonObject)
    {
        mJsonObject = jsonObject;
    }


    @Override
    public URI target()
    {
        if (!mJsonObject.has(KEY_HREF) || mJsonObject.isNull(KEY_HREF))
        {
            return null;
        }
        return URI.create(mJsonObject.optString(KEY_HREF, null));
    }


    @Override
    public URI context(URI defaultContext)
    {
        return defaultContext;
    }


    @Override
    public Set<Locale> languages()
    {
        return Collections.emptySet();
    }


    @Override
    public String title()
    {
        return mJsonObject.isNull(KEY_TITLE) ? null : mJsonObject.optString(KEY_TITLE, null);
    }


    @Override
    public MediaType mediaType()
    {
        String mediaType = mJsonObject.isNull(KEY_TYPE) ? null : mJsonObject.optString(KEY_TYPE, null);
        return mediaType == null || mediaType.isEmpty() ? null : new StringMediaType(mediaType);
    }


    @Override
    public Set<String> relationTypes()
    {
        return Collections.singleton(mJsonObject.optString(KEY_REL));
    }


    @Override
    public Set<String> reverseRelationTypes()
    {
        return Collections.emptySet();
    }


    @Override
    public <T> Parameter<T> firstParameter(ParameterType<T> parameterType, T defaultValue)
    {
        Iterator<Parameter<T>> result = parameters(parameterType);
        return !result.hasNext() ? parameterType.entity(defaultValue) : result.next();
    }


    @Override
    public <T> Iterator<Parameter<T>> parameters(final ParameterType<T> parameterType)
    {
        final JSONObject properties = mJsonObject.getJSONObject(KEY_PROPERTIES);
        return properties == null ? EmptyIterator.<Parameter<T>>instance() : new ConvertedIterator<>(
                new FilteredIterator<>(properties.keys(), new AnyOf<>(parameterType.name())),
                new AbstractConvertedIterator.Converter<Parameter<T>, String>()
                {
                    @Override
                    public Parameter<T> convert(String element)
                    {
                        return parameterType.entityFromString(properties.getString(element));
                    }
                });
    }


    @Override
    public <T> boolean hasParameter(ParameterType<T> parameterType)
    {
        JSONObject properties = mJsonObject.getJSONObject(KEY_PROPERTIES);
        return properties != null && properties.has(parameterType.name());
    }
}
