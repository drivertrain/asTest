package com.theboyz.ffs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.theboyz.utils.*;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.json.*;

import java.util.HashMap;
public class RegisterPage extends AppCompatActivity
{
    public EditText email, pass, confPass, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        this.email = findViewById(R.id.emailField);
        this.pass = findViewById(R.id.pwField);
        this.confPass = findViewById(R.id.confPwField);
        this.userName = findViewById(R.id.nameField);
    }//end onCreate

    public void _registerClick(View view)
    {
        if (this.registerValidator() && ffsAPI.register(this.email.getText().toString(), this.userName.getText().toString(), this.pass.getText().toString()))
        {
            setResult(MainActivity.REGISTER_SUCCESSFUL);
            finish();
        }
        else
        {
            //Create Dialog and display error
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.invalid_input);

            // Add ok button
            builder.setPositiveButton(R.string.okay_button, (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        }//End else
    }//End _register_click

    private boolean registerValidator()
    {
        boolean status = true;
        try
        {
            //Make sure there are no empty fields
            if (email.getText().toString().length() == 0)
                status = false;
            if (pass.getText().toString().length() == 0)
                status = false;
            if (this.userName.getText().toString().length() == 0)
                status = false;


            //Make sure passwords match
            if (!(confPass.getText().toString().equals(pass.getText().toString())))
                status = false;

        }
        catch(Exception e)
        { System.out.println(e.getMessage()); status=false; }
        return status;
    }//End registerValidator
}
