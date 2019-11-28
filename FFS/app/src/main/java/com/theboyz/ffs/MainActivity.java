package com.theboyz.ffs;
import com.theboyz.utils.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static final int LOGIN_REQUEST_CODE = 3000;
    public static final int FIRST_LOGIN_REQUEST_CODE = 3001;
    public static final int LOGIN_SUCCESSFUL = 3010;

    public static final int STAT_PICK_REQUEST_CODE = 4000;
    public static final int STAT_PICK_SUCCESSFUL = 4010;

    public static final int REGISTER_REQUEST_CODE = 5000;
    public static final int REGISTER_SUCCESSFUL = 5010;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//End onCreate

    public void _register_clicked(View view)
    {
        startActivityForResult(new Intent(this, RegisterPage.class), REGISTER_REQUEST_CODE);
    }


    public void _login_clicked(View view)
    {
//        startActivityForResult(new Intent(this, LoginPage.class), LOGIN_REQUEST_CODE);
        startActivityForResult(new Intent(this, PickScoring.class), STAT_PICK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {


            switch (requestCode)
            {
                //Handle Regular Login
                case (LOGIN_REQUEST_CODE):
                    if (resultCode == LOGIN_SUCCESSFUL)
                    {
                        //Save currentUser
                        this.token = data.getStringExtra("token");

                        //Create next activity
                        Intent nextPage = new Intent(this, PickPlayers.class);
                        nextPage.putExtra("token", this.token);

                        //Start next activity
                        startActivity(nextPage);
                    }//End if
                    break;

                //Handle Selection of Stats
                case (STAT_PICK_REQUEST_CODE):
                    if (resultCode == STAT_PICK_SUCCESSFUL)
                    {
                        //Start The weight pick segment
                        Intent nextPage = new Intent(this, PickWeights.class);
                        nextPage.putExtra("selectedStats", data.getStringArrayListExtra("selectedStats"));
                        startActivity(nextPage);

                    }//End if
                    break;

                //Handle register request
                case (REGISTER_REQUEST_CODE):
                    if (resultCode == REGISTER_SUCCESSFUL)
                    {
                        //WHAT TO DO WHEN REGISTER IS SUCCESSFUL
                        startActivityForResult(new Intent(this, LoginPage.class), FIRST_LOGIN_REQUEST_CODE);
                    }//End if
                    break;

                //Handle the first time a user has logged in
                case (FIRST_LOGIN_REQUEST_CODE):
                    //Since it is the users first time logging in we need to force them to select some set of scoring stats/weights
                    if (resultCode == REGISTER_SUCCESSFUL)
                    {
                        startActivityForResult(new Intent(this, PickScoring.class), STAT_PICK_REQUEST_CODE);
                    }//End if

            }//End switch

        }//End try
        catch (Exception e)
        {
            System.out.println("Couldn't Handle Callback" + e.getMessage());
        }//End catch
    }
}//End class MainActivity
