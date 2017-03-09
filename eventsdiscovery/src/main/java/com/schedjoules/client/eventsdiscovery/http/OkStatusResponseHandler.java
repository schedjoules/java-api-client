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

package com.schedjoules.client.eventsdiscovery.http;

import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;

import java.io.IOException;


/**
 * An {@link HttpResponseHandler} that returns whether the response had an {@link HttpStatus#OK} status code.
 * No other criteria is evaluated.
 *
 * @author Marten Gajda
 */
public final class OkStatusResponseHandler implements HttpResponseHandler<Boolean>
{
    @Override
    public Boolean handleResponse(HttpResponse response) throws IOException, ProtocolError, ProtocolException
    {
        response.responseEntity().contentStream().close();
        return HttpStatus.OK.equals(response.status());
    }
}
