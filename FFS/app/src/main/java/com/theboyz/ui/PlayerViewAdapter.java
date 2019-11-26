package com.theboyz.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theboyz.ffs.R;

import java.util.ArrayList;

public class PlayerViewAdapter extends RecyclerView.Adapter <PlayerViewAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView playerImg;
        public TextView playerName;
        public TextView playerScore;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.playerImg = itemView.findViewById(R.id.playerImg);
            this.playerName = itemView.findViewById(R.id.playerName);
            this.playerScore = itemView.findViewById(R.id.playerScore);
        }//End ViewHolder Constructor
    }//End class ViewHolder

    private ArrayList<PlayerViewItem> masterPlayerList, playerList;

    public PlayerViewAdapter(ArrayList<PlayerViewItem> playerList)
    {
        this.playerList = playerList;
        this.masterPlayerList = playerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_view_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PlayerViewItem currentItem = this.playerList.get(position);
        holder.playerImg.setImageResource(currentItem.getImageResource());
        holder.playerName.setText(currentItem.getPlayerName());
        holder.playerScore.setText(currentItem.getScore());
    }

    public void filterPlayers(String term)
    {
        this.playerList = new ArrayList<>();
        for (int i = 0; i < this.masterPlayerList.size(); i++)
        {
            System.out.println("LHS: " + this.masterPlayerList.get(i).getPlayerName().toLowerCase());
            System.out.println("RHS: " + term.toLowerCase() + "\n\n\n");
            if (this.masterPlayerList.get(i).getPlayerName().toLowerCase().contains(term.toLowerCase()))
                this.playerList.add(this.masterPlayerList.get(i));
        }
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
}
