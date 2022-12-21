package com.example.kreative;

import androidx.cardview.widget.CardView;

import com.example.kreative.Models.Notas;

public interface NotasClickListener {
void onClick(Notas notas);
void onLongClick(Notas notas, CardView cardView);
}
