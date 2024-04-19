package com.example.spawtify;

import android.app.Application;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.databinding.ActivityBrowseSongsBinding;
import com.example.spawtify.databinding.FragmentBrowseSongsBinding;
import com.example.spawtify.viewHolders.SongModel;
import com.example.spawtify.viewHolders.Song_RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseSongsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseSongsFragment extends Fragment {

    //  Used for storing int extras to place into viewValue
    //  -1 = came from MainActivity
    //  0 = edit song
    //  1 = delete song
    private static final String VIEW_VALUE = "com.example.spawtify.VIEW_VALUE";
    private static final String FILTER_STRING = "com.example.spawtify.FILTER_STRING";
    private static final String FILTER_VALUE = "com.example.spawtify.FILTER_VALUE";
    private static final String EXPLICIT_BOOLEAN = "com.example.spawtify.EXPLICIT_BOOLEAN";

    FragmentBrowseSongsBinding binding;

    //  These values will be used in if statements to determine what activity user
    //  came from
    private final int editSong = 0;
    private final int deleteSong = 1;
    private final int mainActivity = -1;

    //  These values will be used in if statements to determine what filter will be applied
    private final int noFilters = 2;
    private final int artistFilter = 3;
    private final int albumFilter = 4;
    private final int genreFilter = 5;
    private final int explicitFilter = 6;


    //  List of SongModel objects called songModels
    //  A SongModel contains all of the same fields as a song, used by RecyclerViewAdapter
    //  to display our songs
    List<SongModel> songModels = new ArrayList<>();

    List<Song> songList = new ArrayList<>();

    //  Object of SpawtifyRepository so we can interact with the database
    SpawtifyRepository spawtifyRepository;

    //  viewValue decides how to display browse songs
    private int viewValue = mainActivity;

    //  filterValues decides which filter to apply to the list of songs
    private int filterValue;

    private RecyclerView recyclerView;

    Song_RecyclerViewAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BrowseSongsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrowseSongsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowseSongsFragment newInstance(String param1, String param2) {
        BrowseSongsFragment fragment = new BrowseSongsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        binding = FragmentBrowseSongsBinding.inflate(getLayoutInflater());

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_songs, container, false);
    }
}