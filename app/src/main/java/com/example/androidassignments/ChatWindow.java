package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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
    Button sendChatButton;
    ListView listView;
    EditText editText;
    ArrayList<String> chatMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        sendChatButton = findViewById(R.id.sendButton);

        chatAdapter messageAdapter = new chatAdapter(this);
        listView.setAdapter(messageAdapter);

        sendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chatMessage = editText.getText().toString();
                chatMessages.add(chatMessage);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
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