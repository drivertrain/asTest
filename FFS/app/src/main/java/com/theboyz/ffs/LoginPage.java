package com.theboyz.ffs;

import androidx.appcompat.app.AppCompatActivity;
import com.theboyz.utils.*;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import tech.tablesaw.api.Table;

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
      userDB userDatabase = new userDB();
      String entEmail = this.email.getText().toString();
      String entPW = this.pass.getText().toString();
      userAccount session = null;

      if ((session = userDatabase.authenticate("test@example.com", "password")) != null)
      {
         System.out.println("LOGIN SUCCESSFUL");
      }//End if
      else
      {
         System.out.println("LOGIN FAILED");
      }//End else

   }//End _loginClick
}//End class LoginPage
