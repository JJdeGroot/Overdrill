package fr.ups.overdrill.game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the given tasks
 * Created by JJ on 11/10/2016.
 */
public class TaskManager implements Runnable, TaskCallback {

    private static final String TAG = "TaskManager";

    private static final long TIME_LIMIT = 10000,
                              TIME_INTERVAL = 10;

    private Context context;
    private Task[] tasks;

    private Task task;
    private TaskCountDown countdown;
    private TaskCallback callback;

    private MediaPlayer audioPlayer;

    public TaskManager(Context context) {
        this.context = context;
        this.tasks = Task.values();
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

        // Generate new task and start it
        this.task = newTask();
        playSound(task);
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
            audioPlayer.release();
        }
    }


    /***** SOUND HANDLING *****/

    private void playSound(Task task) {
        try {
            this.audioPlayer = new MediaPlayer();
            audioPlayer.setOnPreparedListener(new PrepareListener());

            AssetFileDescriptor descriptor = context.getResources().openRawResourceFd(task.getAudioID());
            audioPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());

            audioPlayer.prepareAsync();
        }catch(IOException e) {
            Log.d(TAG, "[ERROR] I/O exception: " + e.getMessage());
            e.printStackTrace();
        }catch(IllegalStateException e) {
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
        for(Task task : tasks) {
            list.add(task);
        }
        list.remove(this.task);

        // Return random task
        int random = new Random().nextInt(list.size());
        return list.get(random);
    }

    @Override
    public void onTaskStart(Task task) {
        // Play music
        if(audioPlayer != null) {
            audioPlayer.start();
        }

        // Start countdown
        this.countdown = new TaskCountDown(TIME_LIMIT, TIME_INTERVAL);
        countdown.start();

        // Notify callback
        callback.onTaskStart(task);
    }

    @Override
    public void onTaskTimer(long timeLeft) {
        callback.onTaskTimer(timeLeft);
    }

    @Override
    public void onTaskEvent(Task task) {
        if(task == this.task) {
            onTaskDone(task);
        }else{
            onTaskWrong(this.task, task);
        }
    }

    @Override
    public void onTaskDone(Task task) {
        cancelCountdown();
        callback.onTaskDone(task);
    }

    @Override
    public void onTaskWrong(Task givenTask, Task executedTask) {
        cancelCountdown();
        callback.onTaskWrong(givenTask, executedTask);
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
     * Class which handles the countdown.
     */
    private class TaskCountDown extends CountDownTimer {

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
            onTaskTimer(millisUntilFinished);
        }

    }

}