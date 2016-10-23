package fr.ups.overdrill;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.ups.interactions.model.Interaction;
import fr.ups.overdrill.database.DbHandler;
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

    // User interface
    private TextView timerView, scoreView, commandView;

    // Tasks
    private TaskManager taskManager;
    private boolean isGameOver = false;

    // Hiscore
    private DbHandler handler;
    private int score;

    // Settings
    boolean playingSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Settings
        this.playingSound = true;

        // Database
        this.handler = new DbHandler(this);

        // Timer
        this.timerView = (TextView) findViewById(R.id.main_TimerView);

        // Score
        this.scoreView = (TextView) findViewById(R.id.main_ScoreView);

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
            toggleSound(item);

        } else if (id == R.id.action_hiscore) {
            // Hisores
            Intent intent = new Intent(this, HiscoreActivity.class);
            startActivityForResult(intent, REQUEST_CODE_HISCORE);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Toggles the sound icon
     * @param item Reference to the sound icon
     */
    private void toggleSound(MenuItem item) {
        if(playingSound) {
            item.setIcon(R.drawable.icon_music_disabled);
            playingSound = false;
        }else{
            item.setIcon(R.drawable.icon_music);
            playingSound = true;
        }

        // Notify task manager
        taskManager.onSoundChange(playingSound);
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



    /***** INTERACTION CALLBACK ******/

    @Override
    protected void handleInteraction(Interaction interaction) {
        if(!isGameOver) {
            onInteraction(interaction);
        }
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

        // Show score
        score++; // TODO: More points based on time left.
        scoreView.setText("Score: " + score);

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
        // Store in hiscores
        handler.insertScore("Development", score);

        // Reset game
        this.isGameOver = true;
        showGameOverDialog();

        // Reset score
        this.score = 0;
        onScoreUpdate();
    }

    /**
     * Displays a game over dialog
     */
    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.game_over);
        builder.setCancelable(true);

        // Start a new game
        builder.setPositiveButton(
                "New game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        onNewGame();
                    }
                });

        // Quit app
        builder.setNegativeButton(
                "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder.show();
    }

    /**
     * Called when the score should be updated
     */
    private void onScoreUpdate() {
        scoreView.setText("Score: " + score);
    }

    /**
     * Called to start a new game
     */
    private void onNewGame() {
        this.isGameOver = false;
        onNewTask();
    }

    /**
     * Called to start a new task
     */
    private void onNewTask() {
        taskManager.run();
    }

}