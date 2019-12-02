package com.theboyz.ffs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.theboyz.utils.*;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.json.*;

public class LoginPage extends AppCompatActivity
{
   private EditText email;
   private EditText pass;

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

      try
      {
         JSONObject response = ffsAPI.authenticate(email.getText().toString(), pass.getText().toString());

         if (response != null)
         {
            this.getIntent().putExtra("loginResponse", response.toString());
            setResult(MainActivity.LOGIN_SUCCESSFUL, this.getIntent());
            finish();
         }//End if
         else
         {
            //Create Dialog and display error
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.login_error);

            // Add ok button
            builder.setPositiveButton(R.string.okay_button, (dialog, id) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
         }
      }//End try

      catch (Exception e)
      {
         System.out.println(e.getMessage() + "\n\nFROM LOGIN PAGE");
      }//End catch

   }//Ends _loginClick

}//End class LoginPage

