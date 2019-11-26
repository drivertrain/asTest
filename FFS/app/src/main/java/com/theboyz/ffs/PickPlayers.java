package com.theboyz.ffs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.theboyz.ui.PlayerCardOffset;
import com.theboyz.ui.PlayerViewAdapter;
import com.theboyz.utils.*;

import org.json.JSONObject;

import java.util.ArrayList;

public class PickPlayers extends AppCompatActivity
{
    private EditText searchField;
    private RecyclerView recyclerView;
    private PlayerViewAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView.ItemDecoration rItemDecorator;
    private ArrayList<NFLPlayer> players;
    private userAccount user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_players);

        searchField = findViewById(R.id.playerSearchField);
        try
        {
            this.user = new userAccount(new JSONObject(getIntent().getStringExtra("userjson")));
            players = ffsAPI.getPlayers(this.user, "2019");
            NFLPlayer currentPlayer;
            for (int i = 0; i < players.size(); i++)
            {
                currentPlayer = players.get(i);
                currentPlayer.setImageResource(this.getResources().getIdentifier(currentPlayer.getTeam().toLowerCase() + ".png", "drawable", this.getPackageName()));
            }
        }//End try

        catch(Exception e)
        {
            System.out.println(e.getMessage());
            finish();
        }//End catch

        this.recyclerView = findViewById(R.id.playerView);
        this.rItemDecorator = new PlayerCardOffset(this, R.dimen.player_card_offset);
        this.rLayoutManager = new LinearLayoutManager(this);
        this.rAdapter = new PlayerViewAdapter(players);

        this.recyclerView.setLayoutManager(this.rLayoutManager);
        this.recyclerView.addItemDecoration(this.rItemDecorator);
        this.recyclerView.setAdapter(this.rAdapter);

    }

    public void _filter_players(View view)
    {
        //Filter ArrayList and reload if there is something in search box
        if (!(this.searchField.getText().toString().isEmpty()))
            this.rAdapter.filterPlayers(this.searchField.getText().toString());
        //Else reset view contents
        else
            this.rAdapter.removeFilters();

        this.searchField.setText("");
        this.closeKeyboard();
    }

    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }//End if
    }//End closeKeyboard

}


