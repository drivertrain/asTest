package com.theboyz.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.json.JSONObject;
import tech.tablesaw.api.Table;
import org.json.*;

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

    /**
     * Constructor the runs when the user correctly enters their credentials
     * @param id The id returned from the database if credentials are correct
     */
    public userAccount(int id)
    {
        loggedIn = true;
        this.userID = id;
//        this.getItems();
    }

    public userAccount(JSONObject data)
    {
        this.loggedIn = true;
        try
        {
            JSONObject user = data.getJSONObject("user");
            this.playerIDS = this.parseStr(user.getString("playerIDS"));
            this.scoredStats = this.parseStr(user.getString("scoredStats"));
            this.statWeights = this.parseDouble(user.getString("statWeights"));
            this.token = data.getString("token");
        }//End
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            this.loggedIn = false;
        }
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

    /**
     * This function is used to get the account info back into the form that it is stored on the database with (python style list as a string)
     * @return  Array of strings where each string in the array is a python-esque list
     */
    public String[] prepInfo()
    {
        //Set stats up to go into the db
        String stats = this.packString(this.scoredStats);
        String players = this.packString(this.playerIDS);

        //Set up weights to go into db
        String weights = this.packDouble(this.statWeights);

        return new String[] {stats, weights, players};

    }//End prepInfo()

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
     * getItems connects to the accountDB and pulls in the users team, stat, and weight info
     */
//    private void getItems()
//    {
//        if (!this.loggedIn)
//            return;
//
//        userDB users = new userDB();
//        try
//        {
//            this.events = users.pullEvents(this);
//        } catch (Exception e)
//        {
//            //Right here we should add something that asks the user to select their fantasy rules or just use the defaults
//            System.out.println(e.getMessage());
//        }//End Try/Catch
//
//    }//End getItems()

    /**
     * Parses the data regarding scoredStats, statWeights, and playerIDS that is returned from the database. Then is stores these values in the member functions
     * @param df Table returned from info.accInfo in the database
     */
    private void parseInfo(Table df)
    {
        try
        {
            //Get data from columns
            String stats = df.stringColumn("scoredStats").get(0);
            String weights = df.stringColumn("statWeights").get(0);
            String players = df.stringColumn("playerIDS").get(0);

            //Parse stats string and store in member variable
            this.scoredStats = this.parseStr(stats);
            this.playerIDS = this.parseStr(players);

            //Parse weights into double array
            String [] temp = this.parseStr(weights);
            ArrayList<Double> holder = new ArrayList<>();
            for (String val: temp)
                holder.add(Double.valueOf(val));

            //Convery ArrayList to primitive double array
            this.statWeights = new double[holder.size()];
            for (int i = 0; i < holder.size(); i++)
                this.statWeights[i] = holder.get(i);

        } catch (Exception E)
        {
            System.out.println("Account not setup. Will use default scoring info...");
        }
    }//End parseInfo

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

    /**
     * This function is called by the userDB class after the user modifies their account config (i.e. players, stats, or statWeights)
     */
//    protected void reload()
//    {
//        this.getItems();
//    }


}//End class userAccount
