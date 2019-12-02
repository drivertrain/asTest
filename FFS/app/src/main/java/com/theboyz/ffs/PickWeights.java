package com.theboyz.ffs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.theboyz.ui.CardViewOffset;
import com.theboyz.ui.WeightPickAdapter;
import com.theboyz.utils.Helpers;

import java.util.ArrayList;


public class PickWeights extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private WeightPickAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;
    private RecyclerView.ItemDecoration rItemDecorator;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_weights);

        this.recyclerView = findViewById(R.id.pickWeights);
        this.rItemDecorator = new CardViewOffset(this, R.dimen.card_view_offset);
        this.rLayoutManager = new LinearLayoutManager(this);
        this.rAdapter = new WeightPickAdapter(this.getIntent().getStringArrayListExtra("selectedStats"));
        this.recyclerView.setLayoutManager(this.rLayoutManager);
        this.recyclerView.addItemDecoration(this.rItemDecorator);
        this.recyclerView.setAdapter(this.rAdapter);
//        this.submitButton = findViewById(R.id.saveWeightsButton);
        this.recyclerView.setItemViewCacheSize(this.rAdapter.getItemCount());
    }



    //Need to validate userInput here and make sure that each field has something in it.
    public void _save_changes(View v)
    {
        String currentItem;
        boolean error = false;
        ArrayList<Double> weights = new ArrayList<>();

        for (int i = 0; i < this.rAdapter.getItemCount(); i++)
        {
            WeightPickAdapter.StatViewHolder test = (WeightPickAdapter.StatViewHolder) this.recyclerView.getChildViewHolder(this.recyclerView.getChildAt(i));
            try
            {
                currentItem = test.statInput.getText().toString();



                if (currentItem.isEmpty())
                {
                    error = true;
                    break;
                }//End if

                else if (currentItem.contains("/"))
                {
                    String [] ops = currentItem.split("/");
                    Double op1 = Double.parseDouble(ops[0]);
                    Double op2 = Double.parseDouble(ops[1]);
                    weights.add(op1 / op2);
                }//End else if

                //
                else
                {
                    try
                    {
                        weights.add(Double.parseDouble(currentItem));
                    }
                    catch(Exception e)
                    {
                        //Create Dialog and display error
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.invalid_input);

                        // Add ok button
                        builder.setPositiveButton(R.string.okay_button, (dialog, id) -> dialog.dismiss());

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }//End else

            }
            catch(Exception e)
            {
                for (StackTraceElement el: e.getStackTrace())
                    System.out.println(el.toString());
            }
        }//End for

        if (error)
        {
            //Create Dialog and display error
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.invalid_input);

            // Add ok button
            builder.setPositiveButton(R.string.okay_button, (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        }//End if
        else
        {
            String [] scoredStats = Helpers.convertScoringList(this.getIntent().getStringArrayListExtra("selectedStats"));
            Double[] statWeights  = weights.toArray(new Double [scoredStats.length]);

            Intent intent = this.getIntent();
            intent.putExtra("scoredStats", scoredStats);
            intent.putExtra("statWeights", Helpers.DoubleToPrimitive(statWeights));
            setResult(MainActivity.WEIGHT_PICK_SUCCESSFUL, this.getIntent());
            finish();

        }
    }//End _save_changes


}
