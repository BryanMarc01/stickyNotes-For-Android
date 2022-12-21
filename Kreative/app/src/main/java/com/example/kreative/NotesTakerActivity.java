package com.example.kreative;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kreative.Models.Notas;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {
EditText editText_Title, editText_notas;
ImageView imageView_save;
Notas notas;
boolean isOldNote = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);
editText_Title = findViewById(R.id.editText_Title);
editText_notas = findViewById(R.id.editText_notas);
   imageView_save = findViewById(R.id.ImageView_save);

   notas = new Notas();
   try{
       notas = (Notas) getIntent().getSerializableExtra("old_note");
        editText_Title.setText(notas.getTitulo());
        editText_notas.setText(notas.getNotas());
        isOldNote = true;
        }catch(Exception e ){
       e.printStackTrace();
   }



        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String titulo = editText_Title.getText().toString();
               String descripcion= editText_notas.getText().toString();
               if (descripcion.isEmpty()){
                   Toast.makeText(NotesTakerActivity.this, "Esta vacio", Toast.LENGTH_SHORT).show();
                   return;
               }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a ");
               Date date = new Date();
               if (!isOldNote){
                   notas= new Notas();
               }
               notas.setTitulo(titulo);
               notas.setNotas(descripcion);
               notas.setDate(formatter.format(date));
               Intent intent = new Intent();
               intent.putExtra("nota", notas);
               setResult(Activity.RESULT_OK, intent);
               finish();

            }
        });
    }
}