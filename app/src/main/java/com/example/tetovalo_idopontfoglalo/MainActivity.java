package com.example.tetovalo_idopontfoglalo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int RC_SIGN_IN = 123;
    private static final int SECRET_KEY = 99;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    EditText emailET;
    EditText passwordET;

    // TODO adatok tárolása későbbre. Térkép hozzáadása.  Ha kell majd 3. videó
    //private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.editTextTextEmailAddress);
        passwordET = findViewById(R.id.editTextTextPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(LOG_TAG, account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                Log.w(LOG_TAG, "Google signIn fail", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Log.d(LOG_TAG, "User logged in: Google");
                startTattoo();
            }
            else {
                Log.d(LOG_TAG, "User login fail: Google");
                Toast.makeText(MainActivity.this,
                        "User login fail: Google: "+ task.getException().getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void login(View view) {

        try {
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if(email == null){ email = "";}
            if(password == null){ password = "";}


            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User logged in");
                    startTattoo();
                }
                else {
                    Log.d(LOG_TAG, "User login fail");
                    Toast.makeText(MainActivity.this,
                            "User login fail: "+ task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Log.d(LOG_TAG, "User login fail");
        }
    }

    private void startTattoo(){
        Intent intent = new Intent(this, About.class);
        //intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void loginWithGoogle(View view) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
}