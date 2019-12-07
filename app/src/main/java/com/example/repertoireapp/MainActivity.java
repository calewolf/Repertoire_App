package com.example.repertoireapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

// TODO: (TOGETHER) Finishing touch. Make it look pretty!

// Things our app can't do:
// 1. Save the filter setting when you reopen the filter menu
// 2. Correctly insert a new song into

/**
 * Represents the main activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    /** This is the layout manager for the recycler view. */
    public RecyclerView.LayoutManager mLayoutManager;
    /** This is the list of songs the user has in the app */
    public ArrayList<Song> songList;
    /** This is the recycler view adapter that manages adding data to the view. */
    private LayoutAdapter mAdapter;
    /** This holds the date/time of an added song */
    private String dateTime;

    /**
     * Opened when the activity is first created.
     * @param savedInstanceState saved state from the last terminated instance (unused, but maybe do later?)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = new ArrayList<>();

        // Adds some demo songs.
        Song demo1 = new Song("Demo Song 1", "Pop", "125", "A maj",
                "12/2/19 at 5:02 PM", 60);
        Song demo2 = new Song("Another Song Example With A Long Title", "Rock", "70", "C min",
                "12/1/19 at 3:34 PM", 104);
        Song demo3 = new Song("Geoff Rocks", "Jazz", "140", "A min",
                "11/18/19 at 2:58 AM", 22);
        Song demo4 = new Song("Nutter Butter", "Synth Pop", "135", "D maj",
                "11/13/19 at 5:50 PM", 65);
        Song demo5 = new Song("Lorem Ipsum", "Jazz", "112", "F maj",
                "12/1/19 at 4:00 PM", 65);
        Song demo6 = new Song("An Ode To CS125", "Rock", "150", "D maj",
                "11/1/19 at 5:50 AM", 65);
        Song demo7 = new Song("Insertion Sort Ballad", "Synth Pop", "122", "F maj",
                "11/5/19 at 3:20 PM", 65);
        Song demo8 = new Song("Ack", "Rock", "100", "G min",
                "10/26/19 at 10:09 PM", 65);
        Song demo9 = new Song("The Keys Are Stuck", "EDM", "75", "E min",
                "12/6/19 at 5:01 AM", 65);
        Song demo10 = new Song("Finale", "Rap", "90", "C maj",
                "12/5/19 at 12:00 PM", 65);
        songList.add(demo1);
        songList.add(demo2);
        songList.add(demo3);
        songList.add(demo4);
        songList.add(demo5);
        songList.add(demo6);
        songList.add(demo7);
        songList.add(demo8);
        songList.add(demo9);
        songList.add(demo10);

        aToZSort();

        buildRecyclerView();

        Button addSongButton = findViewById(R.id.addSongButton);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                addSongButtonClicked();
            }
        });

        Button filterButton = findViewById(R.id.doFilterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                System.out.println("The filter button was clicked.");
                filterButtonClicked();
            }
        });
    }

    /**
     * Sets up the recycler view using the adapter.
     */
    public void buildRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new LayoutAdapter(songList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new LayoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("A card was clicked at position: " + position);
                songCardClicked(position);
            }
        });
    }

    /**
     * Called when the filter button is clicked, prompting the filter menu.
     */
    public void filterButtonClicked() {
        Intent intent = new Intent(this, FilterMenuActivity.class);
        startActivityForResult(intent, 2);
    }

    /**
     * Called when the add song button is clicked, prompting the add song menu.
     */
    public void addSongButtonClicked() {
        Intent intent = new Intent(this, AddSongActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Called when a song card is clicked.
     */
    public void songCardClicked(int position) {
        Intent intent = new Intent(this, SongActivity.class);

        intent.putExtra("Position", position);

        Song currentSong = songList.get(position);
        intent.putExtra("Title", currentSong.getTitle());
        intent.putExtra("Style", currentSong.getStyle());
        intent.putExtra("Tempo", currentSong.getTempo());
        intent.putExtra("Key", currentSong.getKey());
        intent.putExtra("LastPlayed", currentSong.getLastPlayedDate());
        intent.putExtra("TotalTimePlayed", currentSong.getTotalPlayMinutes());

        startActivityForResult(intent, 3);
    }

    /**
     * Called when a result is reached from the filter, add song, or song menus.
     * @param requestCode A code representing the request being finished
     * @param resultCode A code representing the success of the result
     * @param data The intent containing info from the result
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) { // If the response comes from the add song menu
            addSong(data);
        } else if (requestCode == 2 && resultCode == RESULT_OK) { // If the response comes from the filter menu
            filter(data);
        } else if (requestCode == 3 && resultCode == RESULT_OK) { // If the response comes from the song menu
            if (data.getStringExtra("Type").equals("Delete")) {
                deleteSong(data);
            } else if (data.getStringExtra("Type").equals("Log")) {
                logSong(data);
            }
        }
    }

    /**
     * Adds a song to the song list and refreshes the list.
     * @param data The intent from the AddSongActivity containing details of the song to be added.
     */
    public void addSong(Intent data) {
        String[] song = data.getStringArrayExtra("songData");

        getDateTime();

        Song newSong = new Song(song[0], song[1], song[2], song[3],
                dateTime, 0);

        songList.add(newSong);
        aToZSort();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the song list and refreshes it.
     * @param data The intent from FilterMenuActivity containing the data to be sorted by.
     */
    public void filter(Intent data) {
        // TODO: Make this a switch statement?
        if (data.getStringExtra("Filter").equals("A To Z")) {
            aToZSort();
        } else if (data.getStringExtra("Filter").equals("Z To A")) {
            zToASort();
        } else if (data.getStringExtra("Filter").equals("Least Recent")) {
            leastRecentSort();
        } else if (data.getStringExtra("Filter").equals("Most Recent")) {
            mostRecentSort();
        } else if (data.getStringExtra("Filter").equals("Least Played")) {
            leastPlayedSort();
        } else if (data.getStringExtra("Filter").equals("Most Played")) {
            mostPlayedSort();
        } else if (data.getStringExtra("Filter").equals("Fastest")) {
            fastestSort();
        } else if (data.getStringExtra("Filter").equals("Slowest")) {
            slowestSort();
        }

        mAdapter.notifyDataSetChanged();
    }

    /**
     * Logs a song by updating its total playtime.
     * @param data The data from SongActivity containing how many minutes to log.
     */
    public void logSong(Intent data) {
        int min = data.getIntExtra("Minutes", -1);
        int positionToModify = data.getIntExtra("Position", -1);

        songList.get(positionToModify).addPlaytime(min);

        getDateTime();

        songList.get(positionToModify).setLastPlayedDate(dateTime);
        mostRecentSort();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Removes a song.
     * @param data The intent containing the position in songList to be removed.
     */
    public void deleteSong(Intent data) {
        int positionToDelete = data.getIntExtra("Position", -1);
        songList.remove(positionToDelete);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Gets the date and time and saves it in the dateTime instance variable.
     */
    public void getDateTime() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/YY hh:mm a");
        dateTime = "";

        try {
            dateTime = newFormat.format(format.parse(new Date().toString()));
            dateTime = dateTime.substring(0, 8) + " at " + dateTime.substring(9);
            System.out.println(dateTime);

            editor.putString("Date and Time Saved", dateTime);

        } catch (java.text.ParseException e) {
            System.out.println("Logging song failed.");
        }

        editor.apply();
    }

    /**
     * Sorts the songs from A-Z.
     */
    public void aToZSort() {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }

    /**
     * Sorts the songs from Z to A.
     */
    public void zToASort() {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
        });
    }

    /**
     * Sorts the songs in order of least recently played.
     */
    public void leastRecentSort() {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o1.getLastPlayedDate().compareTo((o2.getLastPlayedDate()));
            }
        });
    }

    /**
     * Sorts the songs in order of most recent.
     */
    public void mostRecentSort() {
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                return o2.getLastPlayedDate().compareTo((o1.getLastPlayedDate()));
            }
        });
    }

    /**
     * Sorts the songs by least playtime.
     */
    public void leastPlayedSort() {
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

    /**
     * Sorts the songs by most playtime.
     */
    public void mostPlayedSort() {
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

    /**
     * Sorts the songs by tempo.
     */
    public void fastestSort() {
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

    /**
     * Sorts the songs by tempo.
     */
    public void slowestSort() {
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
}
