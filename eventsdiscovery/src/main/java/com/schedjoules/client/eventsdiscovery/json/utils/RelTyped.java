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
import org.dmfs.iterators.decorators.Filtered;

import java.util.Iterator;


/**
 * Decorator for {@link Iterable}s of {@link Link}s which returns only links with a given reltype.
 *
 * @author Marten Gajda
 */
public final class RelTyped implements Iterable<Link>
{
    private final Iterable<Link> mDelegate;
    private final Filter<Link> mRelFilter;


    public RelTyped(Iterable<Link> delegate, String relType)
    {
        mDelegate = delegate;
        mRelFilter = new RelTypeFilter(relType);
    }


    @Override
    public Iterator<Link> iterator()
    {
        return new Filtered<>(mDelegate.iterator(), mRelFilter);
    }

}
