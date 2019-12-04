package com.theboyz.utils;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class apiGetTest
{
   public apiGet obj;


   @Before
   public void setUp() throws Exception
   {
      obj = new apiGet();
   }


   @Test
   public void doInBackground()
   {
      HashMap<String, String> params = new HashMap<>();
      params.put("URL", "http://99.179.141.41:25565/api/this_page_doesnt_exist");
      params.put("token", "no_token");
      params.put("datatype", "keyvalue");
      JSONObject test;

      try
      {
         test = obj.execute(params).get();
      }

      catch (Exception e)
      {
         System.out.println(e);
         test = new JSONObject();
      }

      assertNull(test);
   }
}