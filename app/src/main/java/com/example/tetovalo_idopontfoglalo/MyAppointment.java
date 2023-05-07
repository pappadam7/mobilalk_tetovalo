package com.example.tetovalo_idopontfoglalo;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyAppointment extends AppCompatActivity {
    private static final String LOG_TAG = MyAppointment.class.getName();
    private FirebaseUser user;
    private FirebaseFirestore fireStore;
    private CollectionReference items;
    private BottomNavigationView navigationView;
    private RecyclerView recyclerView;
    private ArrayList<AppointmentModel> appointmentsList;
    private AppointmentListAdapter appointmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

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
                home();
                return true;
            }
            if (R.id.date == item.getItemId()) {
                Log.d(LOG_TAG,"Idopont foglalas");
                appointment();
                return true;
            }
            if (R.id.myDate == item.getItemId()) {
                Log.d(LOG_TAG,"Idopontjaim");
                return true;
            }
            else return false;
        });

        fireStore = FirebaseFirestore.getInstance();
        items = fireStore.collection("Items");

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appointmentsList = new ArrayList<>();

        appointmentListAdapter = new AppointmentListAdapter(this, appointmentsList);
        recyclerView.setAdapter(appointmentListAdapter);
        queryData();
        initializeData();

    }

    private void home(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    private void appointment(){
        Intent intent = new Intent(this, Appointment.class);
        startActivity(intent);
    }

    private void myAppointment(){
        Intent intent = new Intent(this, MyAppointment.class);
        startActivity(intent);
    }

    private void queryData(){
        items.whereEqualTo("email", user.getEmail()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                AppointmentModel appointmentModel = documentSnapshot.toObject(AppointmentModel.class);
                appointmentModel._setId(documentSnapshot.getId());
                appointmentsList.add(appointmentModel);
            }
            appointmentListAdapter.notifyDataSetChanged();
        });
    }

    private void initializeData(){
        String[] descriptions;
        String[] appointments;
        TypedArray imagineResource;
    }
    public void deleteItem(AppointmentModel appointmentModel){
        Log.d(LOG_TAG, "Delete: " +appointmentModel.getAppointment());

        DocumentReference ref = items.document(appointmentModel._getId());
        ref.delete().addOnSuccessListener(success -> {
            Log.d(LOG_TAG, "Item is successfully deleted: " + appointmentModel._getId());
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Item " + appointmentModel._getId() + " cannot be deleted.", Toast.LENGTH_LONG).show();
        });
        myAppointment();
    }

    public void updateItem(AppointmentModel appointmentModel, String date){
        //String date = year +"-"+ month + "-" + day;
        Log.d(LOG_TAG, "Was: " + appointmentModel.getAppointment() + " Update: " + date);
        DocumentReference ref = items.document(appointmentModel._getId());
        ref.update("appointment", date).addOnSuccessListener(success -> {
            Log.d(LOG_TAG, "Item is successfully updated: " + appointmentModel._getId());
        }).addOnFailureListener(e -> {
            Log.w(LOG_TAG, "Update failed");
            Toast.makeText(this, "Item " + appointmentModel._getId() + " cannot be updated.", Toast.LENGTH_LONG).show();
        });
        myAppointment();
    }
}