//package com.example.spawtify.viewHolders;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.spawtify.R;
//
///** SongViewHolder
// * Renders the recycler view
// * @author Krishna Tagdiwala
// * @since 04-13-2024
// */
//
//public class SongViewHolder extends RecyclerView.ViewHolder {
//
//    //Reference to textview in recycler_songlist.xml
//    private final TextView songlistViewItem;
//    /** SongViewHolder constructor:
//     * Overrides the default constructor to take in a parameter View
//     * @param songView the view where the recycler will be rendered
//     */
//    private SongViewHolder(View songView){
//        super(songView);
//        //Reference to a single list item in the recycler view
//        songlistViewItem = songView.findViewById(R.id.recyclerItemTextView);
//    }
//
//    /** bind:
//     * called when we want to bind our items to one of the linear layout Textview items
//     * sets the text of the item
//     * @param text the text that will be used
//     */
//    public void bind (String text){
//        songlistViewItem.setText(text);
//    }
//
//    /** create:
//     * a static version of the ViewHolder
//     * @param parent Reference to the recycler object in the Activity layout file
//     * @return a SongViewHolder object
//     */
//    static SongViewHolder create(ViewGroup parent){
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recycler_songlist, parent, false);
//        //a call to the constructor (similar to singleton pattern)
//        return new SongViewHolder(view);
//    }
//}
