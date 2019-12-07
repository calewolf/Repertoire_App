package com.example.repertoireapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private String dateTime;
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

    public void leastRecentClicked(List<Song> songList) {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getLastPlayedDate().compareTo((o2.getLastPlayedDate()));
            }
        });
    }

    public void mostRecentClicked(List<Song> songList) {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o2.getLastPlayedDate().compareTo((o1.getLastPlayedDate()));
            }
        });
    }

    public void leastPlayedClicked(List<Song> songList) {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                String o1Val = o1.getTotalPlayMinutes();
                String o2Val = o2.getTotalPlayMinutes();
                o1Val = o1Val.replace("Played for ", "");
                o1Val = o1Val.replace(" min", "");
                o1Val = o1Val.replace(".", "");
                o2Val = o2Val.replace("Played for ", "");
                o2Val = o2Val.replace(" min", "");
                o2Val = o2Val.replace(".", "");
                int o1Value = Integer.parseInt(o1Val);
                int o2Value = Integer.parseInt(o2Val);
                return o1Value - o2Value;
            }
        });
    }

    public void mostPlayedClicked(List<Song> songList) {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                String o1Val = o1.getTotalPlayMinutes();
                String o2Val = o2.getTotalPlayMinutes();
                o1Val = o1Val.replace("Played for ", "");
                o1Val = o1Val.replace(" min", "");
                o1Val = o1Val.replace(".", "");
                o2Val = o2Val.replace("Played for ", "");
                o2Val = o2Val.replace(" min", "");
                o2Val = o2Val.replace(".", "");
                int o1Value = Integer.parseInt(o1Val);
                int o2Value = Integer.parseInt(o2Val);
                return o2Value - o1Value;
            }
        });
    }
    public void fastestClicked(List<Song> songList) {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                String o1Tempo = o1.getTempo();
                String o2Tempo = o2.getTempo();
                o1Tempo = o1Tempo.replace(" bpm", "");
                o2Tempo = o2Tempo.replace(" bpm", "");
                int o1TempoValue = Integer.parseInt(o1Tempo);
                int o2TempoValue = Integer.parseInt(o2Tempo);
                return o2TempoValue - o1TempoValue;
            }
        });
    }

    public void slowestClicked(List<Song> songList) {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                String o1Tempo = o1.getTempo();
                String o2Tempo = o2.getTempo();
                o1Tempo = o1Tempo.replace(" bpm", "");
                o2Tempo = o2Tempo.replace(" bpm", "");
                int o1TempoValue = Integer.parseInt(o1Tempo);
                int o2TempoValue = Integer.parseInt(o2Tempo);
                return o1TempoValue - o2TempoValue;
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
