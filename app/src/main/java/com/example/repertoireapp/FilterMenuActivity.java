package com.example.repertoireapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The menu that enables the user to sort songs by various categories and filter them by style tags.
 */
public class FilterMenuActivity extends AppCompatActivity {

    /**
     * Called by Java when the activity launches.
     * @param savedInstanceState Unused.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);

        // TODO: Literally all of this.

        Button cancelButton = findViewById(R.id.cancelButton);
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
