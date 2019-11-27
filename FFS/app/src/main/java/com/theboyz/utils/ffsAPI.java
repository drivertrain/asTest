package com.theboyz.utils;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
            System.out.println(e.getMessage() + "\nFROM ffsAPI_authenticate()");
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

    public static ArrayList<NFLPlayer> getPlayers(userAccount user, String year) throws Exception
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("URL", URL + "api/get_players");
        params.put("token", user.getToken());
        params.put("datatype", "keyvalue");
        params.put("year", year);

        JSONObject response = new apiPost().execute(params).get();
        if (!(response.getString("status").equals("success")))
            return null;

        JSONObject players = response.getJSONObject("data");
        ArrayList<String> playerKeys = (ArrayList) Helpers.getListFromIterator(players.keys());
        ArrayList<NFLPlayer> rVal = new ArrayList<>();
        for (int i = 0; i < playerKeys.size(); i++)
        {
            rVal.add(new NFLPlayer(players.getJSONObject(playerKeys.get(i))));
        }
        return rVal;
    }//End get players


    public static JSONObject getUserConfig(userAccount user) throws Exception
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("URL", URL + "api/get_user");
        params.put("token", user.getToken());
        params.put("datatype", "keyvalue");
        JSONObject response = new apiGet().execute(params).get();
        if (!(response.getString("status").equals("success")))
            response = null;
        else
            response = response.getJSONObject("user");

        return response;
    }//End getUserConfig



}//End class ffsAPI
