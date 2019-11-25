package com.theboyz.utils;

import org.json.JSONObject;

import java.util.HashMap;

public class ffsAPI
{
    private static final String URL = "http://99.179.141.41:25565/";

    public static JSONObject authenticate(String email, String password)
    {
        JSONObject rVal;
        try
        {
            HashMap<String, String> params = new HashMap<>();
            params.put("URL", URL + "api/login");
            params.put("token", "NONE");
            params.put("datatype", "keyvalue");
            params.put("email", email);
            params.put("password", password);
            JSONObject response = new apiPost().execute(params).get();
            if (!(response.getString("status").equals("success")))
                rVal = null;
            else
                rVal = response;

        }
        catch (Exception e)
        {
            rVal = null;
        }
        return rVal;
    }//End authenticate

    public static boolean updateLeagueConfig(userAccount user) throws Exception
    {

        boolean status = true;

        HashMap<String, String> params = new HashMap<>();
        params.put("URL", URL + "api/set_team_rules");
        params.put("token", user.getToken());
        params.put("datatype", "json");
        params.put("json", user.genAccJson().toString());

        JSONObject response = new apiPost().execute(params).get();

        return status;
    }//End updateLeagueConfig



}//End class ffsAPI
