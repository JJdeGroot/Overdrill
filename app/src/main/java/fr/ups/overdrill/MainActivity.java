package fr.ups.overdrill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.ups.interactions.model.Interaction;
import fr.ups.overdrill.game.Task;
import fr.ups.overdrill.game.TaskCallback;
import fr.ups.overdrill.game.TaskManager;
import fr.ups.overdrill.info.HiscoreActivity;
import fr.ups.overdrill.info.InfoActivity;
import fr.ups.interactions.InteractionActivity;

/**
 * Main activity
 */
public class MainActivity extends InteractionActivity implements TaskCallback {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_INFO = 1,
                             REQUEST_CODE_HISCORE = 2;

    private TextView timerView, commandView;
    private TaskManager taskManager;
    private boolean isGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    protected ArrayList<Interaction> getInteractions() {
        ArrayList<Interaction> list = new ArrayList<Interaction>();

        Interaction[] interactions = Interaction.values();
        for(Interaction interaction : interactions) {
            list.add(interaction);
        }

        return list;
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
            // TODO: Toggle sound
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
        Toast toast = Toast.makeText(this, message, 500);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



    /***** INTERACTION CALLBACK ******/

    @Override
    protected void handleInteraction(Interaction interaction) {
        onInteraction(interaction);
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
    public void onInteraction(Interaction interaction) {
        //logToast("Interaction: " + interaction);
        taskManager.onInteraction(interaction);
    }

    @Override
    public void onTaskDone(Task task) {
        logToast("Task " + task + " successfully completed!");
        // TODO: Extract points based on time or something
        onNewTask();
    }

    @Override
    public void onTaskWrong(Interaction required, Interaction executed) {
        logToast("Wrong interaction ("+executed+") should have been: " + required);
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