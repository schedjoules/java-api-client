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
import org.dmfs.rfc3986.parameters.Parameter;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


/**
 * @author Marten Gajda
 */
public class ParameterExistsPolicyTest
{
    private final static class TestParam implements Parameter
    {
        private final CharSequence mName;
        private final CharSequence mValue;


        private TestParam(CharSequence name, CharSequence value)
        {
            mName = name;
            mValue = value;
        }


        @Override
        public CharSequence name()
        {
            return mName;
        }


        @Override
        public CharSequence textValue()
        {
            return mValue;
        }
    }


    @Test
    public void testRewrittenExisting() throws Exception
    {
        // parameter exists, nothing changed
        assertEquals(URI.create("http://example.com/?u=123%3D%26"),
                new ParameterExistsPolicy(new TestParam("u", "xyz")).rewritten(URI.create("http://example.com/?u=123%3D%26"), mock(HttpRequest.class)));
        assertEquals(URI.create("http://example.com/?u=123&r=ab%3D%26c"),
                new ParameterExistsPolicy(new TestParam("u", "xyz")).rewritten(URI.create("http://example.com/?u=123&r=ab%3D%26c"), mock(HttpRequest.class)));
        assertEquals(URI.create("http://example.com/?r=abc&u=12%3D%263"),
                new ParameterExistsPolicy(new TestParam("u", "xyz")).rewritten(URI.create("http://example.com/?r=abc&u=12%3D%263"), mock(HttpRequest.class)));
    }


    @Test
    public void testRewrittenNonExisting() throws Exception
    {
        assertEquals(URI.create("http://example.com/?u=xyz"),
                new ParameterExistsPolicy(new TestParam("u", "xyz")).rewritten(URI.create("http://example.com/?"), mock(HttpRequest.class)));
        assertEquals(URI.create("http://example.com/?u=xyz"),
                new ParameterExistsPolicy(new TestParam("u", "xyz")).rewritten(URI.create("http://example.com/"), mock(HttpRequest.class)));
        assertEquals(URI.create("http://example.com/?r=ab%3D%26c&u=xyz"),
                new ParameterExistsPolicy(new TestParam("u", "xyz")).rewritten(URI.create("http://example.com/?r=ab%3D%26c"), mock(HttpRequest.class)));
        assertEquals(URI.create("http://example.com/?r=ab%3D%26c&x=123&u=xyz"),
                new ParameterExistsPolicy(new TestParam("u", "xyz")).rewritten(URI.create("http://example.com/?r=ab%3D%26c&x=123"), mock(HttpRequest.class)));
    }

}