package fr.ups.interactions.listeners;

import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * SoundListener: listens to sound.
 */
public class SoundListener implements DeviceInteractionListener {

    private static final String TAG = "SoundListener";
    private static final int POLL_FREQ_MS = 500;
    private static final int REQUIRED_AMPLITUDE = 30000;

    // OnSoundListener that is called when sound is detected.
    private OnSoundListener soundListener;
    private MediaRecorder mediaRecorder;

    private File directory;

    /**
     * Constructor: passes internal storage directory
     * @param directory Reference to the local storage directory
     */
    public SoundListener(File directory) {
        this.directory = directory;
    }

    /**
     * The sound interaction interface.
     * Implements methods related to sound recognition.
     */
    public interface OnSoundListener {

        /**
         * Called when user blows into the microphone
         */
        void onMicrophoneBlow();
    }

    /**
     * Setter for the OnSoundListener.
     *
     * @param listener instance of an onSoundListener
     */
    public void setOnSensorActionListener(OnSoundListener listener) {
        this.soundListener = listener;
    }

    /**
     * Returns a location to store temporary audio files
     * @return Path to temporary audio file storage.
     */
    private String getFileLocation() {
        File audioFile = new File(directory, "audio.m4a");
        return audioFile.getAbsolutePath();
    }

    @Override
    public void register() {
        String fileLocation = getFileLocation();
        Log.d(TAG, "File location: " + fileLocation);

        // Register MediaRecorder
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(fileLocation);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Handler
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Inside handler");

                // Check amplitude
                if (mediaRecorder != null) {
                    int amplitude = mediaRecorder.getMaxAmplitude();
                    Log.d(TAG, "Amplitude: " + amplitude);

                    if (amplitude > REQUIRED_AMPLITUDE) {
                        Log.d(TAG, "ON MICROPHONE BLOW");
                        soundListener.onMicrophoneBlow();
                    } else {
                        handler.postDelayed(this, POLL_FREQ_MS);
                    }
                }
            }
        }, POLL_FREQ_MS);
    }

    @Override
    public void deregister() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            }catch(Exception e) {
                Log.d(TAG, "Error while cleaning up MediaRecorder");
                e.printStackTrace();
            }
        }
    }

}