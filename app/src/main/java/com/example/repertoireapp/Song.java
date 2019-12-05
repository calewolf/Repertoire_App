package com.example.repertoireapp;

/**
 * Implements a song to be displayed by song_row.xml.
 */
class Song {

    /** Instance variables */
    private String songTitle;
    private String songStyle;
    private String songTempo;
    private String songKey;
    private String lastPlayedDate;
    private String totalPlayMinutes;
    private int totalPlayMinutesInt;

    /**
     * Constructor.
     * @param title Title of song.
     * @param style Style of song.
     * @param tempo Tempo of song.
     * @param key Key of song.
     * @param date Date song was last played.
     * @param totalPlayMin Total time user has spent playing that song, in minutes.
     */
    Song(String title, String style, String tempo, String key, String date, int totalPlayMin) {
        songTitle = title;
        songStyle = style;
        songTempo = tempo;
        songKey = key;
        lastPlayedDate = "Last played: " + date;
        totalPlayMinutes = "Played for " + totalPlayMin + " min.";
        totalPlayMinutesInt = totalPlayMin;
    }

    /** Getter functions... */

    String getTitle() {
        return songTitle;
    }

    String getStyle() {
        return songStyle;
    }

    String getTempo() {
        return songTempo;
    }

    String getKey() {
        return songKey;
    }

    String getLastPlayedDate() {
        return lastPlayedDate;
    }

    String getTotalPlayMinutes() {
        return totalPlayMinutes;
    }

    /**
     * Sets the "last played" date of the song.
     * @param date The current date passed into the method.
     */
    void setLastPlayedDate(String date) {
        lastPlayedDate = "Last Played: " + date;
    }

    /**
     * Adds to the total playtine count of the song.
     * @param minutes Number of minutes played.
     */
    void addPlaytime (int minutes) {
        totalPlayMinutesInt += minutes;
        totalPlayMinutes = "Played for " + totalPlayMinutesInt + " min.";
    }
}
