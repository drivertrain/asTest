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
import android.widget.EditText;

import com.theboyz.ui.CardViewOffset;
import com.theboyz.ui.PickPlayerAdapter;
import com.theboyz.utils.*;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class PickPlayers extends AppCompatActivity
{
    private EditText searchField;
    private RecyclerView recyclerView;
    private PickPlayerAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView.ItemDecoration rItemDecorator;
    private ArrayList<NFLPlayer> players;
    private userAccount user;
    private ArrayList<BasicNameValuePair> todo;
    private PlayerFilter playerFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_players);
        this.todo = new ArrayList<>();


        try
        {
            JSONObject loginResponse = new JSONObject(getIntent().getStringExtra("loginResponse"));
            this.user = new userAccount(loginResponse.getString("token"), loginResponse.getInt("id"));

            JSONObject userConfig = ffsAPI.getUserConfig(this.user);

            if (userConfig == null)
            {
                setResult(MainActivity.USER_NOT_CONFIGURED, this.getIntent());
                finish();
                return;
            }//END IF

            this.user.configureUser(userConfig);
            players = ffsAPI.getPlayers(this.user, "2019");
        }//End try

        catch(Exception e)
        {
            System.out.println(e.getMessage() + " FROM PICK PLAYERS CREATE USER ACCOUNT");
        }//End catch

        this.searchField = findViewById(R.id.playerSearchField);
        this.recyclerView = findViewById(R.id.playerView);
        this.rItemDecorator = new CardViewOffset(this, R.dimen.card_view_offset);
        this.rLayoutManager = new LinearLayoutManager(this);
        this.rAdapter = new PickPlayerAdapter(players, this.todo, this.user);

        this.recyclerView.setLayoutManager(this.rLayoutManager);
        this.recyclerView.addItemDecoration(this.rItemDecorator);
        this.recyclerView.setAdapter(this.rAdapter);
        this.playerFilter = new PlayerFilter(this.rAdapter, this.searchField);
        this.searchField.addTextChangedListener(this.playerFilter);

    }

    public void _save_changes(View view)
    {
        ArrayList<String> ids = new ArrayList<>();

        for (String val: this.user.getPlayerIDS())
            ids.add(val);

        System.out.println("BEFORE: " + ids.toString());

        //Loop for each change requested
        for (int i = 0; i < this.todo.size(); i++)
        {
            String op = this.todo.get(i).getName();
            String param = this.todo.get(i).getValue();

            //Cases for removing player or adding player
            switch (op)
            {
                case "remove":
                    //First make sure player is owned by the user
                    if (!(ids.contains(param)))
                        System.out.println("PLAYER IS NOT OWNED");
                    //Remove player from users collection
                    else
                    {
                        System.out.println("REMOVING " + param);
                        ids.remove(param);
                    }//End else
                    break;
                case "add":
                    //First make sure player is not owned by the user
                    if (ids.contains(param))
                        System.out.println("PLAYER ALEADY OWNED");
                    //Add player to users collection
                    else
                    {
                        System.out.println("ADDING " + param);
                        ids.add(param);
                    }//End else
                    break;
            }//End switch
        }//End for

        //Assign new data to user
        System.out.println("AFTER: " + ids.toString());
        this.user.setPlayers(ids.toArray(new String[ids.size()]));
        this.todo.clear();
        this.rAdapter.notifyDataSetChanged();
        setResult(MainActivity.PICK_PLAYER_SUCCESSFUL, this.getIntent());
        finish();
    }//End _save_changes

    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }//End if
    }//End closeKeyboard

    public class PlayerFilter implements TextWatcher
    {
        PickPlayerAdapter adapter;
        EditText searchField;

        public PlayerFilter(PickPlayerAdapter adapter, EditText searchField)
        {
            this.adapter = adapter;
            this.searchField = searchField;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            return;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            return;
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (!(this.searchField.getText().toString().isEmpty()))
                this.adapter.filterPlayers(this.searchField.getText().toString());
                //Else reset view contents
            else
                this.adapter.removeFilters();
        }
    }

}


