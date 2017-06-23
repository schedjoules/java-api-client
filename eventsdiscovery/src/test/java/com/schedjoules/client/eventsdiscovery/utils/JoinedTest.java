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

package com.schedjoules.client.eventsdiscovery.utils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * Unit test for {@link Joined}.
 *
 * @author Gabor Keszthelyi
 */
public class JoinedTest
{

    @Test
    public void testVariousCases()
    {
        assertEquals("A,B,C", new Joined(",", Arrays.<CharSequence>asList("A", "B", "C")).toString());

        assertEquals("", new Joined(",", Arrays.<CharSequence>asList()).toString());

        assertEquals("A", new Joined(",", Arrays.<CharSequence>asList("A")).toString());

        assertEquals("A | B | C", new Joined(" | ", Arrays.<CharSequence>asList("A", "B", "C")).toString());
    }

}