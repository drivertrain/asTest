package com.theboyz.utils;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public class apiGet extends AsyncTask<String, Void, String>
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
   protected String doInBackground(String... params)
   {
      String url = params[0];
      String token = params[1];
      HttpClient httpClient = new DefaultHttpClient();
      HttpContext localContext = new BasicHttpContext();
      HttpGet httpGet = new HttpGet(params[0]);
      httpGet.addHeader("Content-Type", "application/json");
      httpGet.addHeader("x-access-token", token);
      String res = "";
      try
      {
         HttpResponse response = httpClient.execute(httpGet, localContext);
         HttpEntity entity = response.getEntity();
         res = getASCIIContentFromEntity(entity);
      }//End try

      catch (Exception e)
      {
         return e.getMessage();
      }//End catch
      return res;
   }//End do in background
}//End apiGet

class apiPost extends AsyncTask<String, Void, String>
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
   protected String doInBackground(String... params)
   {
      String url = params[0];
      String token = params[1];
      HttpClient httpClient = new DefaultHttpClient();
      HttpContext localContext = new BasicHttpContext();
      HttpPost httpPost = new HttpPost(params[0]);
      httpPost.addHeader("x-access-token", token);
      String res = "";
      try
      {
         HttpResponse response = httpClient.execute(httpPost, localContext);
         HttpEntity entity = response.getEntity();
         res = getASCIIContentFromEntity(entity);
      }//End try

      catch (Exception e)
      {
         return null;
      }//End catch
      return res;
   }//End do in background
}//End apiGet

