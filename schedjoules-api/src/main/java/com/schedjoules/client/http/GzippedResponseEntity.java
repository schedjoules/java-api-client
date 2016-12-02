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

package com.schedjoules.client.http;

import org.dmfs.httpessentials.client.HttpResponseEntity;
import org.dmfs.httpessentials.types.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;


/**
 * An {@link HttpResponseEntity} that unzips the transferred content.
 *
 * @author Marten Gajda
 */
public final class GzippedResponseEntity implements HttpResponseEntity
{
    private final HttpResponseEntity mDecoratedResponseEntity;


    public GzippedResponseEntity(final HttpResponseEntity decoratedResponseEntity)
    {
        mDecoratedResponseEntity = decoratedResponseEntity;
    }


    @Override
    public MediaType contentType() throws IOException
    {
        // return the original content type
        return mDecoratedResponseEntity.contentType();
    }


    @Override
    public long contentLength() throws IOException
    {
        // we don't know the actual content-length
        return -1;
    }


    @Override
    public InputStream contentStream() throws IOException
    {
        return new GZIPInputStream(mDecoratedResponseEntity.contentStream());
    }
}
