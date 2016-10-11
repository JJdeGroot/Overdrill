package fr.ups.overdrill.game;

import fr.ups.overdrill.R;

/**
 * Describes all tasks that can be commanded
 * Created by JJ on 11/10/2016.
 */
public enum Task {

    SHAKE               (     R.string.task_shake,                  R.raw.shake                 ),
    VOLUME_UP           (     R.string.task_volume_up,              R.raw.volume_up             ),
    VOLUME_DOWN         (     R.string.task_volume_down,            R.raw.volume_down           ),
    COVER_LIGHT_SENSOR  (     R.string.task_cover_light_sensor,     R.raw.cover_light_sensor    );

    private int textID;
    private int audioID;

    private Task(int textID, int audioID) {
        this.textID = textID;
        this.audioID = audioID;
    }

    public int getTextID() {
        return textID;
    }

    public int getAudioID() {
        return audioID;
    }

}
