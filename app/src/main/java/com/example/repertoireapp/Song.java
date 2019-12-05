package com.example.repertoireapp;

/**
 * Implements a song to be displayed by song_row.xml.
 */
public class Song { // TODO: Finish the Song class constructor and (maybe) getters/setters
    private String title;

    private String style;

    private String tempo;

    private String key;

    private String lastPlayedDate;

    private String totalPlayMinutes;

    private String tempText;

    public Song(String a, String b, String c, String d, String e, String f) {
        title = a;
        style = b;
        tempo = c;
        key = d;
        lastPlayedDate = e;
        totalPlayMinutes = f;

    }

    public String getTitle() {
        return title;
    }
    public String getStyle() { return style; }
    public String getTempo() { return tempo; }
    public String getKey() { return key; }
    public String getLastPlayedDate() { return lastPlayedDate; }
    public String getTotalPlayMinutes() { return totalPlayMinutes; }
}
