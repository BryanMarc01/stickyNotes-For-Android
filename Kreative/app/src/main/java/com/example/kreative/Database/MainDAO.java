package com.example.kreative.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.kreative.Models.Notas;

import java.util.List;

@Dao
public interface MainDAO {
  @Insert(onConflict = REPLACE)
    void insert(Notas notas);

  @Query("SELECT * FROM notas ORDER BY id DESC ")
    List<Notas> getAll();
@Query("UPDATE notas SET titulo = :titulo, notas= :notas WHERE ID = :id")
  void update (int id, String titulo, String notas);
@Delete
void delete (Notas notas);
@Query("UPDATE notas SET fijado = :pin WHERE ID= :id ")
void pin(int id, boolean pin);
}
