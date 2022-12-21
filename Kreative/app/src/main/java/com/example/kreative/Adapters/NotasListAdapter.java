package com.example.kreative.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kreative.Models.Notas;
import com.example.kreative.NotasClickListener;
import com.example.kreative.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotasListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
Context context;
List<Notas> list;
NotasClickListener listener;

    public NotasListAdapter(Context context, List<Notas> list, NotasClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notas_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
 holder.textView_title.setText(list.get(position).getTitulo());
 holder.textView_title.setSelected(true);

 holder.textView_notas.setText(list.get(position).getNotas());
 holder.textView_date.setText(list.get(position).getDate());
 holder.textView_date.setSelected(true);

 if (list.get(position).isPinned())
 {
     holder.imageView_pin.setImageResource(R.drawable.ic_pin);
 }     else{
     holder.imageView_pin.setImageResource(0);
 }
int color_code = getRandomColor();
 holder.notas_container.setCardBackgroundColor(ContextCompat.getColor(context, color_code));
 holder.notas_container.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         listener.onClick(list.get(holder.getAdapterPosition()));
     }
 });
 holder.notas_container.setOnLongClickListener(new View.OnLongClickListener() {
     @Override
     public boolean onLongClick(View view) {
         listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notas_container);
         return true;

     }
 });
    }
    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color1);
        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
return colorCode.get(random_color);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notas> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}
class NotesViewHolder extends RecyclerView.ViewHolder {

CardView notas_container;
TextView textView_title,textView_notas,textView_date;
ImageView imageView_pin;



    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notas_container = itemView.findViewById(R.id.notas_container);
        textView_date = itemView.findViewById(R.id.textView_date);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_notas = itemView.findViewById(R.id.textView_notas);
        imageView_pin = itemView.findViewById(R.id.ImageView_pin);
    }
}