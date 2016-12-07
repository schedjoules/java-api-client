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

import com.schedjoules.client.Api;
import com.schedjoules.client.ApiQuery;
import com.schedjoules.client.insights.utils.JsonRequestEntity;
import com.schedjoules.client.utils.ApiVersionHeaders;
import org.dmfs.httpessentials.HttpMethod;
import org.dmfs.httpessentials.HttpStatus;
import org.dmfs.httpessentials.client.HttpRequest;
import org.dmfs.httpessentials.client.HttpRequestEntity;
import org.dmfs.httpessentials.client.HttpResponse;
import org.dmfs.httpessentials.client.HttpResponseHandler;
import org.dmfs.httpessentials.exceptions.ProtocolError;
import org.dmfs.httpessentials.exceptions.ProtocolException;
import org.dmfs.httpessentials.headers.Headers;
import org.dmfs.httpessentials.responsehandlers.TrivialResponseHandler;
import org.dmfs.rfc5545.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * An {@link ApiQuery} to post insights steps to the API.
 *
 * @author Marten Gajda
 */
public final class InsightsRequest implements ApiQuery<Boolean>
{
    private final static URI QUERY_PATH = URI.create("/insights");

    private final Session mSession;
    private final Platform mPlatform;
    private final Client mClient;
    private final Iterable<Step> mDataPoints;


    public InsightsRequest(Session session, Platform platform, Client client, Iterable<Step> dataPoints)
    {
        mSession = session;
        mPlatform = platform;
        mClient = client;
        mDataPoints = dataPoints;
    }


    @Override
    public Boolean queryResult(Api api) throws IOException, URISyntaxException, ProtocolError, ProtocolException
    {
        final JSONObject requestData = new JSONObject();
        JSONArray steps = new JSONArray();
        for (Step step : mDataPoints)
        {
            JSONObject dataP = new JSONObject();
            dataP.put("timestamp", formattedTime(step.timeStamp()));
            dataP.put("category", step.category().toString());
            dataP.put("uri", step.data().toASCIIString());
            if (step.event() != null)
            {
                dataP.put("event-id", step.event().uid());
            }
            steps.put(dataP);
        }
        requestData.put("session", sessionObject());
        requestData.put("platform", platformObject());
        requestData.put("client", clientObject());
        requestData.put("steps", steps);
        return api.queryResult(QUERY_PATH, new HttpRequest<Boolean>()
        {
            @Override
            public HttpMethod method()
            {
                return HttpMethod.POST;
            }


            @Override
            public Headers headers()
            {
                return new ApiVersionHeaders("1");
            }


            @Override
            public HttpRequestEntity requestEntity()
            {
                return new JsonRequestEntity(requestData);
            }


            @Override
            public HttpResponseHandler<Boolean> responseHandler(HttpResponse response) throws IOException, ProtocolError, ProtocolException
            {
                return new TrivialResponseHandler<>(HttpStatus.OK.equals(response.status()));
            }
        });
    }


    private JSONObject sessionObject()
    {
        JSONObject result = new JSONObject();
        result.put("id", mSession.id().toString());
        result.put("start", formattedTime(mSession.start()));
        result.put("user-id", mSession.user().toString());
        if (mSession.end() != null)
        {
            result.put("end", formattedTime(mSession.end()));
        }
        return result;
    }


    private JSONObject clientObject()
    {
        JSONObject result = new JSONObject();
        result.put("id", mClient.id().toString());
        result.put("version", mClient.version().toString());
        result.put("network-operator", mClient.networkOperator().toString());
        result.put("locale", mClient.locale().toString());
        return result;
    }


    private JSONObject platformObject()
    {
        JSONObject result = new JSONObject();
        result.put("name", mPlatform.name().toString());
        result.put("version", mPlatform.version().toString());
        result.put("vendor", mPlatform.vendor().toString());
        result.put("device", mPlatform.device().toString());
        if (mPlatform.display() != null)
        {
            result.put("display", displayObject());
        }
        return result;
    }


    private JSONObject displayObject()
    {
        JSONObject result = new JSONObject();
        result.put("width", mPlatform.display().width());
        result.put("height", mPlatform.display().height());
        result.put("dpi", mPlatform.display().dpi());
        return result;
    }


    private String formattedTime(DateTime dateTime)
    {
        DateTime utcDateTime = dateTime.shiftTimeZone(DateTime.UTC);
        return String.format("%04d-%02d-%02dT%02d:%02d:%02dZ", utcDateTime.getYear(), utcDateTime.getMonth() + 1, utcDateTime.getDayOfMonth(),
                utcDateTime.getHours(), utcDateTime.getMinutes(),
                utcDateTime.getSeconds());
    }
}
