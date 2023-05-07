package com.example.tetovalo_idopontfoglalo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tetovalo_idopontfoglalo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class About extends AppCompatActivity {
    BottomNavigationView navigationView;
    private FirebaseUser user;
    private static final String LOG_TAG = About.class.getName();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null){
            Log.d(LOG_TAG, "Authenticated user");
        }else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }

        navigationView = findViewById(R.id.bottomMenu);

        navigationView.setOnItemSelectedListener(item -> {
            if (R.id.home == item.getItemId()) {
                Log.d(LOG_TAG,"Home");
                //home();
                return true;
            }
            if (R.id.date == item.getItemId()) {
                Log.d(LOG_TAG,"Idopont foglalas");
                appointment();
                return true;
            }
            if (R.id.myDate == item.getItemId()) {
                Log.d(LOG_TAG,"Idopontjaim");
                myAppointments();
                return true;
            }
            else return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


    private void appointment(){
        Intent intent = new Intent(this, Appointment.class);
        startActivity(intent);
    }
    private void myAppointments(){
        Intent intent = new Intent(this, MyAppointment.class);
        startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }
}