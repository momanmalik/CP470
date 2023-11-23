package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    private String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        text = getString(R.string.SnackBarInit);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Moman Was Here",Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int itemId = mi.getItemId();
        if (itemId == R.id.action_one) {
            Log.d("Win", "Option 1 selected");
            Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (itemId == R.id.action_two) {
            Log.d("Cards", "Option 2 selected");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.BuilderTitle);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Handle the cancel action
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (itemId == R.id.action_three) {
            Log.d("Edit", "Option 3 selected");
            LayoutInflater inflater = this.getLayoutInflater();
            AlertDialog.Builder builderThree = new AlertDialog.Builder(this);
            final View view = inflater.inflate(R.layout.dialog_layout, null);
            builderThree.setView(view);

            builderThree.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditText edit = view.findViewById(R.id.dialog_message_box);
                    String message = edit.getText().toString();
                    text = message;
                }
            });

            builderThree.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Handle the cancel action
                }
            });

            AlertDialog dialogThree = builderThree.create();
            dialogThree.show();
        } else if (itemId == R.id.about) {
            Log.d("Settings", "Version 1.0 by Moman");
            Toast.makeText(TestToolbar.this, "Version 1.0 by Moman", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}