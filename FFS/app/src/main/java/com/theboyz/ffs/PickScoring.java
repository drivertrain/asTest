package com.theboyz.ffs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class PickScoring extends AppCompatActivity
{
    private ArrayList<CheckBox> fields;

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
        ArrayList<String> selectedStats = new ArrayList<>();
        boolean validSelection = false;
        for(CheckBox check: this.fields)
            if (check.isChecked())
                selectedStats.add(check.getText().toString());
        if (this.isValid())
        {
            this.getIntent().putExtra("selectedStats", selectedStats);
            setResult(MainActivity.STAT_PICK_SUCCESSFUL, this.getIntent());
            finish();
        }//End if
        else
        {
            //Create Dialog and display error
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.none_selected_error);

            // Add ok button
            builder.setPositiveButton(R.string.okay_button, (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();

        }//End Else
    }//End next click

    public void _default_scoring_clicked(View v)
    {
       setResult(MainActivity.USE_DEFAULT_SCORING);
       finish();
    }

    public boolean isValid()
    {
        boolean status = false;
        for(CheckBox check: this.fields)
            if (check.isChecked())
                status = true;
        return status;
    }//End isValid
}
