package com.example.repertoireapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddSongActivity extends AppCompatActivity {

    private EditText titleTextField = findViewById(R.id.titleInputField);
    private EditText styleTextField = findViewById(R.id.styleInputField);
    private EditText tempoTextField = findViewById(R.id.tempoInputField);
    private EditText keyTextField = findViewById(R.id.keyInputField);
    private Button cancelButton = findViewById(R.id.cancelButton3);
    private Button addSongButton = findViewById(R.id.addNewSongButton);
    private String titleText;
    private String styleText;
    private String tempoText;
    private String keyText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        System.out.println("Opened add song menu");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                cancelButtonClicked();
            }
        });

        addSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSongButtonClicked();
            }
        });
    }

    public void cancelButtonClicked() {
        finish();
    }
    public void addSongButtonClicked() {
        titleText = titleTextField.getText().toString();
        styleText = styleTextField.getText().toString();
        tempoText = tempoTextField.getText().toString();
        keyText = keyTextField.getText().toString();

        if (titleText.isEmpty()) {
            titleText = "Untitled Song";
        }

        Song newSong = new Song(titleText, styleText, tempoText, keyText, "Last Played: (Today)", "Played 0 times");
        MainActivity.insertSong(newSong);
    }
}
