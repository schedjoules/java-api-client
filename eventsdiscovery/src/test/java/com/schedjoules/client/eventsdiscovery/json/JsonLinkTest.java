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

import org.dmfs.httpessentials.converters.PlainStringHeaderConverter;
import org.dmfs.httpessentials.parameters.BasicParameterType;
import org.dmfs.httpessentials.parameters.ParameterType;
import org.dmfs.httpessentials.types.StructuredMediaType;
import org.json.JSONObject;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Marten Gajda
 */
public class JsonLinkTest
{
    @Test
    public void target() throws Exception
    {
        assertEquals(URI.create("http://example.com"), new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\"\n" +
                "}")).target());
    }


    @Test
    public void context() throws Exception
    {
        assertEquals(URI.create("http://test.com"), new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\"\n" +
                "}")).context(URI.create("http://test.com")));
    }


    @Test
    public void languages() throws Exception
    {
        // TODO: implement test
    }


    @Test
    public void title() throws Exception
    {
        assertEquals("link-title", new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"link-title\",\n" +
                "  \"href\": \"http://example.com\"\n" +
                "}")).title());
    }


    @Test
    public void mediaType() throws Exception
    {
        assertEquals(new StructuredMediaType("application", "xml"), new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"link-title\",\n" +
                "  \"href\": \"http://example.com\",\n" +
                "  \"type\": \"application/xml\"\n" +
                "}")).mediaType());
    }


    @Test
    public void relationTypes() throws Exception
    {
        assertEquals(new HashSet<>(Arrays.asList("rel-type")), new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\"\n" +
                "}")).relationTypes());
    }


    @Test
    public void reverseRelationTypes() throws Exception
    {
        assertEquals(new HashSet<>(), new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\"\n" +
                "}")).reverseRelationTypes());
    }


    @Test
    public void firstParameter() throws Exception
    {
        ParameterType<String> parameterType = new BasicParameterType<>("test", new PlainStringHeaderConverter());
        assertEquals("123", new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\",\n" +
                "  \"properties\": {\n" +
                "     \"test\": \"123\"\n" +
                "  }\n" +
                "}")).firstParameter(parameterType, "fail").value());
    }


    @Test
    public void firstParameter2() throws Exception
    {
        ParameterType<String> parameterType = new BasicParameterType<>("test", new PlainStringHeaderConverter());
        assertEquals("123", new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\",\n" +
                "  \"properties\": {\n" +
                "  }\n" +
                "}")).firstParameter(parameterType, "123").value());
    }

    @Test
    public void firstParameter3() throws Exception
    {
        ParameterType<String> parameterType = new BasicParameterType<>("test", new PlainStringHeaderConverter());
        assertEquals("123", new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\"\n" +
                "}")).firstParameter(parameterType, "123").value());
    }

    @Test
    public void parameters() throws Exception
    {
        // TODO: implement test
    }


    @Test
    public void hasParameter() throws Exception
    {
        ParameterType<String> parameterType = new BasicParameterType<>("test", new PlainStringHeaderConverter());
        assertTrue(new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\",\n" +
                "  \"properties\": {\n" +
                "    \"test\": \"123\"\n" +
                "  }\n" +
                "}")).hasParameter(parameterType));
    }


    @Test
    public void hasParameter2() throws Exception
    {
        ParameterType<String> parameterType = new BasicParameterType<>("test", new PlainStringHeaderConverter());
        assertFalse(new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\",\n" +
                "  \"properties\": {\n" +
                "  }\n" +
                "}")).hasParameter(parameterType));
    }


    @Test
    public void hasParameter3() throws Exception
    {
        ParameterType<String> parameterType = new BasicParameterType<>("test", new PlainStringHeaderConverter());
        assertFalse(new JsonLink(new JSONObject("{\n" +
                "  \"rel\": \"rel-type\",\n" +
                "  \"title\": \"title\",\n" +
                "  \"href\": \"http://example.com\"\n" +
                "}")).hasParameter(parameterType));
    }
}