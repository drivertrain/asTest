package com.theboyz.utils;

import android.os.AsyncTask;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class apiGet extends AsyncTask<Map<String, String>, Void, JSONObject>
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
      String URL = params[0].get("URL");
      String token = params[0].get("token");

      HttpClient httpClient = new DefaultHttpClient();
      HttpContext localContext = new BasicHttpContext();
      HttpGet httpGet = new HttpGet(URL);
      httpGet.addHeader("Content-Type", "application/json");
      httpGet.addHeader("x-access-token", token);
      String resText = "";
      JSONObject res;
      try
      {
         HttpResponse response = httpClient.execute(httpGet, localContext);
         HttpEntity entity = response.getEntity();
         resText = getASCIIContentFromEntity(entity);
         res = new JSONObject(resText);
      }//End try

      catch (Exception e)
      {
         res = null;
      }//End catch
      return res;
   }//End do in background
}//End apiGet

