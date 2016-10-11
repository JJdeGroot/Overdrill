package fr.ups.overdrill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.ups.overdrill.game.Task;
import fr.ups.overdrill.game.TaskCallback;
import fr.ups.overdrill.game.TaskManager;
import fr.ups.overdrill.hiscore.HiscoreActivity;
import fr.ups.overdrill.info.InfoActivity;
import fr.ups.sensoractions.SensorActivity;
import fr.ups.sensoractions.listeners.ActionListener;

/**
 * Main activity
 */
public class MainActivity extends SensorActivity implements TaskCallback {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_INFO = 1,
                             REQUEST_CODE_HISCORE = 2;

    private TextView timerView, commandView;
    private TaskManager taskManager;
    private boolean isGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set configuration for registering listener over extra intent
        String listenerList = "1,3";
        getIntent().putExtra(SensorActivity.EXTRA_SENSOR_CONFIG, listenerList);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Timer
        this.timerView = (TextView) findViewById(R.id.main_TimerView);
        timerView.setOnClickListener(new RetryListener());

        // Command
        this.commandView = (TextView) findViewById(R.id.main_CommandText);

        // TaskManager
        this.taskManager = new TaskManager(this);
        taskManager.setCallback(this);

        // Start the game!
        onNewGame();
    }

    @Override
    protected void onDestroy() {
        // Clean-up resources
        if(taskManager != null) {
            taskManager.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            // Info
            Intent intent = new Intent(this, InfoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_INFO);
        } else if (id == R.id.action_sound) {
            // Sound
            // TODO: Change sound setting
        } else if (id == R.id.action_hiscore) {
            // Hisores
            Intent intent = new Intent(this, HiscoreActivity.class);
            startActivityForResult(intent, REQUEST_CODE_HISCORE);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Logs a message and shows it to the user
     * @param message The message to log & show
     */
    private void logToast(String message) {
        // Log
        Log.d(TAG, message);

        // Toast
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    // Shake callback

    @Override
    public void handleShakeEvent() {
        onTaskEvent(Task.SHAKE);
    }

    // Button callback

    @Override
    public void handleVolumeUpEvent() {
        onTaskEvent(Task.VOLUME_UP);
    }

    @Override
    public void handleVolumeDownEvent() {
        onTaskEvent(Task.VOLUME_DOWN);
    }

    // Lux callback

    @Override
    public void handleLightDark() {
        onTaskEvent(Task.COVER_LIGHT_SENSOR);
    }



    /***** TASK CALLBACK *****/

    @Override
    public void onTaskStart(Task task) {
        timerView.setText("");
        commandView.setText(task.getTextID());
    }

    @Override
    public void onTaskTimer(long timeLeft) {
        timerView.setText(timeLeft + " ms");
    }

    @Override
    public void onTaskEvent(Task task) {
        logToast("Event: " + task);
        taskManager.onTaskEvent(task);
    }

    @Override
    public void onTaskDone(Task task) {
        logToast("Task " + task + " successfully completed!");
        // TODO: Extract points based on time or something
        onNewGame();
    }

    @Override
    public void onTaskWrong(Task givenTask, Task executedTask) {
        logToast("Wrong task executed ("+executedTask+") should have been: " + givenTask);
        onGameOver();
    }

    @Override
    public void onTaskTimeout(Task task) {
        logToast("Task " + task + " not executed within time");
        onGameOver();
    }

    /***** GAME OVER *****/

    /**
     * Called when it's game over
     */
    private void onGameOver() {
        isGameOver = true;
        timerView.setText(R.string.game_over);
    }

    /**
     * Called to start a new game
     */
    private void onNewGame() {
        isGameOver = false;
        timerView.setText(R.string.new_game);
        onNewTask();
    }

    /**
     * Called to start a new task
     */
    private void onNewTask() {
        taskManager.run();
    }

    /**
     * Listens to clicks on the retry button
     */
    private class RetryListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // Check if pressed on purpose
            if(isGameOver) {
                onNewGame();
            }
        }
    }

}