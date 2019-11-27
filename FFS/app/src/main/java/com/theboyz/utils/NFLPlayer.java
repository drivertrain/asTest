package com.theboyz.utils;
import com.theboyz.ffs.R;

import org.json.*;

public class NFLPlayer
{
    private String name, playerID, team;
    private int season;
    private double totalTouches, leagueScore;
    private int imageResource;

    public NFLPlayer(String name, String playerID, int season, String team, double totalTouches, double leagueScore)
    {
        this.name = name;
        this.playerID = playerID;
        this.season = season;
        this.team = team;
        this.totalTouches = totalTouches;
        this.leagueScore = leagueScore;
    }

    public NFLPlayer(JSONObject player)
    {
        try
        {
            this.name = player.getString("name");
            this.playerID = player.getString("playerID");
            this.season = player.getInt("Season");
            this.team = player.getString("Team");
            this.leagueScore = player.getDouble("League Score");
            this.totalTouches = player.getDouble("Total Touches");
        }
        catch(Exception e)
        {
            System.out.println("ERROR MAKING NFLPLAYER FROM JSON");
        }
    }//End contstructor

    public String getName() { return this.name; }
    public String getScore() { return String.valueOf(this.leagueScore); }
    public String getTeam() { return this.team; }
    public String getID() { return this.playerID; }


    public int getImageResource() { return this.imageResource; }
    public void setImageResource(int value) { this.imageResource = value; }
}
