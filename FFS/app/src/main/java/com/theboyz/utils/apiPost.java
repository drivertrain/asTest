package com.theboyz.utils;

import java.util.*;
import android.os.AsyncTask;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class apiPost extends AsyncTask<Map<String, String>, Void, JSONObject>
{

    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException
    {
        InputStream in = entity.getContent();


        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n > 0)
        {
            byte[] b = new byte[4096];
            n = in.read(b);
            if (n > 0) out.append(new String(b, 0, n));
        }//End while
        return out.toString();
    }



    @Override
    protected JSONObject doInBackground(Map<String, String>... params)
    {
        /************************************************************************         *
         *THESE THREE KEYS ARE REQUIRED FOR THIS POST FUNCTION
         *IF A REQUEST IS NEEDED FOR A FUNCTION THAT DOESN'T REQUIRE A TOKEN
         *PUT AN EMPTY STRING FOR THE VALUE OF TOKEN
         * *********************************************************************/
        String URL = params[0].get("URL");
        String token = params[0].get("token");
        String config = params[0].get("datatype");


        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("x-access-token", token);
        JSONObject json = null, res;
        try
        {
            if (!config.equals("json"))
            {
                json = new JSONObject();
                //Add post Params
                for (String k : params[0].keySet())
                {
                    if ((!params[0].get(k).equals(URL)) && (!params[0].get(k).equals(token)) && (!params[0].get(k).equals(config)))
                    {
                        try
                        {
                            json.put(k, params[0].get(k));
                        } catch (Exception e)
                        {
                            continue;
                        }
                    }
                }
            }//End if
            else
                json = new JSONObject(params[0].get("json"));
            httpPost.setEntity(new StringEntity(json.toString()));
            HttpResponse response = httpClient.execute(httpPost, localContext);
            HttpEntity entity = response.getEntity();
            res = new JSONObject(getASCIIContentFromEntity(entity));
        }//End try

        catch (Exception e)
        {
            res = null;
        }//End catch

        return res;
    }//End do in background
}//End apiGet

