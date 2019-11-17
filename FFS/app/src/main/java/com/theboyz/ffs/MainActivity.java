package com.theboyz.ffs;
import com.theboyz.utils.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Intent loginPage;
    private Intent registerPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginPage = new Intent(this, LoginPage.class);
        startActivity(loginPage);
    }//End onCreate
}//End class MainActivity
