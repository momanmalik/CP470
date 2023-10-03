package com.example.androidassignments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, " onCreate reached");

        final ImageButton cameraButton = findViewById(R.id.imageButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent picture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(picture, 10);
            }
        });

        final Switch switchButton = findViewById(R.id.switch1);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(ListItemsActivity.this, "Switch is on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListItemsActivity.this, "Switch is off", Toast.LENGTH_LONG).show();
                }
            }
        });

        final CheckBox checkbox =findViewById(R.id.checkBox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);

                builder.setMessage(getString(R.string.dialog_message))

                        .setTitle(getString(R.string.dialog_title))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        if (requestCode == 10 && responseCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton btnImg = findViewById(R.id.imageButton);
            btnImg.setImageBitmap(imageBitmap);
            try{
                //saveImage(imageBitmap);
            }catch(Exception e){
            }
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
        }
    }

    protected void print(String string){
        Toast.makeText(ListItemsActivity.this, string, Toast.LENGTH_SHORT).show();
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