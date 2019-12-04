package com.example.repertoireapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: The Song objects are kept in an ArrayList. Here they are preset.
        // TODO: Find a way to add and remove songs using the buttons in the app.
        ArrayList<Song> songList = new ArrayList<>();
        songList.add(new Song("Song Title"));
        songList.add(new Song("Here's Song Number Two"));
        songList.add(new Song("Here's a Third Song With A Longer Title (etc.)"));

        mRecyclerView = findViewById(R.id.recyclerView); // Stores reference to the RecyclerView
        mRecyclerView.setHasFixedSize(true); // Makes sure it has a fixed size
        mLayoutManager = new LinearLayoutManager(this);

        // Sets up the RecyclerView adapter. This connects the song list to the layout itself.
        mAdapter = new LayoutAdapter(songList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        // This sets up a listener for the add song button.
        Button addSongButton = findViewById(R.id.addSongButton);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                System.out.println("The add song button was clicked.");
                addSongButtonClicked();
            }
        });

        // This sets up a listener for the add song button.
        Button filterButton = findViewById(R.id.doFilterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                System.out.println("The filter button was clicked.");
                filterButtonClicked();
            }
        });

    }

    public void filterButtonClicked() {
        Intent intent = new Intent(this, FilterMenuActivity.class);
        startActivity(intent);
    }
    public void addSongButtonClicked() {
        Intent intent = new Intent(this, AddSongActivity.class);
        startActivity(intent);
    }
}

// TODO: Create classes for the Filter, Song, and AddSong menus and make the app work.
