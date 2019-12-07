package com.example.repertoireapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The menu that enables the user to sort songs by various categories and filter them by style tags.
 */
public class FilterMenuActivity extends AppCompatActivity {

    /**
     * Called by Java when the activity launches.
     *
     * @param savedInstanceState Unused.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButtonClicked();
            }
        });


        Button filterButton = findViewById(R.id.doFilterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unused) {
                filterButtonClicked();
            }
        });

    }

    /**
     * Called when the filter button is clicked. Sends an intent back to MainActivity telling it
     * what button was checked.
     */
    public void filterButtonClicked() {
        RadioButton aToZButton = findViewById(R.id.aToZButton);
        RadioButton zToAButton = findViewById(R.id.zToAButton);
        RadioButton leastRecentButton = findViewById(R.id.leastRecentButton);
        RadioButton mostRecentButton = findViewById(R.id.mostRecentButton);
        RadioButton leastPlayedButton = findViewById(R.id.leastPlayedButton);
        RadioButton mostPlayedButton = findViewById(R.id.mostPlayedButton);
        RadioButton fastestButton = findViewById(R.id.fastestButton);
        RadioButton slowestButton = findViewById(R.id.slowestButton);

        Intent intent = new Intent(this, MainActivity.class);

        if (aToZButton.isChecked()) {
            intent.putExtra("Filter", "A To Z");
        } else if (zToAButton.isChecked()) {
            intent.putExtra("Filter", "Z To A");
        } else if (leastRecentButton.isChecked()) {
            intent.putExtra("Filter", "Least Recent");
        } else if (mostRecentButton.isChecked()) {
            intent.putExtra("Filter", "Most Recent");
        } else if (leastPlayedButton.isChecked()) {
            intent.putExtra("Filter", "Least Played");
        } else if (mostPlayedButton.isChecked()) {
            intent.putExtra("Filter", "Most Played");
        } else if (fastestButton.isChecked()) {
            intent.putExtra("Filter", "Fastest");
        } else if (slowestButton.isChecked()) {
            intent.putExtra("Filter", "Slowest");
        }

        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Called when the cancel button is clicked. Closes the window.
     */
    public void cancelButtonClicked() {
        finish();
    }

}
