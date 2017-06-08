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

package com.schedjoules.client.eventsdiscovery.json.utils;

import org.dmfs.httpessentials.types.Link;
import org.dmfs.iterators.Filter;


/**
 * A {@link Filter} to filter {@link Link}s by their reltypes.
 *
 * @author Marten Gajda
 */
public final class RelTypeFilter implements Filter<Link>
{
    private final String mRelType;


    public RelTypeFilter(String relType)
    {
        mRelType = relType;
    }


    @Override
    public boolean iterate(Link element)
    {
        return element.relationTypes().contains(mRelType);
    }
}
