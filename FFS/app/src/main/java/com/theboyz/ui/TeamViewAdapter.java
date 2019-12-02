package com.theboyz.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theboyz.ffs.R;
import com.theboyz.utils.Helpers;
import com.theboyz.utils.NFLPlayer;
import com.theboyz.utils.userAccount;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;

public class TeamViewAdapter extends RecyclerView.Adapter <PickPlayerAdapter.ViewHolder>
{
   private ArrayList<NFLPlayer> masterPlayerList, playerList;
   private ArrayList<BasicNameValuePair> todo;
   private userAccount user;

   public TeamViewAdapter(ArrayList<NFLPlayer> playerList, ArrayList<BasicNameValuePair> todo, userAccount user)
   {
      this.playerList = playerList;
      this.masterPlayerList = playerList;
      this.todo = todo;
      this.user = user;
   }

   @NonNull
   @Override
   public TeamViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
   {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_view_card, parent, false);
      TeamViewHolder viewHolder = new TeamViewHolder(v, this.todo, this.user, this);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull TeamViewHolder holder, int position)
   {
      NFLPlayer currentItem = this.playerList.get(position);
      holder.playerName.setText(currentItem.getName());
      holder.playerScore.setText(currentItem.getScore());
      holder.player = currentItem;
      holder.player.setImageResource(Helpers.getImageId(holder.player.getTeam()));
      holder.playerImg.setImageResource(holder.player.getImageResource());


      holder.setPosition(position);
   }

   public void filterPlayers(String term)
   {
      this.playerList = new ArrayList<>();

      for (int i = 0; i < this.masterPlayerList.size(); i++)
      {
         if (this.masterPlayerList.get(i).getName().toLowerCase().contains(term.toLowerCase()))
            this.playerList.add(this.masterPlayerList.get(i));
      }//End for

      System.out.println(this.todo.size());
      notifyDataSetChanged();
   }

   public void removeFilters()
   {
      this.playerList = masterPlayerList;
      notifyDataSetChanged();
   }

   @Override
   public int getItemCount()
   {
      return this.playerList.size();
   }


   public static class TeamViewHolder extends RecyclerView.ViewHolder
   {
      //CONSTANTS
      private final TeamViewAdapter adapter;
      public final userAccount user;


      public int position;
      public ImageView playerImg;
      public TextView playerName;
      public TextView playerScore;
      public Button inspectButton;
      public NFLPlayer player;


      public TeamViewHolder(@NonNull View itemView, ArrayList<BasicNameValuePair> todo, userAccount user, TeamViewAdapter parent)
      {
         super(itemView);

         this.playerImg = itemView.findViewById(R.id.playerImg);
         this.playerName = itemView.findViewById(R.id.playerName);
         this.playerScore = itemView.findViewById(R.id.playerScore);
         this.inspectButton = itemView.findViewById(R.id.viewButton);

         this.user = user;
         this.adapter = parent;
         this.inspectButton.setOnClickListener(v -> this._on_player_click(itemView));
      }//End ViewHolder Constructor

      public void _on_player_click(View v)
      {
         System.out.println("view clicked");
      }


      public void setPosition(int val) { this.position = val; }

   }//End class ViewHolder
}
