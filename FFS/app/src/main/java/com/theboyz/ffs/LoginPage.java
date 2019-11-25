package com.theboyz.ffs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.theboyz.utils.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.json.*;

import java.util.HashMap;


public class LoginPage extends AppCompatActivity
{
   private EditText email;
   private EditText pass;
   private userAccount user;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login_page);
      this.email = findViewById(R.id.emailField);
      this.pass = findViewById(R.id.pwField);
   }

   public void _loginClick(View view)
   {
      JSONObject response = ffsAPI.authenticate(email.getText().toString(), pass.getText().toString());

      if (response != null)
      {
         Intent intent = getIntent();
         intent.putExtra("userjson", response.toString());
         setResult(MainActivity.LOGIN_SUCCESSFUL, intent);
         finish();
      }//End if
      else
      {
         //Create Dialog and display error
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setMessage(R.string.login_error);
         // Add ok button
         builder.setPositiveButton(R.string.okay_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id)
            {
               dialog.dismiss();
            }
         });

         //Could create no button here but no need to
//            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    // User cancelled the dialog
//                }
//            });

         AlertDialog dialog = builder.create();
         dialog.show();
      }
   }//End _loginClick
}//End class LoginPage
