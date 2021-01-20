package com.ls.pollenpanic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;


/**
 * CustomAdapter is responsible for populating the RecyclerView with LeaderboardEntries
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context; // Current context
    private ArrayList<LeaderboardEntry> leaderboardEntries; // A list of LeaderboardEntries

    // Constructor
    CustomAdapter(Context context, ArrayList<LeaderboardEntry> leaderboardEntries) {
        this.context = context;
        this.leaderboardEntries = leaderboardEntries;
    }

    // Inflates the view using a custom card layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    // Sets the values in the card for each given LeaderboardEntry
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LeaderboardEntry leaderboardEntry = leaderboardEntries.get(position);
        holder.leaderboard_username_tv.setText(leaderboardEntry.getUsername());
        holder.leaderboard_score_tv.setText(String.valueOf(leaderboardEntry.getScore()));
    }

    @Override
    public int getItemCount() {
        return leaderboardEntries.size();
    }

    // Inner class which gets a reference to the Widgets in a card
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leaderboard_username_tv, leaderboard_score_tv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderboard_username_tv = itemView.findViewById(R.id.leaderboard_username_tv);
            leaderboard_score_tv = itemView.findViewById(R.id.leaderboard_score_tv);
        }
    }
}
