package com.example.repertoireapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Takes data from the song list passed in and puts it in the RecyclerView.
 * I still can't explain most of this. I followed a tutorial.
 */
public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SongViewHolder> {

    /** A reference to the song list passed in. */
    private ArrayList<Song> mySongList;


    public static class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView tempText;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tempText = itemView.findViewById(R.id.titleText);
        }
    }

    public LayoutAdapter(ArrayList<Song> exampleList) {
        mySongList = exampleList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_row, parent, false);
        SongViewHolder evh = new SongViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song currentItem = mySongList.get(position);
        holder.tempText.setText(currentItem.getTempText());
    }

    @Override
    public int getItemCount() {
        return mySongList.size();
    }
}
