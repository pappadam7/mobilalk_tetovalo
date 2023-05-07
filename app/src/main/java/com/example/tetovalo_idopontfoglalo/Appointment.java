package com.example.tetovalo_idopontfoglalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Appointment extends AppCompatActivity {
    BottomNavigationView navigationView;
    private FirebaseUser user;
    private FirebaseFirestore fireStore;
    private CollectionReference items;

    private static final String LOG_TAG = About.class.getName();

    EditText shortDestET;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Log.d(LOG_TAG, "Authenticated user");
        }else {
            Log.d(LOG_TAG, "Unauthenticated user");
            finish();
        }

        shortDestET = findViewById(R.id.editTextTextShortDescription);
        datePicker = findViewById(R.id.date_picker_actions);

        navigationView = findViewById(R.id.bottomMenu);
        navigationView.setOnItemSelectedListener(item -> {
            if (R.id.home == item.getItemId()) {
                Log.d(LOG_TAG,"Home");
                home();
                return true;
            }
            if (R.id.date == item.getItemId()) {
                Log.d(LOG_TAG,"Idopont foglalas");
                return true;
            }
            if (R.id.myDate == item.getItemId()) {
                Log.d(LOG_TAG,"Idopontjaim");
                myAppointments();
                return true;
            }
            else return false;
        });

        fireStore = FirebaseFirestore.getInstance();
        items = fireStore.collection("Items");

    }

    public void submit(View view) {
        //TODO majd a kép kezelése
        String shortDescription = shortDestET.getText().toString();
        String appointment = datePicker.getYear() + "-" + datePicker.getMonth()+1 + "-" + datePicker.getDayOfMonth();
        String email = user.getEmail();

        Log.d(LOG_TAG, "Date: " + appointment+ " Leírás: " + shortDescription);
        items.add(new AppointmentModel(email, appointment, shortDescription)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(LOG_TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(LOG_TAG, "Error adding document", e);
            }
        });
        myAppointments();
    }

    private void home(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    private void myAppointments(){
        Intent intent = new Intent(this, MyAppointment.class);
        startActivity(intent);
    }
}