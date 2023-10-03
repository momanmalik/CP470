package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    SharedPreferences sp;
    String loginEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = findViewById(R.id.loginButton);
        final EditText loginName = findViewById(R.id.loginEditText);
        final EditText password = findViewById(R.id.passwordEditText);

        sp = getSharedPreferences("DefaultEmail", MODE_PRIVATE);
        String emailDefaulted = sp.getString("DefaultEmail","email@domain.com");
        loginName.setText(emailDefaulted);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                loginEmail = loginName.getText().toString();
                SharedPreferences.Editor editor = sp.edit();

                editor.putString("DefaultEmail",loginEmail);
                editor.commit();

                if ((!loginEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) && !password.getText().toString().isEmpty())  {
                    Toast.makeText(LoginActivity.this, "Email and pass verified!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Enter valid email address and password!", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, " onResume reached");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, " onStart reached");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, " onPause reached");
    }
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, " onStop reached");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, " onDestroy reached");
    }

    protected void onSaveInstanceState(Bundle savedInstance){
        super.onSaveInstanceState(savedInstance);
        Log.i(ACTIVITY_NAME, " onSaveInstanceState reached");
    }

    protected void onRestoreInstanceState(Bundle savedInstance){
        super.onRestoreInstanceState(savedInstance);
        Log.i(ACTIVITY_NAME, " onRestoreInstanceState reached");
    }
}