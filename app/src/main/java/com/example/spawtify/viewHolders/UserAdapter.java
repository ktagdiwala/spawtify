package com.example.spawtify.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.spawtify.Database.entities.User;

/** UserAdapter
 * An adapter that will convert the ViewModel and ViewHolder
 * into an object that Android can use to display on the device
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */
public class UserAdapter extends ListAdapter<User, UserViewHolder> {

    /** UserAdapter constructor:
     * Overrides the default constructor to take in a parameter
     * @param diffCallback enables the adapter to figure out if records are the same or different
     *                     to determine whether or not it should be rendered
     */
    public UserAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback){
        super(diffCallback);
    }

    /** onCreateViewHolder:
     * creates the recycler widget and binds it to the Activity
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return a UserViewHolder
     */
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return UserViewHolder.create(parent);
    }

    /** onBindViewHolder:
     * sets the text of the next/previous items as the user scrolls through the list of items
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User current = getItem(position);
        holder.bind(current.getUsername());
    }


    public static class UserDiff extends DiffUtil.ItemCallback<User>{

        /** areItemsTheSame:
         * checks to see if the oldItem and newItem are the same object
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return true if old and new item are the same, false otherwise
         */
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem == newItem;
        }

        /** areContentsTheSame:
         * checks to see if the contents of oldItem and newItem are the same
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return false if at least one field differs between oldItem and newItem
         */
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return (oldItem.getUserId()== newItem.getUserId())&&
                    oldItem.getUsername().equals(newItem.getUsername())&&
                    oldItem.getPassword().equals(newItem.getPassword())&&
                    (oldItem.isAdmin() ==(newItem.isAdmin()));
        }
    }

}
