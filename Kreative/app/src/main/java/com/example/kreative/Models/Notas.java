package com.example.kreative.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notas")

public class Notas implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int ID = 0;
    @ColumnInfo(name="titulo")
    String titulo = "";
    @ColumnInfo(name = "notas")
    String notas = "";
    @ColumnInfo(name = "date")
    String date = "";
    @ColumnInfo(name = "fijado")
    boolean pinned = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
