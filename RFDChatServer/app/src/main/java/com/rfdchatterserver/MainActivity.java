package com.rfdchatterserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.personName);

    }
    public void startActivity(View view) {
        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError("Username field cannot be Empty");
            return;
        }
        Intent i = new Intent(this, StartActivity.class);
        i.putExtra("name", name.getText().toString());
        startActivity(i);
    }
}