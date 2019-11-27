package com.theboyz.utils;

import java.util.ArrayList;
import org.json.JSONObject;
/**
 * userAccount class keeps track of details related to the users team
 * @author chaseuphaus
 */
public class userAccount
{
    private int userID;
    private boolean loggedIn;
    private String name;
    private String token;

    private String[] playerIDS;
    private String[] scoredStats;
    private double[] statWeights;

    /**
     * This Constructor runs if the user wishes to continues as a guest
     */
    public userAccount()
    {
        userID = -1;
        name = "null";
        loggedIn = false;
    }//end default constructor

    public userAccount(String token)
    {
        this.loggedIn = true;
        this.token = token;
    }

    //Setters
    public void setStats(String[] statsIn) { this.scoredStats = statsIn; }
    public void setWeights(double[] weightsIn) { this.statWeights = weightsIn; }
    public void setPlayers(String[] playersIn) { this.playerIDS = playersIn; }

    //Getters
    public String[] getStats() { return this.scoredStats; }
    public double[] getWeights() { return this.statWeights; }
    public String[] getPlayerIDS() { return this.playerIDS; }
    public int getID() { return this.userID; }
    public String getToken() { return this.token; }

    public boolean loggedIn() { return this.loggedIn; }

    /**
     * This function is used to generate a JSON version of the object to be sent to the DB
     * @return Object represented as a JSON
     */
    public JSONObject genAccJson()
    {
        JSONObject user = new JSONObject();
        try
        {
            user.put("id", this.userID);
            user.put("playerIDS", this.packString(this.playerIDS));
            user.put("scoredStats", this.packString(scoredStats));
            user.put("statWeights", this.packDouble(this.statWeights));
        }
        catch(Exception e)
        {
            user = null;
        }
        return user;
    }

    public void configureUser(JSONObject user)
    {
        try
        {
            this.playerIDS = this.parseStr(user.getString("playerIDS"));
            this.scoredStats = this.parseStr(user.getString("scoredStats"));
            this.statWeights = this.parseDouble(user.getString("statWeights"));
        }//End
        catch(Exception e)
        {
            System.out.println(e.getMessage() + "configureUserError");
        }
    }//End configureUser

    /**
     * packString converts a list/array of strings into an array formatted like a list from python
     * @param in String array to be converted into a python-esque list
     * @return String formatted to that of a python list
     */
    private String packString(String[] in)
    {
        String out = "[";
        for (int i = 0; i < in.length; i++)
        {
            out += "'" + in[i] + "'";
            if (i != in.length-1)
                out += ", ";
        }
        out += "]";
        return out;
    }//End packString

    /**
     * packDouble converts a list/array of doubles into an array formatted like a list from python
     * @param in double array to be converted in to a python-esque list
     * @return String formatted to that of a python list
     */
    private String packDouble(double[] in)
    {
        String out = "[";
        for (int i = 0; i < in.length; i++)
        {
            out += Double.toString(in[i]);
            if (i != in.length-1)
                out += ", ";
        }
        out += "]";
        return out.replaceAll(".0,", ",").replaceAll(".0\\]", "]");
    }//End packDouble


    /**
     * Formats string form of python list into an array of strings
     * @param in String in the form a python list
     * @return Array of strings
     */
    private String[] parseStr(String in)
    {
        in = in.replaceAll("'", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
        return in.split(",");
    }//End parseStr

    private double [] parseDouble(String in)
    {
        String[] weights = this.parseStr(in);
        ArrayList<Double> rList = new ArrayList<>();

        for (String w: weights)
        {
            rList.add(Double.valueOf(w));
        }

        double [] rval = new double[rList.size()];

        for (int i = 0; i < rList.size(); i++)
                rval[i] = rList.get(i);

        return rval;

    }

}//End class userAccount
