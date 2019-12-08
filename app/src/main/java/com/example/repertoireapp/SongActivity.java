package com.example.repertoireapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * The menu displaying details of a singular song that can be used to log or delete songs.
 * Audio recording and playback features were adapted from Sairam Krishna on tutorialspoint.com
 * https://www.tutorialspoint.com/android/android_audio_capture.htm
 */
public class SongActivity extends AppCompatActivity {

    /** The position of the song in the ArrayList in MainActivity */
    private int position;

    /** The path to save the audio file in the device. */
    String AudioSavePathInDevice = null;

    /** The thing that actually handles the recording */
    MediaRecorder mediaRecorder;

    /** Used to generate a random file name. */
    Random random;

    /** Also used to generate a random file name. */
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";

    /** Code to request permission. */
    public static final int RequestPermissionCode = 1;

    /** The media player. */
    MediaPlayer mediaPlayer ;

    /** The buttons to make the magic happen. */
    Button buttonStart, buttonStop, buttonPlayLastRecordAudio,
            buttonStopPlayingRecording ;

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

        random = new Random();

        buttonStart = findViewById(R.id.button2);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecordingButtonClicked();
            }
        });

        buttonStop = findViewById(R.id.button3);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopButtonClicked();
            }
        });

        buttonPlayLastRecordAudio = findViewById(R.id.button4);
        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {
                playLastRecordingButtonClicked();
            }
        });

        buttonStopPlayingRecording = findViewById(R.id.button5);
        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaybackClicked();
            }
        });

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

        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);
    }

    /**
     * Called to stop playback of the recording.
     */
    public void stopPlaybackClicked() {
        buttonStop.setEnabled(false);
        buttonStart.setEnabled(true);
        buttonStopPlayingRecording.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(true);

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            MediaRecorderReady();
        }
    }

    /**
     * Called to play the recording.
     */
    public void playLastRecordingButtonClicked() {
        buttonStop.setEnabled(false);
        buttonStart.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(AudioSavePathInDevice);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        Toast.makeText(SongActivity.this, "Recording Playing",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Called to stop recording.
     */
    public void stopButtonClicked() {
         mediaRecorder.stop();
         buttonStop.setEnabled(false);
         buttonPlayLastRecordAudio.setEnabled(true);
         buttonStart.setEnabled(true);
         buttonStopPlayingRecording.setEnabled(false);

         Toast.makeText(SongActivity.this, "Recording Completed",
                 Toast.LENGTH_LONG).show();
    }

    /**
     * Called to start recording.
     */
    public void startRecordingButtonClicked() {
        if (checkPermission()) {
            AudioSavePathInDevice =
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            CreateRandomAudioFileName(5) + "AudioRecording.3gp";

            MediaRecorderReady();

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            buttonStart.setEnabled(false);
            buttonStop.setEnabled(true);

            Toast.makeText(SongActivity.this, "Recording started", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }
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

    /**
     * Sets up the media recorder.
     */
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    /**
     * Creates a random audio file name.
     * @param string To help generate the name.
     * @return The audio file name.
     */
    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    /**
     * Requests user permission for microphone.
     */
    private void requestPermission() {
        ActivityCompat.requestPermissions(SongActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    /**
     * Deals with the result of a permission request.
     * @param requestCode The code for success or failure.
     * @param permissions The permissions.
     * @param grantResults Don't know exactly.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(SongActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SongActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    /**
     * Checks to see if the user has granted permission for microphone usage.
     * @return The status of permission.
     */
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
}
