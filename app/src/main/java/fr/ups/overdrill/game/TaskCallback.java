package fr.ups.overdrill.game;

/**
 * Task callback
 * Created by JJ on 11/10/2016.
 */
public interface TaskCallback {

    void onTaskStart(Task task);
    void onTaskTimer(long timeLeft);
    void onTaskEvent(Task task);
    void onTaskDone(Task task);
    void onTaskWrong(Task givenTask, Task executedTask);
    void onTaskTimeout(Task task);

}
