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

package com.schedjoules.client.eventsdiscovery.http;

import com.schedjoules.client.eventsdiscovery.Category;
import com.schedjoules.client.eventsdiscovery.json.JsonArrayIterator;
import com.schedjoules.client.eventsdiscovery.json.JsonCategory;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.responsehandlers.StringResponseHandler;
import org.dmfs.iterators.Function;
import org.dmfs.iterators.decorators.Mapped;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;


/**
 * An {@link HttpResponseHandler} for responses that contain an array of {@link Category}s.
 *
 * @author Gabor Keszthelyi
 */
public final class CategoriesArrayResponseHandler implements HttpResponseHandler<Iterator<Category>>
{
    @Override
    public Iterator<Category> handleResponse(final HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        JSONArray categoriesArray = new JSONArray(new StringResponseHandler().handleResponse(response));
        return new Mapped<>(new JsonArrayIterator(categoriesArray),
                new Function<JSONObject, Category>()
                {
                    @Override
                    public Category apply(JSONObject element)
                    {
                        return new JsonCategory(element);
                    }
                });
    }
}
