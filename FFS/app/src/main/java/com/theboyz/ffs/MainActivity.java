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

public class MainActivity extends AppCompatActivity
{
    public static final int LOGIN_REQUEST_CODE = 3350;
    public static final int LOGIN_SUCCESSFUL = 3360;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//End onCreate

    public void _register_clicked(View view)
    {
        startActivity(new Intent(this, RegisterPage.class));
    }

    public void _login_clicked(View view)
    {
//        startActivityForResult(new Intent(this, LoginPage.class), LOGIN_REQUEST_CODE);
        startActivity(new Intent(this, PickScoring.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {


            switch (requestCode)
            {
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
                    }

                    break;
            }

        }//End try
        catch (Exception e)
        {
            System.out.println("Couldn't Handle Callback" + e.getMessage());
        }//End catch
    }
}//End class MainActivity
