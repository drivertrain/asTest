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
         String res = new apiGet().execute("http://10.0.2.2:46680/api/get_team", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJleHAiOjE1NzQ1NTA3ODF9.2apjRi5jsdpkHXCzwjRvj5SCFIk43un-ixrZ-m_TJo0").get();
         System.out.println(res);

      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
      }


   }//End _loginClick
}//End class LoginPage
