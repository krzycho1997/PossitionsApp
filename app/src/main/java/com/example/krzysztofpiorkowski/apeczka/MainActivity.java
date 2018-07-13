package com.example.krzysztofpiorkowski.apeczka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void LogowanieButton(View v)
    {
        Intent myIntent = new Intent(v.getContext(), LoginActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
    }
    public void WyjscieButton(View v)
    {
        finish();
    }
}
