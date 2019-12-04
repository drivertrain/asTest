package com.theboyz.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.theboyz.ffs.R;
import com.theboyz.utils.NFLPlayer;
import com.theboyz.utils.NFLPlayerGame;

import java.util.ArrayList;

public class InspectPlayerAdapter extends RecyclerView.Adapter <InspectPlayerAdapter.GameHolder>
{
   private ArrayList<NFLPlayerGame> gameList;


   public InspectPlayerAdapter(ArrayList<NFLPlayerGame> gameList)
   {
      this.gameList = gameList;
   }//End constructor


   @NonNull
   @Override
   public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
   {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_view_card, parent, false);
      return new GameHolder(v);
   }//onCreateViewHolder

   @Override
   public void onBindViewHolder(@NonNull GameHolder holder, int position)
   {
      NFLPlayerGame currentGame = this.gameList.get(position);
      holder.setGame(currentGame);
      holder.playerTouches.setText("Touches: " + currentGame.getTouches());
      holder.playerScore.setText("Points: " + currentGame.getScore());
      holder.gameDate.setText("Game Date: " + currentGame.getDate());
      holder.teamIcon.setImageResource(holder.game.getImageResource());

   }//END onBindViewHolder

   @Override
   public int getItemCount()
   {
      if (this.gameList == null)
         return 0;
      else
         return this.gameList.size();
   }//End getItemCount

   public class GameHolder extends ViewHolder
   {
      public TextView playerScore, playerTouches, gameDate;
      public Button inspectButton;
      public ImageView teamIcon;
      NFLPlayerGame game;

      public GameHolder(@NonNull View itemView)
      {
         super(itemView);

         //Bind Variables to UI elements
         this.playerScore = itemView.findViewById(R.id.playerScore);
         this.gameDate = itemView.findViewById(R.id.gameDate);
         this.playerTouches = itemView.findViewById(R.id.amountTouches);
         this.inspectButton = itemView.findViewById(R.id.viewGameButton);
         this.teamIcon = itemView.findViewById(R.id.playerImg);

         this.game = game;

         //Set onclick listener for the view game button
         this.inspectButton.setOnClickListener(v -> this._on_player_click(itemView));
      }

      public void setGame(NFLPlayerGame game) { this.game = game; }

      public void _on_player_click(View v)
      {
         System.out.println("view game pressed for game: " + this.game.getID());
      }//End _on_player_click
   }

}
