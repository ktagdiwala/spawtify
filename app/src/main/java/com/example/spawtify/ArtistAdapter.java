package com.example.spawtify;

import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArtistAdapter  extends RecyclerView.Adapter<ArtistAdapter.MyViewHolder> {
    Context context;
    List<String>artistList;

    private final ArtistRecyclerViewInterface recyclerViewInterface;

    public ArtistAdapter
            (Context context,
             List<String> artistList,
             ArtistRecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.artistList = artistList;
        this.recyclerViewInterface = recyclerViewInterface;
    }
    @NonNull
    @Override
    public ArtistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.artist_recycler_view_row, parent, false);
        return new ArtistAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.MyViewHolder holder, int position) {
        holder.artist.setText(artistList.get(position));
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView artist;
        public MyViewHolder(@NonNull View itemView, ArtistRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            artist = itemView.findViewById(R.id.individualArtist);

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
