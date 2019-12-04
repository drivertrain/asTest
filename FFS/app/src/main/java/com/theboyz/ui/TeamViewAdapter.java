package com.theboyz.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theboyz.ffs.MainActivity;
import com.theboyz.ffs.R;
import com.theboyz.ffs.ProfilePage;
import com.theboyz.utils.Helpers;
import com.theboyz.utils.NFLPlayer;
import com.theboyz.utils.userAccount;

import java.util.ArrayList;

public class TeamViewAdapter extends RecyclerView.Adapter <TeamViewAdapter.TeamViewHolder>
{
   private ArrayList<NFLPlayer>  playerList;
   private userAccount user;
   protected ProfilePage parent;

   public TeamViewAdapter(ArrayList<NFLPlayer> playerList, userAccount user, ProfilePage parent)
   {
      this.playerList = playerList;
      this.user = user;
      this.parent = parent;
   }

   @NonNull
   @Override
   public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
   {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_view_card, parent, false);
      TeamViewHolder viewHolder = new TeamViewHolder(v, this.user, this);
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

   @Override
   public int getItemCount()
   {
      if (this.playerList != null)
         return this.playerList.size();
      else
         return 0;
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


      public TeamViewHolder(@NonNull View itemView, userAccount user, TeamViewAdapter parent)
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
         this.adapter.parent._inspect_player(this.player.getID());
      }//End _on_player_click


      public void setPosition(int val) { this.position = val; }

   }//End class ViewHolder
}
