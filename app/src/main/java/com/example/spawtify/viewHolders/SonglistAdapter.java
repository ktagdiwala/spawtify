//package com.example.spawtify.viewHolders;
//
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.recyclerview.widget.ListAdapter;
//
//import com.example.spawtify.Database.entities.Song;
//
///** SonglistAdapter
// * An adapter that will convert the ViewModel and ViewHolder
// * into an object that Android can use to display on the device
// * @author Krishna Tagdiwala
// * @since 04-13-2024
// */
//public class SonglistAdapter extends ListAdapter<Song, SongViewHolder> {
//
//    /** SonglistAdapter constructor:
//     * Overrides the default constructor to take in a parameter
//     * @param diffCallback enables the adapter to figure out if records are the same or different
//     *                     to determine whether or not it should be rendered
//     */
//    public SonglistAdapter(@NonNull DiffUtil.ItemCallback<Song> diffCallback){
//        super(diffCallback);
//    }
//
//    /** onCreateViewHolder:
//     * creates the recycler widget and binds it to the Activity
//     * @param parent The ViewGroup into which the new View will be added after it is bound to
//     *               an adapter position.
//     * @param viewType The view type of the new View.
//     *
//     * @return a SongViewHolder
//     */
//    @NonNull
//    @Override
//    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
//        return SongViewHolder.create(parent);
//    }
//
//    /** onBindViewHolder:
//     * sets the text of the next/previous items as the user scrolls through the list of items
//     * @param holder The ViewHolder which should be updated to represent the contents of the
//     *        item at the given position in the data set.
//     * @param position The position of the item within the adapter's data set.
//     */
//    @Override
//    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
//        Song current = getItem(position);
//        holder.bind(current.toString());
//    }
//
//
//    public static class SongDiff extends DiffUtil.ItemCallback<Song>{
//
//        /** areItemsTheSame:
//         * checks to see if the oldItem and newItem are the same object
//         * @param oldItem The item in the old list.
//         * @param newItem The item in the new list.
//         * @return true if old and new item are the same, false otherwise
//         */
//        @Override
//        public boolean areItemsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
//            return oldItem == newItem;
//        }
//
//        /** areContentsTheSame:
//         * checks to see if the contents of oldItem and newItem are the same
//         * @param oldItem The item in the old list.
//         * @param newItem The item in the new list.
//         * @return false if at least one field differs between oldItem and newItem
//         */
//        @Override
//        public boolean areContentsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
//            return (oldItem.getSongId()== newItem.getSongId())&&
//                    oldItem.getSongTitle().equals(newItem.getSongTitle())&&
//                    oldItem.getSongArtist().equals(newItem.getSongArtist())&&
//                    oldItem.getSongAlbum().equals(newItem.getSongAlbum())&&
//                    oldItem.getSongGenre().equals(newItem.getSongGenre())&&
//                    (oldItem.isExplicit()==newItem.isExplicit());
//        }
//    }
//
//}
