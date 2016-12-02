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

package com.schedjoules.client.insights.steps;

import com.schedjoules.client.insights.Step;
import org.dmfs.rfc5545.DateTime;


/**
 * @author Marten Gajda
 */
public abstract class AbstractStep implements Step
{
    private final DateTime mTimeStamp;


    public AbstractStep()
    {
        mTimeStamp = DateTime.now();
    }


    @Override
    public final DateTime timeStamp()
    {
        return mTimeStamp;
    }
}
