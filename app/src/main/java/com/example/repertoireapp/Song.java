package com.example.repertoireapp;

/**
 * Implements a song to be displayed by song_row.xml.
 */
public class Song { // TODO: Finish the Song class constructor and (maybe) getters/setters
    private String title;

    private String style;

    private int tempo;

    private String key;

    private String lastPlayedDate;

    private int totalPlayMinutes;

    private String tempText;

    public Song(String a) {
        tempText = a;
    }

    public String getTempText() {
        return tempText;
    }
}
