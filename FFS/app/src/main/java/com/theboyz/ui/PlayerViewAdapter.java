package com.theboyz.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theboyz.ffs.R;
import com.theboyz.utils.NFLPlayer;
import com.theboyz.utils.userAccount;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.Boolean.FALSE;

public class PlayerViewAdapter extends RecyclerView.Adapter <PlayerViewAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView playerImg;
        public TextView playerName;
        public TextView playerScore;
        public Button changeButton;
        public NFLPlayer player;
        public final ArrayList<BasicNameValuePair> todo;
        public final userAccount user;
        public int position;
        private final PlayerViewAdapter adapter;

        public ViewHolder(@NonNull View itemView, ArrayList<BasicNameValuePair> todo, userAccount user, PlayerViewAdapter parent)
        {
            super(itemView);
            this.playerImg = itemView.findViewById(R.id.playerImg);
            this.playerName = itemView.findViewById(R.id.playerName);
            this.playerScore = itemView.findViewById(R.id.playerScore);
            this.changeButton = itemView.findViewById(R.id.addButton);
            this.user = user;
            this.todo = todo;
            this.adapter = parent;
            this.changeButton.setOnClickListener(v -> this._on_player_click());
        }//End ViewHolder Constructor

        public void _on_player_click()
        {

            if (Arrays.asList(this.user.getPlayerIDS()).contains(this.player.getID()))
            {
                todo.add(new BasicNameValuePair("remove", this.player.getID()));
            }
            else
                todo.add(new BasicNameValuePair("add", this.player.getID()));
            this.changeButton.setVisibility(View.INVISIBLE);
            this.changeButton.setEnabled(FALSE);
        }
        
        public void setButtonText()
        {
            if (Arrays.asList(this.user.getPlayerIDS()).contains(this.player.getID()))
                this.changeButton.setText(R.string.remove_button_text);
            else
                this.changeButton.setText(R.string.add_button_text);
        }

        public void setPosition(int val) { this.position = val; }
    }//End class ViewHolder

    private ArrayList<NFLPlayer> masterPlayerList, playerList;
    private ArrayList<BasicNameValuePair> todo;
    private userAccount user;

    public PlayerViewAdapter(ArrayList<NFLPlayer> playerList, ArrayList<BasicNameValuePair> todo, userAccount user)
    {
        this.playerList = playerList;
        this.masterPlayerList = playerList;
        this.todo = todo;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_view_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, this.todo, this.user, this);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        NFLPlayer currentItem = this.playerList.get(position);
        holder.playerImg.setImageResource(currentItem.getImageResource());
        holder.playerName.setText(currentItem.getName());
        holder.playerScore.setText(currentItem.getScore());
        holder.player = currentItem;
        holder.changeButton.setEnabled(true);
        holder.changeButton.setVisibility(View.VISIBLE);
        holder.setButtonText();
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
}
