package com.rfdchatterserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartActivity extends AppCompatActivity {

    TextView name;
    TextView messageC;
    EditText messageS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        name = findViewById(R.id.name);
        messageS = findViewById(R.id.messageS);
        messageC = findViewById(R.id.messageC);

        messageC.setMovementMethod(new ScrollingMovementMethod());

        Intent i = getIntent();
        String Name = i.getStringExtra("name").trim();
        name.setText("You are Talking with "+ Name);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messageI");
        final DatabaseReference myRefChild = myRef.child(Name);

        myRefChild.child("Client").addValueEventListener(new ValueEventListener() {
            String m;
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    m = snapshot.getValue().toString();
                    Log.d("MainActivity", "Successfully retreived previous data");
                }
                catch (Exception e) {
                    m = "Hi from Client";
                    myRefChild.child("Server").setValue("Hi, from Server");
                    Log.d("MainActivity", "Exception");
                }
                messageC.setText(m);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(StartActivity.this, "Failed to read data from firebase", Toast.LENGTH_SHORT).show();
            }
        });


            myRefChild.child("Server").addListenerForSingleValueEvent(new ValueEventListener() {
                String m;

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {
                        m = snapshot.getValue().toString();
                        Log.d("MainActivity", "Successfully retreived previous data");
                    } catch (Exception e) {
                        m = "Hi from Server";
                        myRefChild.child("Client").setValue("Hi, from Client");
                        Log.d("MainActivity", "Exception");
                    }
                    messageS.setText(m);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(StartActivity.this, "Failed to read data from firebase", Toast.LENGTH_SHORT).show();
                }
            });


        messageS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String m1 = messageS.getText().toString();
                myRefChild.child("Server").setValue(m1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}