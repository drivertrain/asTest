package com.theboyz.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class WeightPickAdapter extends RecyclerView.Adapter <WeightPickAdapter.StatViewHolder>
{
    //INTEGER CONSTANTS FOR STATUS CODES
    public static final int ADD_PLAYER_IN_TODO = 1000;
    public static final int REMOVE_PLAYER_IN_TODO = 1001;
    public static final int PLAYER_OWNED = 1010;
    public static final int PLAYER_NOT_OWNED = 1011;

    private ArrayList<String> stats;
//    private userAccount user;

    public WeightPickAdapter(ArrayList<String> stats)
    {
        this.stats = stats;
    }

    @NonNull
    @Override
    public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_pick_card, parent, false);
        StatViewHolder viewHolder = new StatViewHolder(v, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatViewHolder holder, int position)
    {
        String currentItem = this.stats.get(position);
        holder.statName.setText(currentItem);
        holder.setPosition(position);
    }

    @Override
    public int getItemCount()
    {
        return this.stats.size();
    }


    public static class StatViewHolder extends RecyclerView.ViewHolder
    {
        //CONSTANTS
        private final WeightPickAdapter adapter;

        public int position;
        public EditText statInput;
        public TextView statName;

        public StatViewHolder(@NonNull View itemView, WeightPickAdapter parent)
        {
            super(itemView);

            this.adapter = parent;
            this.statInput = itemView.findViewById(R.id.statInput);
            this.statName = itemView.findViewById(R.id.statName);
        }//End ViewHolder Constructor

        public void setPosition(int val) { this.position = val; }

    }//End class ViewHolder
}
