package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "ChatWindow";
    Button sendChatButton;
    ListView listView;
    EditText editText;
    ArrayList<String> chatMessages = new ArrayList<>();
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ChatDatabaseHelper chatDatabaseHelper = new ChatDatabaseHelper(this);

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        sendChatButton = findViewById(R.id.sendButton);
        db = chatDatabaseHelper.getWritableDatabase();

        chatAdapter messageAdapter = new chatAdapter(this);
        listView.setAdapter(messageAdapter);

        Cursor cursor = db.rawQuery("SELECT " + chatDatabaseHelper.KEY_MESSAGE + " FROM " + chatDatabaseHelper.TABLE_NAME, null);

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

        while (cursor.isAfterLast()) {
            String chatMessage = cursor.getString(0);
            chatMessages.add(chatMessage);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + chatMessage);
            cursor.moveToNext();
        }

        // Print the names of each column returned by the cursor
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String columnName = cursor.getColumnName(i);
            Log.i(ACTIVITY_NAME, "Column Name[" + i + "] = " + columnName);
        }

        cursor.close();


        sendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chatMessage = editText.getText().toString();

                // Insert the new message into the database
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, chatMessage);
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);

                chatMessages.add(chatMessage);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy(); // Call the super() version of the function

        // Close the database if it was opened
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private class chatAdapter extends ArrayAdapter<String> {
        public chatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chatMessages.size();
        }

        public String getItem(int position) {
            return chatMessages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;

            if (position%2==0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }


}