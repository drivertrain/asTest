package com.theboyz.ffs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.theboyz.ui.PlayerCardOffset;
import com.theboyz.ui.PlayerViewAdapter;
import com.theboyz.ui.PlayerViewItem;

import java.util.ArrayList;

public class PickPlayers extends AppCompatActivity
{
    private EditText searchField;
    private RecyclerView recyclerView;
    private PlayerViewAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView.ItemDecoration rItemDecorator;
    private ArrayList<PlayerViewItem> players;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_players);

        searchField = findViewById(R.id.playerSearchField);

        players = new ArrayList<>();
        players.add(new PlayerViewItem(R.drawable.logo, "NAME1", "SCORE1"));
        players.add(new PlayerViewItem(R.drawable.logo, "NAME2", "SCORE2"));
        players.add(new PlayerViewItem(R.drawable.logo, "NAME3", "SCORE3"));

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
    }

    public void _handle_card_click(View view)
    {
        //Inspect Player
    }
}


