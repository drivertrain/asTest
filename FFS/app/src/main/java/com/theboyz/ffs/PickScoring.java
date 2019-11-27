package com.theboyz.ffs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class PickScoring extends AppCompatActivity
{
    private ArrayList<CheckBox> fields;
//    private CheckBox passComp;
//    private CheckBox passYards;
//    private CheckBox passTDs;
//    private CheckBox passInt;
//    private CheckBox rushAtt;
//    private CheckBox rushYards;
//    private CheckBox rushTDs;
//    private CheckBox recept;
//    private CheckBox recYards;
//    private CheckBox recTDs;
//    private CheckBox totRetYards;
//    private CheckBox kickRetTDs;
//    private CheckBox puntRetTDs;
//    private CheckBox fumbLost;
//    private CheckBox twoPts;
//    private CheckBox extraPts;
//    private CheckBox fieldGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_scoring);
        fields = new ArrayList<>();

        fields.add(findViewById(R.id.passCompletionCheck));
        fields.add(findViewById(R.id.passYardsCheck));
        fields.add(findViewById(R.id.passTDSCheck));
        fields.add(findViewById(R.id.intThrown));
        fields.add(findViewById(R.id.rushAttempts));
        fields.add(findViewById(R.id.rushYards));
        fields.add(findViewById(R.id.rushTDs));
        fields.add(findViewById(R.id.recept));
        fields.add(findViewById(R.id.recYards));
        fields.add(findViewById(R.id.recTDs));
        fields.add(findViewById(R.id.returns));
        fields.add(findViewById(R.id.kickRetTD));
        fields.add(findViewById(R.id.puntRetTD));
        fields.add(findViewById(R.id.fumbLost));
        fields.add(findViewById(R.id.twoPts));
        fields.add(findViewById(R.id.xpa));
        fields.add(findViewById(R.id.fgs));

    }

    public void _next_clicked(View v)
    {
        for(CheckBox check: this.fields)
            if (check.isChecked())
                System.out.println(check.getText().toString() + "is checked");
    }
}
