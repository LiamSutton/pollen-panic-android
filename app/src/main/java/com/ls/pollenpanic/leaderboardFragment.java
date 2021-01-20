package com.ls.pollenpanic;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link leaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class leaderboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public leaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment leaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static leaderboardFragment newInstance(String param1, String param2) {
        leaderboardFragment fragment = new leaderboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    DBHelper db; // used to query the database
    CustomAdapter customAdapter; // used to populate the recyclerview with leaderboard entries
    ArrayList<LeaderboardEntry> leaderboardEntries; // contains all loaded data from the db
    Context context; // current context
    RecyclerView leaderboard_rv; // holds leaderboard entries
    Button mainMenuBtn; // used to return to the main menu
    NavController navController; // used to facilitate navigation
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // perform initialisations and get references
        leaderboardEntries = new ArrayList<LeaderboardEntry>();
        navController = Navigation.findNavController(view);
        context = getContext();
        leaderboard_rv = (RecyclerView)view.findViewById(R.id.leaderboard_rv);
        mainMenuBtn = (Button)view.findViewById(R.id.leaderboard_to_main_menu_btn);

        db = new DBHelper(context);
        retrieveLeaderboardData(); // fill the arraylist with leaderboard entries

        // initialise adapter and set data source for the leaderboard
        customAdapter = new CustomAdapter(context, leaderboardEntries);
        leaderboard_rv.setAdapter(customAdapter);
        leaderboard_rv.setLayoutManager(new LinearLayoutManager(context));

        // return the user to the main menu
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_leaderboardFragment_to_mainMenuFragment);
            }
        });
    }

    // grabs the top n leaderboard entries and uses them to populate the array list
    void retrieveLeaderboardData() {
        Cursor cursor = db.getTopHighScores(5);

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No high scores to display!", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                leaderboardEntries.add(new LeaderboardEntry(cursor.getShort(0), cursor.getString(1), cursor.getShort(2)));
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }
}