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

    public ArtistAdapter(Context context, List<String> artistList){
        this.context = context;
        this.artistList = artistList;
    }
    @NonNull
    @Override
    public ArtistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.artist_recycler_view_row, parent, false);
        return new ArtistAdapter.MyViewHolder(view);
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            artist = itemView.findViewById(R.id.SongArtist);
        }
    }
}
