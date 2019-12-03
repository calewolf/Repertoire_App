package com.example.repertoireapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        System.out.println("Opened add song menu");

        Button cancelButton = findViewById(R.id.cancelButton3);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                cancelButtonClicked();
            }
        });
    }

    public void cancelButtonClicked() {
        finish();
    }
}
