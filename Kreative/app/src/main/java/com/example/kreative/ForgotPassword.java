package com.example.kreative;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button registro2;
   EditText servi2;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
        registro2 = findViewById(R.id.registro2);
        servi2=findViewById(R.id.servi2);
        registro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }



    private void validate() {
        String email= servi2.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            servi2.setError("Correo invalido");
            return;
        }
        sendEmail(email);
    }

    private void sendEmail(String email) {
        FirebaseAuth auth= FirebaseAuth.getInstance();
        String servi2 = email;
        auth.sendPasswordResetEmail(servi2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPassword.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ForgotPassword.this, "Correo no enviado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ForgotPassword.this , MainActivity.class);
   startActivity(intent);
   finish();
    }

}

