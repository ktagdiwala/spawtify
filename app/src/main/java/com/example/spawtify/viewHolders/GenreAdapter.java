package com.example.spawtify.viewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {
    Context context;
    List<String> genreList;

    private final GenreRecyclerViewInterface recyclerViewInterface;

    public GenreAdapter(Context context, List<String> genreList, GenreRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.genreList = genreList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public GenreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genre_recycler_view_row,parent,false);
        return new GenreAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.MyViewHolder holder, int position) {
        holder.genre.setText(genreList.get(position));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView genre;
        public MyViewHolder(@NonNull View itemView, GenreRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            genre = itemView.findViewById(R.id.individualGenre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
