package com.example.krzysztofpiorkowski.apeczka;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListPos extends AppCompatActivity {
    private FirebaseUser user;
    private String email;
    private TextView user_area;
    private Button wyslij_button;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private LocationManager lm;
    private LocationListener ll;
    private Criteria cr;
    private Location loc;
    private String best_provider;
    int zmienna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pos);
        ActivityCompat.requestPermissions(ListPos.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        wyslij_button = (Button) findViewById(R.id.send_button);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView = (ListView) findViewById(R.id.lista_pozycji);
        user_area = (TextView) findViewById(R.id.user_tv);
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();
        user_area.setText("Witaj!: " + email);
        zmienna = 0;
        listView.setAdapter(adapter);
        wyslij_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.shiva.gpstut.GpsTracker gt = new com.example.shiva.gpstut.GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    mDatabase.push().setValue(email + ": " + " N: " + lat + "   E: " + lon);
                }
            }
        });
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string=dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    public void Logout_button(View v)
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        mAuth.signOut();
        startActivityForResult(myIntent, 0);
        finish();
    }
}
