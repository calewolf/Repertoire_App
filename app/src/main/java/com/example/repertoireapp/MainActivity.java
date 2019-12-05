package com.example.repertoireapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// TODO: (ADAM) Implement the "log song" button using the clock on the Android phone.

// TODO: (ADAM) See SongActivity.

// TODO: (ADAM) Implement the filter menu functionality.

// TODO: (TOGETHER) Finishing touch. Make it look pretty!

// TODO: (Extra) Implement an "edit song" button into the song menu.

// TODO: (ADAM) Should prob find a way so the app saves your songs when you exit.

/**
 * Represents the main activity of the app.
 */
public class
MainActivity extends AppCompatActivity {

    /** This is the layout manager for the recycler view. */
    public RecyclerView.LayoutManager mLayoutManager;
    /** This is the list of songs the user has in the app */
    public ArrayList<Song> songList;
    /** This is the recycler view adapter that manages adding data to the view. */
    private LayoutAdapter mAdapter;

    /**
     * Opened when the activity is first created.
     * @param savedInstanceState saved state from the last terminated instance (unused, but maybe do later?)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList = new ArrayList<>();
        Song demo1 = new Song("Demo Song 1", "Pop", "125 bpm", "A maj",
                "12/2/19 at 5:02 PM", 60);
        Song demo2 = new Song("Another Song Example With A Long Title", "Rock", "70 bpm", "C min",
                "12/1/19 at 3:34 PM", 104);
        Song demo3 = new Song("Geoff Rocks", "Jazz", "140 bpm", "A min",
                "11/18/19 at 2:58 AM", 22);
        Song demo4 = new Song("Nutter Butter", "Synth Pop", "135 bpm", "D maj",
                "11/13/19 at 5:50 PM", 65);
        songList.add(demo1);
        songList.add(demo2);
        songList.add(demo3);
        songList.add(demo4);

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

        // TODO: Make each song card clickable to open the song menu and remove/log songs.
    }

    /**
     * Called when the filter button is clicked, prompting the filter menu.
     */
    public void filterButtonClicked() {
        Intent intent = new Intent(this, FilterMenuActivity.class);
        startActivityForResult(intent, 2); // TODO: Implement filter button functionality.
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
     * Called when a result is reached from the filter, add song, or song menus.
     * @param requestCode A code representing the request being finished
     * @param resultCode A code representing the success of the result
     * @param data The intent containing info from the result
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) { // If the response comes from the add song menu
            String[] song = data.getStringArrayExtra("songData");

            // TODO: (ADAM) Find a way to get the date/time the button was pressed. (DONE)
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            //editor.putString("Date and Time Saved", new Date().toString());


            // TODO: (ADAM) Convert it to string format MM/DD/YY at 00:00 AM/PM (DONE)
            String dateTime = preferences.getString("Date and Time Saved", "");

            Song newSong = new Song(song[0], song[1], song[2], song[3],
                    dateTime, 0);

            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

            try {
                Date newDate = format.parse(dateTime);
                System.out.println(dateTime);
                SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/YY hh:mm a");
                //format.applyPattern("MM/dd/YY hh:mm a"); // = new SimpleDateFormat("MM/DD/YY at hh:mm a");
                String date = newFormat.format(format.parse(dateTime));
                String s = date.substring(0, 8) + " at " + date.substring(9);
                System.out.println(s);
                editor.putString("Date and Time Saved", s);
                //System.out.println(date);
                //Log.d("print", dateTime);
            }catch(java.text.ParseException e){

            }

            editor.apply();
            songList.add(newSong);
            mAdapter.notifyDataSetChanged();

        } else if (requestCode == 2 && resultCode == RESULT_OK) { // If the response comes from the filter menu
            System.out.println("Filtering/Sorting...");

            // TODO: (ADAM) Implement filtering.

        } else if (requestCode == 3 && resultCode == RESULT_OK) { // If the response comes from the song menu
            if (data.getStringExtra("Type").equals("Delete")) {
                int positionToDelete = data.getIntExtra("Position", -1);

                songList.remove(positionToDelete);
                mAdapter.notifyDataSetChanged();

            } else if (data.getStringExtra("Type").equals("Log")) {
                int min = data.getIntExtra("Minutes", -1);
                int positionToModify = data.getIntExtra("Position", -1);

                songList.get(positionToModify).addPlaytime(min);

                // TODO: (ADAM) Same as above. Get date/time and pass it to method. (DONE)
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                //editor.putString("Date and Time Saved", new Date().toString());

                String dateTime = preferences.getString("Date and Time Saved", "");
                System.out.println(dateTime);


                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

                try {
                    Date newDate = format.parse(dateTime);
                    System.out.println(dateTime);
                    SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/YY hh:mm a");
                    //format.applyPattern("MM/dd/YY hh:mm a"); // = new SimpleDateFormat("MM/DD/YY at hh:mm a");
                    String date = newFormat.format(format.parse(dateTime));
                    String s = date.substring(0, 8) + " at " + date.substring(9);
                    System.out.println(s);
                    editor.putString("Date and Time Saved", s);
                    //System.out.println(date);
                    //Log.d("print", dateTime);
                }catch(java.text.ParseException e){

                }

                editor.apply();
                /**
                SimpleDateFormat format = new SimpleDateFormat("MM/DD/YY at hh:mm a");
                String date = format.format(Date.parse("Date and Time Saved"));

                 SimpleDateFormat dt = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz YYYY");
                 Date newDate = dt.parse(dateTime);
                 SimpleDateFormat theFormat = new SimpleDateFormat("MM/DD/YY at hh:mm a");
                 String date = theFormat.format(newDate);
                 **/

                //editor.apply();

                songList.get(positionToModify).setLastPlayedDate(dateTime);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
