package com.example.repertoireapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

/**
 * The menu displaying details of a singular song that can be used to log or delete songs.
 */
public class SongActivity extends AppCompatActivity {

    /** The position of the song in the ArrayList in MainActivity */
    private int position;

    /**
     * Called by Java when the activity is created.
     * @param savedInstanceState Saves data from last time. Unused.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_menu);

        Intent intent = getIntent();

        TextView titleText = findViewById(R.id.songTitleText);
        titleText.setText(intent.getStringExtra("Title"));

        TextView menuText = findViewById(R.id.menuStyleText);
        menuText.setText(intent.getStringExtra("Style"));

        TextView tempoText = findViewById(R.id.menuTempoText);
        tempoText.setText(intent.getStringExtra("Tempo"));

        TextView menuKey = findViewById(R.id.menuKeyText);
        menuKey.setText(intent.getStringExtra("Key"));

        TextView lastPlayed = findViewById(R.id.menuLastPlayedText);
        lastPlayed.setText(intent.getStringExtra("LastPlayed"));

        TextView totalPlaytime = findViewById(R.id.menuPlaytimeText);
        totalPlaytime.setText(intent.getStringExtra("TotalTimePlayed"));

        position = intent.getIntExtra("Position", -1);

        Button cancelButton = findViewById(R.id.cancelButton2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                cancelButtonClicked();
            }
        });

        Button logSongButton = findViewById(R.id.logPlayButton);
        logSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logSongButtonClicked();
            }
        });

        Button deleteButton = findViewById(R.id.deleteSongButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButtonClicked();
            }
        });
    }

    /**
     * Called when cancel button is clicked to close the activity.
     */
    public void cancelButtonClicked() {
        finish();
    }

    /**
     * Called when "log song" button is clicked. Records and sends the play time in the text box.
     */
    public void logSongButtonClicked() {
        System.out.println("The log song button was clicked.");
        EditText numMinBox = findViewById(R.id.menuPlaytimeBox);
        if (numMinBox.getText() != null) {
            int minutesToAdd = Integer.parseInt(numMinBox.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Type", "Log");
            intent.putExtra("Minutes", minutesToAdd);
            intent.putExtra("Position", position);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Called when delete button is clicked.
     */
    public void deleteButtonClicked() {
        System.out.println("The delete button was clicked.");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Type", "Delete");
        intent.putExtra("Position", position);
        setResult(RESULT_OK, intent);
        finish();
    }
}
