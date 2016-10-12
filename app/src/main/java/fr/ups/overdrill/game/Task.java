package fr.ups.overdrill.game;

import fr.ups.interactions.model.Interaction;
import fr.ups.overdrill.R;

/**
 * Describes all tasks that can be commanded
 * Created by JJ on 11/10/2016.
 */
public enum Task {

    /***** TASKS *****/

    SHAKE(
            Interaction.SHAKE,
            R.string.task_shake,
            R.raw.shake
    ),

    VOLUME_UP           (
            Interaction.VOLUME_UP,
            R.string.task_volume_up,
            R.raw.volume_up
    ),

    VOLUME_DOWN         (
            Interaction.VOLUME_DOWN,
            R.string.task_volume_down,
            R.raw.volume_down
    ),

    COVER_LIGHT_SENSOR  (
            Interaction.LUX,
            R.string.task_cover_light_sensor,
            R.raw.cover_light_sensor
    );


    /***** DEFINITION *****/

    private Interaction interaction;
    private int textID;
    private int audioID;

    private Task(Interaction interaction, int textID, int audioID) {
        this.interaction = interaction;
        this.textID = textID;
        this.audioID = audioID;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public int getTextID() {
        return textID;
    }

    public int getAudioID() {
        return audioID;
    }

}
