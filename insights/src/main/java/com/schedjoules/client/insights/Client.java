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

package com.schedjoules.client.insights;

import java.util.Locale;


/**
 * The interface of an object that gives some information about the client.
 *
 * @author Marten Gajda
 */
public interface Client
{
    /**
     * The id of the client.
     *
     * @return
     */
    CharSequence id();

    /**
     * The version of the client.
     *
     * @return
     */
    CharSequence version();

    /**
     * The name of the network operator, if available.
     *
     * @return
     */
    CharSequence networkOperator();

    /**
     * The current locale.
     *
     * @return
     */
    Locale locale();
}
