package fr.ups.overdrill.game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import fr.ups.interactions.InteractionActivity;
import fr.ups.interactions.model.Interaction;

/**
 * Manages the given tasks
 * Created by JJ on 11/10/2016.
 */
public class TaskManager implements Runnable, TaskCallback, SettingsCallback {

    private static final String TAG = "TaskManager";

    private static final long TIME_LIMIT = 5000,
                              TIME_INTERVAL = 10;

    private Context context;
    private Task[] tasks;

    private Task task;

    private TaskCountDown countdown;
    private TaskCallback callback;

    private MediaPlayer audioPlayer;

    /**
     * Creates a TaskManager
     * @param context Context reference
     */
    public TaskManager(Context context) {
        this.context = context;
        this.tasks = Task.values();
        this.playSound = true;
    }

    /**
     * Set callback for task events
     * @param callback The callback
     */
    public void setCallback(TaskCallback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        Log.d(TAG, "Generating and starting task!");

        // Generate new task and registerSoundListener it
        this.task = newTask();
        playAudio(task);
    }

    /**
     * Stops all sound.
     */
    private void stopAudio() {
        if(audioPlayer != null && audioPlayer.isPlaying()) {
            audioPlayer.stop();
            audioPlayer.reset();
        }
    }

    /**
     * Clean-up resources
     */
    public void destroy() {
        cancelCountdown();
        destroyAudioPlayer();
    }

    /**
     * Destroys the audio player
     */
    private void destroyAudioPlayer() {
        if(audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.reset();
            audioPlayer.release();
        }
    }


    /***** SETTINGS CALLBACK *****/
    private boolean playSound;

    @Override
    public void onSoundChange(boolean playSound) {
        this.playSound = playSound;
    }

    /***** AUDIO HANDLING *****/

    private void playAudio(Task task) {
        // Stop previous audio
        stopAudio();

        // Prepare for new audio
        try {
            this.audioPlayer = new MediaPlayer();
            audioPlayer.setOnPreparedListener(new PrepareListener());

            AssetFileDescriptor descriptor = context.getResources().openRawResourceFd(task.getAudioID());
            audioPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());

            audioPlayer.prepareAsync();
        } catch (IOException e) {
            Log.d(TAG, "[ERROR] I/O exception: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Log.d(TAG, "[ERROR] Illegal state: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * Waits for the audio player to complete
     */
    private class PrepareListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.d(TAG, "Audio player has been prepared!");
            onTaskStart(task);
        }
    }


    /***** TASK HANDLING *****/

    /**
     * Determines a new task
     * @return The new task.
     */
    private Task newTask() {
        // List of tasks except current task
        ArrayList<Task> list = new ArrayList<Task>();
        Collections.addAll(list, tasks);
        list.remove(this.task);

        // Return random task
        int random = new Random().nextInt(list.size());
        return list.get(random);
    }

    @Override
    public void onTaskStart(Task task) {
        // Play music
        Log.d(TAG, "Audio player: " + audioPlayer + ", playing sound: " + playSound);
        if(audioPlayer != null && playSound) {
            audioPlayer.start();
        }

        // Notify callback
        callback.onTaskStart(task);

        // Start countdown
        this.countdown = new TaskCountDown(TIME_LIMIT, TIME_INTERVAL);
        countdown.start();
    }

    @Override
    public void onTaskTimer(long timeLeft) {
        callback.onTaskTimer(timeLeft);
    }

    @Override
    public void onInteraction(Interaction interaction) {
        // Check if interaction is correct for given task.
        if(task.getInteraction() == interaction) {
            onTaskDone(task, getCountdownTime());
        }else{
            // Not correct, it is ignored.
        }
    }

    @Override
    public void onTaskDone(Task task, long timeLeft) {
        cancelCountdown();
        callback.onTaskDone(task, timeLeft);
    }

    @Override
    public void onTaskTimeout(Task task) {
        cancelCountdown();
        callback.onTaskTimeout(task);
    }

    /**
     * Cancels the countdown
     */
    public void cancelCountdown() {
        if(countdown != null) {
            countdown.cancel();
        }
    }

    /**
     * Returns the time left on the countdown
     * @return Time left on countdown
     */
    public long getCountdownTime() {
        if(countdown != null) {
            return countdown.getTimeLeft();
        }
        return 0;
    }

    /**
     * Class which handles the countdown.
     */
    private class TaskCountDown extends CountDownTimer {

        private long timeLeft;

        public TaskCountDown(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            Log.d(TAG, "Countdown has finished, task failed.");
            onTaskTimeout(task);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            this.timeLeft = millisUntilFinished;
            onTaskTimer(millisUntilFinished);
        }

        /**
         * Returns the time left on the countdown
         * @return Time left to finish
         */
        public long getTimeLeft() {
            return timeLeft;
        }

    }

}