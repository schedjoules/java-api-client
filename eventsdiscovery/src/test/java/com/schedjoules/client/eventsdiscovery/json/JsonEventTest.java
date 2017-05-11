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

package com.schedjoules.client.eventsdiscovery.json;

import org.dmfs.rfc5545.Duration;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * @author Marten Gajda
 */
public class JsonEventTest
{

    @Test
    public void testDuration() throws Exception
    {
        assertEquals(Duration.parse("PT123H"), new JsonEvent(new JSONObject("{\"duration\": \"PT123H\"}")).duration().value());
        assertEquals(Duration.parse("PT123H"), new JsonEvent(new JSONObject("{\"duration\": \"PT123H\"}")).duration().value(Duration.parse("P123D")));

        assertFalse(new JsonEvent(new JSONObject("{}")).duration().isPresent());
        assertEquals(Duration.parse("PT123H"), new JsonEvent(new JSONObject("{}")).duration().value(Duration.parse("PT123H")));

        assertFalse(new JsonEvent(new JSONObject("{\"duration\": null}")).duration().isPresent());
        assertEquals(Duration.parse("PT123H"), new JsonEvent(new JSONObject("{\"duration\": null}")).duration().value(Duration.parse("PT123H")));
    }

}