package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, " onCreate reached");

        final Button mainActivityButton = findViewById(R.id.listItemsButton);

        mainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        final Button startChatButton = findViewById(R.id.startChatButton);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ChatWindow.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            }
        });
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        Log.i(ACTIVITY_NAME, " onActivityResult reached");
        super.onActivityResult(requestCode, responseCode, data);
        if(requestCode == 10 && responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
            Toast toast = Toast.makeText(MainActivity.this, "ListItemsActivity passed: "+messagePassed, Toast.LENGTH_SHORT);
            toast.show();
        }
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