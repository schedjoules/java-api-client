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

package com.schedjoules.client.http;

import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.executors.urlrewriting.RewritePolicy;
import org.dmfs.iterators.Filter;
import org.dmfs.iterators.decorators.Filtered;
import org.dmfs.rfc3986.Uri;
import org.dmfs.rfc3986.encoding.Precoded;
import org.dmfs.rfc3986.encoding.XWwwFormUrlEncoded;
import org.dmfs.rfc3986.parameters.Parameter;
import org.dmfs.rfc3986.parameters.ParameterList;
import org.dmfs.rfc3986.parameters.adapters.XwfueParameterList;
import org.dmfs.rfc3986.parameters.parametersets.Appending;
import org.dmfs.rfc3986.parameters.parametersets.EmptyParameterList;
import org.dmfs.rfc3986.queries.SimpleQuery;
import org.dmfs.rfc3986.uris.LazyUri;
import org.dmfs.rfc3986.uris.RelativeUri;
import org.dmfs.rfc3986.uris.Resolved;
import org.dmfs.rfc3986.uris.Text;

import java.net.URI;


/**
 * A {@link RewritePolicy} which makes sure that every request URL contains a given parameter.
 * <p>
 * Note, if the parameter already exists in the request URI, it's not added again (even if the existing parameter has a different value).
 *
 * @author Marten Gajda
 */
public final class ParameterExistsPolicy implements RewritePolicy
{

    private final Parameter mParameter;


    public ParameterExistsPolicy(Parameter parameter)
    {
        mParameter = parameter;
    }


    @Override
    public URI rewritten(URI location, HttpRequest<?> request)
    {
        // for now we need to convert to Uri
        // TODO: remove when http-client-essentials uses Uri for request URIs
        Uri uri = new LazyUri(new Precoded(location.toASCIIString()));

        // TODO: XwfueParameterList expects an Optional<UriEncoded> but it should be Optional<? extends UriEncoded>, hence the ugly workaround
        ParameterList parameters = uri.query().isPresent() ? new XwfueParameterList(uri.query().value()) : new EmptyParameterList();

        // TODO: this is not ideal. Uri-toolkit needs a good way to test for the existence of a parameter if no ParameterType is known.
        // Or should we create a fake type that shares the name with mParameter?
        if (new Filtered<>(parameters.iterator(), new ParameterNameFilter(mParameter.name().toString())).hasNext())
        {
            // parameter already exists, return URI as is
            return location;
        }

        return toURI(new Resolved(uri, new RelativeUri(new SimpleQuery(new XWwwFormUrlEncoded(new Appending(parameters, mParameter))))));
    }


    /**
     * Temporary method to convert Uri to Java's URI. To be removed when http-client-essentials uses uri-toolkit for request URIs.
     *
     * @param uri
     *
     * @return
     */
    private URI toURI(Uri uri)
    {
        return URI.create(new Text(uri).toString());
    }


    private final static class ParameterNameFilter implements Filter<Parameter>
    {
        private final String mParamName;


        private ParameterNameFilter(String paramName)
        {
            mParamName = paramName;
        }


        @Override
        public boolean iterate(Parameter argument)
        {
            return argument.name().toString().equals(mParamName);
        }
    }
}
