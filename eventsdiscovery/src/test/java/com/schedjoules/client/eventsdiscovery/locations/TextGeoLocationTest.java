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

package com.schedjoules.client.eventsdiscovery.locations;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Marten Gajda
 */
public class TextGeoLocationTest {
    @Test
    public void latitude() throws Exception {
        assertEquals(0, new TextGeoLocation("0,0").latitude(), 0.00001);
        assertEquals(5, new TextGeoLocation("5,0").latitude(), 0.00001);
        assertEquals(5.123, new TextGeoLocation("5.123,0").latitude(), 0.0001);
        assertEquals(-5.123, new TextGeoLocation("-5.123,0").latitude(), 0.0001);

        assertEquals(0, new TextGeoLocation("0,1.234").latitude(), 0.00001);
        assertEquals(5, new TextGeoLocation("5,1.234").latitude(), 0.00001);
        assertEquals(5.123, new TextGeoLocation("5.123,1.234").latitude(), 0.0001);
        assertEquals(-5.123, new TextGeoLocation("-5.123,1.234").latitude(), 0.0001);
    }

    @Test
    public void longitude() throws Exception {
        assertEquals(0, new TextGeoLocation("0,0").longitude(), 0.00001);
        assertEquals(5, new TextGeoLocation("0,5").longitude(), 0.00001);
        assertEquals(5.123, new TextGeoLocation("0,5.123").longitude(), 0.0001);
        assertEquals(-5.123, new TextGeoLocation("0,-5.123").longitude(), 0.0001);

        assertEquals(0, new TextGeoLocation("1.234,0").longitude(), 0.00001);
        assertEquals(5, new TextGeoLocation("1.234,5").longitude(), 0.00001);
        assertEquals(5.123, new TextGeoLocation("1.234,5.123").longitude(), 0.0001);
        assertEquals(-5.123, new TextGeoLocation("1.234,-5.123").longitude(), 0.0001);

    }

}