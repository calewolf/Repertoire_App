package com.example.repertoireapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The menu used in adding a new song to the
 */
public class AddSongActivity extends AppCompatActivity {

    /** The text box where the user types the title of the song to be added. */
    private EditText titleTextField;

    /** The text box where the user types the style of the song to be added. */
    private EditText styleTextField;

    /** The text box where the user types the tempo of the song to be added. */
    private EditText tempoTextField;

    /** The text box where the user types the key of the song to be added. */
    private EditText keyTextField;

    /**
     * Opened when the activity is first created.
     * @param savedInstanceState saved state from the last terminated instance
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        titleTextField = findViewById(R.id.titleInputField);
        styleTextField = findViewById(R.id.styleInputField);
        tempoTextField = findViewById(R.id.tempoInputField);
        keyTextField = findViewById(R.id.keyInputField);

        Button cancelButton = findViewById(R.id.cancelButton3);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                cancelButtonClicked();
            }
        });

        Button addSongButton = findViewById(R.id.addNewSongButton);
        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSongButtonClicked();
            }
        });
    }

    /**
     * Called when the cancel button is clicked.
     */
    public void cancelButtonClicked() {
        finish();
    }

    /**
     * Called when the "add song" button is clicked. Gathers entry data and submits it as an intent.
     */
    public void addSongButtonClicked() {
        String titleText = titleTextField.getText().toString();
        String styleText = styleTextField.getText().toString();
        String tempoText = tempoTextField.getText().toString();
        String keyText = keyTextField.getText().toString();

        if (titleText.isEmpty()) {
            titleText = "Untitled Song";
        }

        String[] newSongInfo = new String[]{titleText, styleText, tempoText, keyText};

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("songData", newSongInfo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
