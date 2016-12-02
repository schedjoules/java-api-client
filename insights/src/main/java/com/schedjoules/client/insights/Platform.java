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

/**
 * In interface that provides information about the current platform.
 *
 * @author Marten Gajda
 */
public interface Platform
{
    /**
     * The name of the platform.
     *
     * @return "Android"
     */
    CharSequence name();

    /**
     * The name of the platform vendor.
     *
     * @return
     */
    CharSequence vendor();

    /**
     * The name of the device/model.
     *
     * @return
     */
    CharSequence device();

    /**
     * The version of the platform software.
     *
     * @return
     */
    CharSequence version();

    /**
     * Information about the display of the device.
     *
     * @return
     */
    Display display();
}
