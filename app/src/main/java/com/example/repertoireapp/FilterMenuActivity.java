package com.example.repertoireapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The menu that enables the user to sort songs by various categories and filter them by style tags.
 */
public class FilterMenuActivity extends AppCompatActivity {
    private MainActivity theActivity;
    private List<Song> songList;
    private LayoutAdapter mAdapter = new MainActivity().getmAdapter();
    /**
     * Called by Java when the activity launches.
     * @param savedInstanceState Unused.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);

        theActivity = new MainActivity();
        songList = theActivity.getSongList();




        // TODO: Literally all of this.

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                cancelButtonClicked();
            }
        });
        Button filterButton = findViewById(R.id.doFilterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
               // MainActivity.cl
                filterButtonClicked();
            }
        });

    }

    public void aToZSort(List<Song> songList) {

        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }

    public void zToAsort(List<Song> songList) {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
        });
    }

    public void filterButtonClicked() {

        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }

    public void cancelButtonClicked() {
        finish();
    }

}
