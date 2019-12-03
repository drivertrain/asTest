package com.theboyz.ffs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.theboyz.ui.CardViewOffset;
import com.theboyz.ui.PickPlayerAdapter;
import com.theboyz.ui.TeamViewAdapter;
import com.theboyz.utils.*;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfilePage extends AppCompatActivity
{
   private RecyclerView recyclerView;
   private TeamViewAdapter rAdapter;
   private RecyclerView.LayoutManager rLayoutManager;
   private RecyclerView.ItemDecoration rItemDecorator;
   private ArrayList<NFLPlayer> players;
   private userAccount user;
   private Button changeTeam, changeScoring;
   private TextView greetingTag;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_profile_page);

      try
      {
         JSONObject loginResponse = new JSONObject(getIntent().getStringExtra("loginResponse"));
         this.user = new userAccount(loginResponse.getString("token"), loginResponse.getInt("id"));
         JSONObject userConfig = ffsAPI.getUserConfig(this.user);
         this.user.configureUser(userConfig);
         System.out.println(userConfig.toString());

         if (userConfig == null || (this.user.getPlayerIDS().length == 0))
         {
            setResult(MainActivity.USER_NOT_CONFIGURED, this.getIntent());
            finish();
            return;
         }//END IF

         players = ffsAPI.getTeam(this.user);

         this.changeTeam = findViewById(R.id.changeTeam);
         this.changeScoring = findViewById(R.id.changeScoring);
      }//End try

      catch(Exception e)
      {
         for (StackTraceElement el: e.getStackTrace())
            System.out.println(el.toString());

      }//End catch

      this.recyclerView = findViewById(R.id.teamView);
      this.rItemDecorator = new CardViewOffset(this, R.dimen.card_view_offset);
      this.rLayoutManager = new LinearLayoutManager(this);
      this.rAdapter = new TeamViewAdapter(players, this.user);

      this.recyclerView.setLayoutManager(this.rLayoutManager);
      this.recyclerView.addItemDecoration(this.rItemDecorator);
      this.recyclerView.setAdapter(this.rAdapter);
      this.greetingTag = findViewById(R.id.greetingLabel);
      this.greetingTag.setText("Welcome " + this.user.getName());
   }//End onCreate


   public void _change_team(View v)
   {
      setResult(MainActivity.PICK_PLAYER_REQUEST);
      finish();
   }//End change team

   public void _change_scoring(View v)
   {
      setResult(MainActivity.STAT_PICK_REQUEST_CODE);
      finish();
   }//End change scoring

}//End ProfilePage


