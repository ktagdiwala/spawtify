package com.example.spawtify.viewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.entities.Playlist;
import com.example.spawtify.R;

import java.util.List;


/** Playlist_RecyclerViewAdapter
 * Adapter class for playlist recycler view
 * @author Krishna Tagdiwala
 * @since 04-16-2024
 */

public class Playlist_RecyclerViewAdapter extends RecyclerView.Adapter<Playlist_RecyclerViewAdapter.MyViewHolder> {
    private final PlaylistRecyclerViewInterface playlistRVInterface;

    Context context;
    List<Playlist> playlists;

    public Playlist_RecyclerViewAdapter(Context context, List<Playlist> playlists,
                                        PlaylistRecyclerViewInterface playlistRVInterface) {
        this.context = context;
        this.playlists = playlists;
        this.playlistRVInterface = playlistRVInterface;
    }

    /** onCreateViewHolder
     * inflates the layout (giving a look to our rows)
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     * @return a ViewHolder object that inflates the layout
     */
    @NonNull
    @Override
    public Playlist_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.playlists_recycler_view_row, parent, false);
        return new Playlist_RecyclerViewAdapter.MyViewHolder(view, playlistRVInterface);
    }

    /** onBindViewHolder
     * Assigns values to the views created (as the user scrolls)
     * in the playlist_recycler_view_row layout file
     * based on the position of the recycler view
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull Playlist_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.playlistTitle.setText(playlists.get(position).getPlaylistTitle());
        holder.playlistDescription.setText(playlists.get(position).getPlaylistDescription());
    }

    /** getItemCount
     * keeps track of how many total items should be displayed in the recycler view
     * @return the total number of items that will be displayed in the recycler view
     */
    @Override
    public int getItemCount() {
        return playlists.size();
    }

    /** MyViewHolder
     * retrieves the views from playlist_recycler_view_row layout file;
     * similar to how the onCreate method works
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView playlistTitle, playlistDescription;

        public MyViewHolder(@NonNull View itemView, PlaylistRecyclerViewInterface playlistRVInterface) {
            super(itemView);
            playlistTitle = itemView.findViewById(R.id.playlistTitleTextview);
            playlistDescription = itemView.findViewById(R.id.playlistDescriptionTextview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(playlistRVInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            playlistRVInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
