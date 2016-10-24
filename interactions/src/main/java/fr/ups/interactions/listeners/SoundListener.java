package fr.ups.interactions.listeners;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * SoundListener: listens to sound.
 */
public class SoundListener implements DeviceInteractionListener {

    // OnSoundListener that is called when sound is detected.
    private OnSoundListener soundListener;

    private static final int POLL_FREQ_MS = 200;

    private MediaRecorder mediaRecorder = null;

    /**
     * The sound interaction interface.
     * Implements methods related to sound recognition.
     */
    public interface OnSoundListener {

        /**
         * TODO
         */
        void onMicrophoneBlow();
    }

    /**
     * Setter for the OnSoundListener.
     *
     * @param listener instance of an onSoundListener
     */
    public void setOnSensorActionListener(OnSoundListener listener) {
        soundListener = listener;
    }

    Thread ampPollThread = new Thread() {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    sleep(POLL_FREQ_MS);
                    Log.d("AMP", Double.toString(getAmplitude()));
                    if (getAmplitude() > 0.5) {
                        soundListener.onMicrophoneBlow();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void register() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("/dev/null");

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();

            ampPollThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deregister() {
        if (mediaRecorder != null) {
            ampPollThread.interrupt();

            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private double getAmplitude() {
        if (mediaRecorder != null) {
            return mediaRecorder.getMaxAmplitude();

        } else {
            return 0;
        }
    }
}
