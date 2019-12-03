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
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.theboyz.ffs.R;
import com.theboyz.utils.Helpers;
import com.theboyz.utils.NFLPlayer;
import com.theboyz.utils.userAccount;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;

public class PickPlayerAdapter extends RecyclerView.Adapter <PickPlayerAdapter.PickViewHolder>
{
    //INTEGER CONSTANTS FOR STATUS CODES
    public static final int ADD_PLAYER_IN_TODO = 1000;
    public static final int REMOVE_PLAYER_IN_TODO = 1001;
    public static final int PLAYER_OWNED = 1010;
    public static final int PLAYER_NOT_OWNED = 1011;

    private ArrayList<NFLPlayer> masterPlayerList, playerList;
    private ArrayList<BasicNameValuePair> todo;
    private userAccount user;

    public PickPlayerAdapter(ArrayList<NFLPlayer> playerList, ArrayList<BasicNameValuePair> todo, userAccount user)
    {
        this.playerList = playerList;
        this.masterPlayerList = playerList;
        this.todo = todo;
        this.user = user;
    }

    @NonNull
    @Override
    public PickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_player_card, parent, false);
        PickViewHolder viewHolder = new PickViewHolder(v, this.todo, this.user, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickViewHolder viewHolder, int position)
    {
        NFLPlayer currentItem = this.playerList.get(position);
        viewHolder.playerName.setText(currentItem.getName());
        viewHolder.playerScore.setText(currentItem.getScore());
        viewHolder.player = currentItem;
        viewHolder.player.setImageResource(Helpers.getImageId(viewHolder.player.getTeam()));
        viewHolder.playerImg.setImageResource(viewHolder.player.getImageResource());
        viewHolder.setButtonText();
        viewHolder.setPosition(position);
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
        if (playerList == null)
        {
            System.out.println(user.getPlayerIDS());
            return 0;
        }
        return this.playerList.size();
    }

    public String getAmtDisplayedItems()
    {
        return "Displaying " + this.playerList.size() + " out of " + this.masterPlayerList.size();
    }


    public static class PickViewHolder extends ViewHolder
    {
        //CONSTANTS
        private final PickPlayerAdapter adapter;
        public final ArrayList<BasicNameValuePair> todo;
        public final userAccount user;
        private final Context context;


        public int position;
        public ImageView playerImg;
        public TextView playerName;
        public TextView playerScore;
        public Button changeButton;
        public NFLPlayer player;


        public PickViewHolder(@NonNull View itemView, ArrayList<BasicNameValuePair> todo, userAccount user, PickPlayerAdapter parent)
        {
            super(itemView);

            this.context = itemView.getContext();
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
            int playerStatus = this.todoQueueStatus();
            switch(playerStatus)
            {
                case ADD_PLAYER_IN_TODO:
                    this.todo.remove(new BasicNameValuePair("add", this.player.getID()));
                    break;

                case REMOVE_PLAYER_IN_TODO:
                    this.todo.remove(new BasicNameValuePair("remove", this.player.getID()));
                    break;

                case PLAYER_OWNED:
                    this.todo.add(new BasicNameValuePair("remove", this.player.getID()));
                    break;

                case PLAYER_NOT_OWNED:
                    this.todo.add(new BasicNameValuePair("add", this.player.getID()));
                    break;
            }//End Switch
            this.adapter.notifyItemChanged(this.position);
        }//End _on_player_click


        public void setButtonText()
        {
            int playerStatus = this.todoQueueStatus();
            switch(playerStatus)
            {
                case ADD_PLAYER_IN_TODO:
                case PLAYER_OWNED:
                    this.changeButton.setText(R.string.remove_button_text);;
                    break;

                case REMOVE_PLAYER_IN_TODO:
                case PLAYER_NOT_OWNED:
                    this.changeButton.setText(R.string.add_button_text);
                    break;

            }//End Switch
        }

        public void setPosition(int val) { this.position = val; }

        /**
         * Helper function that returns status of the current player
         * regarding pending changes and whether or not the user
         * already has the player as a part of their team
         * @return integer based on static identifiers 'ADD/REMOVE_PLAYER_IN_TODO', 'PLAYER_(NOT_)/OWNED'
         */
        private int todoQueueStatus()
        {
            //IF ADD PLAYER IN TODO
            if (this.todo.contains(new BasicNameValuePair("add", this.player.getID())))
                return ADD_PLAYER_IN_TODO;

                //IF REMOVE PLAYER IN TODO
            else if (this.todo.contains(new BasicNameValuePair("remove", this.player.getID())))
                return REMOVE_PLAYER_IN_TODO;

            else
            {
                //PLAYER IS IN THE USERS COLLECTION
                if (Arrays.asList(this.user.getPlayerIDS()).contains(this.player.getID()))
                    return PLAYER_OWNED;

                    //PLAYER IS NOT IN THE USERS COLLECTION
                else
                    return PLAYER_NOT_OWNED;
            }
        }

    }//End class ViewHolder
}
