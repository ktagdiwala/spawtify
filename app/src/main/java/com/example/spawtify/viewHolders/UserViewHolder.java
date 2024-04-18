package com.example.spawtify.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.R;

/** UserViewHolder
 * Renders the recycler view
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

    //Reference to textview in recycler_userlist.xml
    private final TextView userViewItem;
    /** UserViewHolder constructor:
     * Overrides the default constructor to take in a parameter View
     * @param userView the view where the recycler will be rendered
     */
    private UserViewHolder(View userView){
        super(userView);
        //Reference to a single list item in the recycler view
        userViewItem = userView.findViewById(R.id.recyclerItemTextView);
    }

    /** bind:
     * called when we want to bind our items to one of the linear layout Textview items
     * sets the text of the item
     * @param text the text that will be used
     */
    public void bind (String text){
        userViewItem.setText(text);
    }

    /** create:
     * a static version of the ViewHolder
     * @param parent Reference to the recycler object in the Activity layout file
     * @return a UserViewHolder object
     */
    static UserViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_userlist, parent, false);
        //a call to the constructor (similar to singleton pattern)
        return new UserViewHolder(view);
    }
}
