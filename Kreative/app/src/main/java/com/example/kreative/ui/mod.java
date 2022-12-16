package com.example.kreative.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.kreative.R;

public class mod extends AppCompatActivity {
    ImageButton vipbutton,pagobutton,reportbutton,seguridadbutton,reclamobutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);

        vipbutton= (ImageButton) findViewById(R.id.vipbutton);
        pagobutton= (ImageButton) findViewById(R.id.pagobutton);
        reportbutton= (ImageButton) findViewById(R.id.reportbutton);
        seguridadbutton= (ImageButton) findViewById(R.id.seguridadbutton);
       reclamobutton= (ImageButton) findViewById(R.id.reclamobutton);

        vipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(mod.this, Membresia.class);
                startActivity(intentLoadNewActivity);
            }
        });

        pagobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(mod.this, Pagos.class);
                startActivity(intentLoadNewActivity);
            }
        });


        seguridadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(mod.this, Cambios.class);
                startActivity(intentLoadNewActivity);
            }
        });

        reclamobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(mod.this, Reclamos.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}