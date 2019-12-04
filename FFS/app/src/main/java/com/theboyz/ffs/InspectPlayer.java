package com.theboyz.ffs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.theboyz.ui.CardViewOffset;
import com.theboyz.ui.InspectPlayerAdapter;
import com.theboyz.ui.TeamViewAdapter;
import com.theboyz.utils.NFLPlayer;
import com.theboyz.utils.NFLPlayerGame;
import com.theboyz.utils.ffsAPI;
import com.theboyz.utils.userAccount;

import org.json.JSONObject;

import java.util.ArrayList;

public class InspectPlayer extends AppCompatActivity
{
   private String playerID;
   private ArrayList<NFLPlayerGame> games;
   private userAccount user;

   private RecyclerView recyclerView;
   private InspectPlayerAdapter rAdapter;
   private RecyclerView.LayoutManager rLayoutManager;
   private RecyclerView.ItemDecoration rItemDecorator;

   private TextView playerName, playerTeam;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_inspect_player);

      try
      {
         this.playerID = getIntent().getStringExtra("playerID");
         JSONObject loginResponse = new JSONObject(getIntent().getStringExtra("loginResponse"));

         this.user = new userAccount(loginResponse.getString("token"), loginResponse.getInt("id"));
         this.games = ffsAPI.getGamesForPlayer(this.user, this.playerID, "2018");


      }//End try

      catch(Exception e)
      {
         for (StackTraceElement el: e.getStackTrace())
            System.out.println(el.toString());

      }//End catch

      this.playerName = findViewById(R.id.playerName);
      this.playerTeam = findViewById(R.id.playerTeam);

      //Set up recycler view
      this.recyclerView = findViewById(R.id.gameViewer);
      this.rItemDecorator = new CardViewOffset(this, R.dimen.card_view_offset);
      this.rLayoutManager = new LinearLayoutManager(this);
      this.rAdapter = new InspectPlayerAdapter(games);

      this.recyclerView.setLayoutManager(this.rLayoutManager);
      this.recyclerView.addItemDecoration(this.rItemDecorator);
      this.recyclerView.setAdapter(this.rAdapter);

      if (games != null && games.size() > 0)
      {
         this.playerName.setText(games.get(0).getName());
         this.playerTeam.setText(games.get(0).getTeam());
      }//End if

   }//End onCreate


   public void _back_button_click(View v)
   {
      setResult(MainActivity.PROFILE_REQUEST, this.getIntent());
      finish();
   }//End _back_button_click
}
