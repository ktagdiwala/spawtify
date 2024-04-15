package com.example.spawtify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Song_RecyclerViewAdapter
        extends RecyclerView.Adapter<Song_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<SongModel> songModels;

    public Song_RecyclerViewAdapter(Context context, List<SongModel> songModels){
        this.context = context;
        this.songModels = songModels;
    }

    @NonNull
    @Override
    public Song_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  This is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.songs_recycler_view_row, parent, false);
        return new Song_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Song_RecyclerViewAdapter.MyViewHolder holder, int position) {
        //  Assigning values to the views as they come back on the screen
        //  Based on the position of the recycler view
        holder.tvTitle.setText(songModels.get(position).getSongTitle());
        holder.tvArtist.setText(songModels.get(position).getSongArtist());
        holder.tvAlbum.setText(songModels.get(position).getSongAlbum());
        holder.tvGenre.setText(songModels.get(position).getSongGenre());
    }

    @Override
    public int getItemCount() {
        //  total item count
        return songModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //  Grabs the views from our songs_recycler_view_row layout file
        TextView tvTitle, tvArtist, tvAlbum, tvGenre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.SongTitle);
            tvArtist = itemView.findViewById(R.id.SongArtist);
            tvAlbum = itemView.findViewById(R.id.SongAlbum);
            tvGenre = itemView.findViewById(R.id.SongGenre);
        }
    }
}
