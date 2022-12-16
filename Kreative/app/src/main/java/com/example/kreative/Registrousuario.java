package com.example.kreative;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registrousuario extends AppCompatActivity {
    private FirebaseAuth mAuth;
private EditText emailR;
private EditText contraseña;
private EditText contraseñaC;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrousuario);

        mAuth = FirebaseAuth.getInstance();

        emailR = findViewById(R.id.emailR);
                contraseña = findViewById(R.id.contraseña);
        contraseñaC = findViewById(R.id.contraseñaC);
    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

public void Registrousuario (View view){
        if (contraseña.getText().toString().equals(contraseñaC.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(emailR.getText().toString(),contraseña.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext() , MainActivity.class);
                                startActivity(i);
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });

        }else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

}

}