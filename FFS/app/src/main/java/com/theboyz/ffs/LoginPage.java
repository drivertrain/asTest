package com.theboyz.ffs;

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
//   private userDB userDatabase;
//   private userAccount session;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login_page);
      this.email = findViewById(R.id.emailField);
      this.pass = findViewById(R.id.pwField);
//      this.userDatabase = new userDB();
//      session = null;
   }

   public void _loginClick(View view)
   {
      try
      {
         String res = new apiGet().execute("http://99.179.141.41:25565/api/get_team", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJleHAiOjE1NzQ2NTgyNDh9.jieeZiuvVirlqlhjwBeaIE8P93W3RPR41jXBSkDqT6M").get();
         System.out.println(res);

      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
      }


   }//End _loginClick
}//End class LoginPage
