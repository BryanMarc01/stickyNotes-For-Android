package com.example.kreative;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.SearchView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.kreative.Adapters.NotasListAdapter;
import com.example.kreative.Database.RoomDB;
import com.example.kreative.Models.Notas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    RecyclerView recyclerView;
    NotasListAdapter notasListAdapter;
    List<Notas> notas= new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_home;
    Notas selectedNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        searchView_home = findViewById(R.id.searchView_home);
        database = RoomDB.getInstance(this);
        notas = database.mainDAO().getAll();

        updateRecycler(notas);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Notas>filteredList = new ArrayList<>();
        for (Notas singleNota : notas){
            if (singleNota.getTitulo().toLowerCase().contains(newText.toLowerCase())
                    || singleNota.getNotas().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNota);
            }
            notasListAdapter.filterList(filteredList);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101){
            if (resultCode == Activity.RESULT_OK){
                Notas new_notas= (Notas) data.getSerializableExtra("nota");
                database.mainDAO().insert(new_notas);
                notas.clear();
                notas.addAll(database.mainDAO().getAll());
                notasListAdapter.notifyDataSetChanged();

            }
        }
        else if (requestCode==102){
            if(resultCode == Activity.RESULT_OK){
                Notas new_notas= (Notas) data.getSerializableExtra("nota");
                database.mainDAO().update(new_notas.getID(), new_notas.getTitulo(), new_notas.getNotas());
                notas.clear();
                notas.addAll(database.mainDAO().getAll());
                notasListAdapter.notifyDataSetChanged();

            }
        }

    }

    private void updateRecycler(List<Notas> notas) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notasListAdapter = new NotasListAdapter(MainActivity2.this,notas,notasClickListener );
        recyclerView.setAdapter(notasListAdapter);


    }

    private final NotasClickListener notasClickListener = new NotasClickListener() {
        @Override
        public void onClick(Notas notas) {
            Intent intent= new Intent(MainActivity2.this, NotesTakerActivity.class);
            intent.putExtra("old_note",notas);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notas notas, CardView cardView) {
            selectedNota = new Notas();
            selectedNota= notas;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pin:
                if (selectedNota.isPinned()){
                    database.mainDAO().pin(selectedNota.getID(), false);
                    Toast.makeText(MainActivity2.this, "Desfijado!", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.mainDAO().pin(selectedNota.getID(),true);
                    Toast.makeText(MainActivity2.this, "Fijado!", Toast.LENGTH_SHORT).show();
                }
                notas.clear();
                notas.addAll(database.mainDAO().getAll());
                notasListAdapter.notifyDataSetChanged();
                return true;
            case R.id.delete:
                database.mainDAO().delete(selectedNota);
                notas.remove(selectedNota);
                notasListAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity2.this, "Nota eliminada!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}