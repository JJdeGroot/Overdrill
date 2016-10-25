package fr.ups.overdrill.game;

import fr.ups.interactions.model.Interaction;
import fr.ups.overdrill.R;

/**
 * Describes all tasks that can be commanded
 * Created by JJ on 11/10/2016.
 */
public enum Task {

    /***** TASKS *****/

    SHAKE_DEVICE (
            Interaction.SHAKE_DEVICE,
            R.string.shake_your_device,
            R.string.explanation_shake_your_device,
            R.raw.shake_your_device,
            R.drawable.icon_shake
    ),

    TILT_DEVICE_UP (
            Interaction.TILT_DEVICE_UP,
            R.string.tilt_your_device_up,
            R.string.explanation_tilt_your_device_up,
            R.raw.tilt_your_device_up,
            R.drawable.icon_tilt_up
    ),

    TILT_DEVICE_RIGHT (
            Interaction.TILT_DEVICE_RIGHT,
            R.string.tilt_your_device_right,
            R.string.explanation_tilt_your_device_right,
            R.raw.tilt_your_device_right,
            R.drawable.icon_tilt_right
    ),

    TILT_DEVICE_DOWN (
            Interaction.TILT_DEVICE_DOWN,
            R.string.tilt_your_device_down,
            R.string.explanation_tilt_your_device_down,
            R.raw.tilt_your_device_down,
            R.drawable.icon_tilt_down
    ),

    TILT_DEVICE_LEFT (
            Interaction.TILT_DEVICE_LEFT,
            R.string.tilt_your_device_left,
            R.string.explanation_tilt_your_device_left,
            R.raw.tilt_your_device_left,
            R.drawable.icon_tilt_left
    ),

    COVER_FRONT_CAMERA (
            Interaction.COVER_FRONT_CAMERA,
            R.string.cover_the_front_camera,
            R.string.explanation_cover_the_front_camera,
            R.raw.cover_the_front_camera,
            R.drawable.icon_camera_front
    ),

    COVER_REAR_CAMERA (
            Interaction.COVER_REAR_CAMERA,
            R.string.cover_the_rear_camera,
            R.string.explanation_cover_the_rear_camera,
            R.raw.cover_the_rear_camera,
            R.drawable.icon_camera_rear
    ),

    COVER_LIGHT_SENSOR (
            Interaction.COVER_LIGHT_SENSOR,
            R.string.cover_the_light_sensor,
            R.string.explanation_cover_the_light_sensor,
            R.raw.cover_the_light_sensor,
            R.drawable.icon_light
    ),

    PRESS_VOLUME_UP (
            Interaction.PRESS_VOLUME_UP,
            R.string.press_volume_up,
            R.string.explanation_press_volume_up,
            R.raw.press_volume_up,
            R.drawable.icon_volume_up
    ),

    PRESS_VOLUME_DOWN (
            Interaction.PRESS_VOLUME_DOWN,
            R.string.press_volume_down,
            R.string.explanation_press_volume_down,
            R.raw.press_volume_down,
            R.drawable.icon_volume_down
    ),

    BLOW_INTO_MICROPHONE (
            Interaction.BLOW_INTO_MICROPHONE,
            R.string.blow_into_the_microphone,
            R.string.explanation_blow_into_the_microphone,
            R.raw.blow_into_the_microphone,
            R.drawable.icon_blow
    );


    /***** DEFINITION *****/

    private Interaction interaction;
    private int textID;
    private int explanationID;
    private int audioID;
    private int iconID;

    private Task(Interaction interaction, int textID, int explanationID, int audioID, int iconID) {
        this.interaction = interaction;
        this.textID = textID;
        this.explanationID = explanationID;
        this.audioID = audioID;
        this.iconID = iconID;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public int getTextID() {
        return textID;
    }

    public int getExplanationID() {
        return explanationID;
    }

    public int getAudioID() {
        return audioID;
    }

    public int getIconID() {
        return iconID;
    }

}
