package fr.ups.interactions.listeners;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import java.io.File;

/**
 * SoundListener: listens to sound.
 */
public class SoundListener implements DeviceInteractionListener {

    // DEBUG tag
    private static final String TAG = "SoundListener";

    // Sound Listener settings
    private static final int POLL_FREQ_MS = 500;
    private static final int REQUIRED_AMPLITUDE = 30000;

    // OnSoundListener that is called when sound is detected.
    private OnSoundListener soundListener;
    private MediaRecorder mediaRecorder;

    private File directory;
    private int blowCount = 0;

    /**
     * Constructor: passes internal storage directory
     *
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
     *
     * @return Path to temporary audio file storage.
     */
    private String getFileLocation() {
        File audioFile = new File(directory, "audio.m4a");
        return audioFile.getAbsolutePath();
    }

    @Override
    public void register(Context context) {
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

                    if (amplitude > REQUIRED_AMPLITUDE) {
                        blowCount++;
                    }

                    if (blowCount > 3) {
                        //Log.d(TAG, "ON MICROPHONE BLOW");
                        soundListener.onMicrophoneBlow();

                    } else {
                        handler.postDelayed(this, POLL_FREQ_MS);
                    }
                }
            }
        }, POLL_FREQ_MS);
    }

    @Override
    public void deregister(Context context) {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;

            } catch (Exception e) {
                Log.d(TAG, "Error while cleaning up MediaRecorder");
                e.printStackTrace();
            }
        }
    }

}