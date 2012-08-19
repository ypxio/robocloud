package com.robocloud.project;

import java.util.HashMap;
import java.util.Map;

import com.robocloud.project.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    /** Called when the activity is first created. */
	
	private final String HTTP_LOGIN = "http://172.21.0.75/project/robocloud/index.php";
	
	private EditText email;
	private EditText password;
	
	private Button btnLogin;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				validation();
			}

		});
    }
    
    public void validation() {
    	
		final String s_email = email.getText().toString();
		final String s_pass = password.getText().toString();
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("email", s_email);
		params.put("pass", s_pass);
		
		Toast.makeText(LoginActivity.this, "Login Validation, please wait...",Toast.LENGTH_SHORT).show();
		
		HttpRequestClass login = new HttpRequestClass(HTTP_LOGIN, params, HttpRequestClass.Method.POST);
		String response = login.sendRequest();
		response = response.trim();
		
		if(response.equals("false"))
		{
			Toast.makeText(LoginActivity.this, "salah", Toast.LENGTH_LONG).show();
			
		}
		else
		{
//			Toast.makeText(LoginActivity.this, "benar", Toast.LENGTH_LONG).show();
			Intent i = new Intent(getApplicationContext(), Dashboard.class);
			startActivity(i);
		}

	}
}