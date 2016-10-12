package fr.ups.overdrill.game;

import fr.ups.interactions.model.Interaction;

/**
 * Task callback
 * Created by JJ on 11/10/2016.
 */
public interface TaskCallback {

    void onTaskStart(Task task);

    void onInteraction(Interaction interaction);
    void onTaskDone(Task task);
    void onTaskWrong(Interaction required, Interaction executed);

    void onTaskTimer(long timeLeft);
    void onTaskTimeout(Task task);

}