package com.example.tetovalo_idopontfoglalo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private static final String LOG_TAG = Register.class.getName();
    private static final int SECRET_KEY = 99;
    private FirebaseAuth firebaseAuth;

    EditText emailET;
    EditText passwordET;
    EditText passwordAgainET;
    EditText phoneNumberET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailET = findViewById(R.id.editTextTextEmailAddress);
        passwordET = findViewById(R.id.editTextTextPassword);
        passwordAgainET = findViewById(R.id.editTextTextPasswordAgain);
        phoneNumberET = findViewById(R.id.editTextTextPhoneNumber);

        int secretKey= getIntent().getIntExtra("SECRET_KEY", 0);
        if (secretKey != 99){
            finish();
        }

        firebaseAuth =  FirebaseAuth.getInstance();
    }

    public void register(View view) {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordAgain = passwordAgainET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();

        if(!password.equals(passwordAgain)){
            Log.i(LOG_TAG, "Nem egyezik a két jelszó");
            return;
        }
        Log.i(LOG_TAG, "Reg: email:"+email+ " PW:" + password+ " PWAgain:" +passwordAgain+ " phone:" +phoneNumber);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Log.d(LOG_TAG, "User created");
                startTattoo();
            }
            else {
                Log.d(LOG_TAG, "User wasn't created");
                Toast.makeText(Register.this,
                                "User wasn't created: "+ task.getException().getMessage(),
                                     Toast.LENGTH_LONG).show();
            }
        });
    }

    public void login(View view) {
        finish();
    }

    private void startTattoo(){
        Intent intent = new Intent(this, About.class);
        //intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }
}