package com.theboyz.utils;

import org.json.JSONObject;

public class NFLPlayerGame extends NFLPlayer
{
   private String gameDate;
   private int gameID, season;

   public NFLPlayerGame(JSONObject data)
   {
      super(data);
      try
      {
         this.gameDate = data.getString("date");
         this.gameID = data.getInt("game.id");
         this.season = data.getInt("Season");
      }
      catch (Exception e)
      {
         System.out.println("ERROR MAKING NFL GAME " + e.getMessage());
      }

   }//End constructor
   
   //Getters
   public String getDate() { return this.gameDate; }
   public int getGameID() { return this.gameID; }
   public int getSeason() { return this.season; }


}
