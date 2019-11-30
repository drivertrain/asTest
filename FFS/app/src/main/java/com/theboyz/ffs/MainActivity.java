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
    //REQUEST CODES
    public static final int REGISTER_REQUEST_CODE = 2000;
    public static final int LOGIN_REQUEST_CODE = 3000;
    public static final int FIRST_LOGIN_REQUEST_CODE = 3001;
    public static final int STAT_PICK_REQUEST_CODE = 4000;
    public static final int WEIGHT_PICK_REQUEST_CODE = 5000;
    public static final int PICK_PLAYER_REQUEST = 6000;


    //SUCCESS CODES
    public static final int REGISTER_SUCCESSFUL = 2010;
    public static final int LOGIN_SUCCESSFUL = 3010;
    public static final int STAT_PICK_SUCCESSFUL = 4010;
    public static final int WEIGHT_PICK_SUCCESSFUL = 5010;
    public static final int PICK_PLAYER_SUCCESSFUL = 6010;


    //ERROR CODES
    public static final int USER_NOT_CONFIGURED = 9001;

    private String loginResponse;

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
        startActivityForResult(new Intent(this, LoginPage.class), LOGIN_REQUEST_CODE);
    }


    //THIS IS PRETTY MUCH HOW THE APP CONTROL FLOW IS HANDLED CURRENTLY
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            //SWITCH BASED ON WHICH REQUEST WAS MADE
            switch (requestCode)
            {
                //Handle register request
                case (REGISTER_REQUEST_CODE):
                    if (resultCode == REGISTER_SUCCESSFUL)
                    {
                        //WHAT TO DO WHEN REGISTER IS SUCCESSFUL
                        startActivityForResult(new Intent(this, LoginPage.class), FIRST_LOGIN_REQUEST_CODE);
                    }//End if
                    break;

                //Handle Regular Login
                case (LOGIN_REQUEST_CODE):
                    if (resultCode == LOGIN_SUCCESSFUL)
                    {
                        //Save currentUser
                        this.loginResponse = data.getStringExtra("loginResponse");

                        //Create next activity
                        Intent nextPage = new Intent(this, PickPlayers.class);
                        nextPage.putExtra("loginResponse", this.loginResponse);

                        //Start next activity
                        startActivityForResult(nextPage, PICK_PLAYER_REQUEST);
                    }//End if
                    break;

                //Handle the first time a user has logged in
                case (FIRST_LOGIN_REQUEST_CODE):
                    //Since it is the users first time logging in we need to force them to select some set of scoring stats/weights
                    if (resultCode == LOGIN_SUCCESSFUL)
                    {
                        startActivityForResult(new Intent(this, PickScoring.class), STAT_PICK_REQUEST_CODE);
                    }//End if
                    break;

                //Handle Selection of Stats
                case (STAT_PICK_REQUEST_CODE):
                    if (resultCode == STAT_PICK_SUCCESSFUL)
                    {
                        //Start The weight pick segment
                        Intent nextPage = new Intent(this, PickWeights.class);
                        nextPage.putExtra("selectedStats", data.getStringArrayListExtra("selectedStats"));
                        startActivityForResult(nextPage, WEIGHT_PICK_REQUEST_CODE);

                    }//End if
                    break;

                case (WEIGHT_PICK_REQUEST_CODE):
                    if (resultCode == WEIGHT_PICK_SUCCESSFUL)
                    {
                        String [] scoredStats = data.getStringArrayExtra("scoredStats");
                        double [] statWeights = data.getDoubleArrayExtra("statWeights");
                        userAccount user = this.getUser();

                        if (user != null)
                        {
                            user.setStats(scoredStats);
                            user.setWeights(statWeights);
                            ffsAPI.updateLeagueConfig(user);
                        }//End if

                    }//END IF


                case (PICK_PLAYER_REQUEST):
                    if (resultCode == USER_NOT_CONFIGURED)
                    {
                        //IF USER IS NOT CONFIGURED GO TO STAT PICK PAGE AND MAKE USER CONFIGURE THEIR ACCOUNT
                        startActivityForResult(new Intent(this, PickScoring.class), STAT_PICK_REQUEST_CODE);
                    }//End if
                    else if(resultCode == PICK_PLAYER_SUCCESSFUL)
                    {
                        String [] playerIDS = data.getStringArrayExtra("playerIDS");
                    }//END ELSE IF

            }//End switch

        }//End try
        catch (Exception e)
        {
            System.out.println("Couldn't Handle Callback" + e.getMessage());
        }//End catch
    }//End on activityResult


    /**
     * getUser gets the userAccount from the response string saved in this class
     * @return
     */
    public userAccount getUser()
    {
        JSONObject responseJSON;
        userAccount user;
        try
        {
            responseJSON = new JSONObject(this.loginResponse);
            user = new userAccount(responseJSON.getString("token"));
        }//End try
        catch (Exception e)
        {
            user = null;
        }//End catch

        return user;
    }//End getUser

    private void userNotLoggedInError()
    {
        return;
    }//End userNotLoggedInError
}//End class MainActivity
