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

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView styleText;
        public TextView tempoText;
        public TextView keyText;
        public TextView lastPlayedText;
        public TextView totalPlaytimeText;
        public SongViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            styleText = itemView.findViewById(R.id.menuStyleText);
            tempoText = itemView.findViewById(R.id.menuTempoText);
            keyText = itemView.findViewById(R.id.menuKeyText);
            lastPlayedText = itemView.findViewById(R.id.menuLastPlayedText);
            totalPlaytimeText = itemView.findViewById(R.id.totalPlaytimeText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public LayoutAdapter(ArrayList<Song> exampleList) {
        mySongList = exampleList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_row, parent, false);
        SongViewHolder evh = new SongViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song currentItem = mySongList.get(position);
        holder.titleText.setText(currentItem.getTitle());
        holder.styleText.setText(currentItem.getStyle());
        holder.tempoText.setText(currentItem.getTempo());
        holder.keyText.setText(currentItem.getKey());
        holder.lastPlayedText.setText(currentItem.getLastPlayedDate());
        holder.totalPlaytimeText.setText(currentItem.getTotalPlayMinutes());
    }

    @Override
    public int getItemCount() {
        return mySongList.size();
    }
}
