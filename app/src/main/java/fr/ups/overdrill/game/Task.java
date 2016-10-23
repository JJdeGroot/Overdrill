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
            R.raw.shake_your_device
    ),

    TILT_DEVICE_UP (
            Interaction.TILT_DEVICE_UP,
            R.string.tilt_your_device_up,
            R.raw.tilt_your_device_up
    ),

    TILT_DEVICE_RIGHT (
            Interaction.TILT_DEVICE_RIGHT,
            R.string.tilt_your_device_right,
            R.raw.tilt_your_device_right
    ),

    TILT_DEVICE_DOWN (
            Interaction.TILT_DEVICE_DOWN,
            R.string.tilt_your_device_down,
            R.raw.tilt_your_device_down
    ),

    TILT_DEVICE_LEFT (
            Interaction.TILT_DEVICE_LEFT,
            R.string.tilt_your_device_left,
            R.raw.tilt_your_device_left
    ),

    COVER_FRONT_CAMERA (
            Interaction.COVER_FRONT_CAMERA,
            R.string.cover_the_front_camera,
            R.raw.cover_the_front_camera
    ),

    COVER_REAR_CAMERA (
            Interaction.COVER_REAR_CAMERA,
            R.string.cover_the_rear_camera,
            R.raw.cover_the_rear_camera
    ),

    COVER_LIGHT_SENSOR (
            Interaction.COVER_LIGHT_SENSOR,
            R.string.cover_the_light_sensor,
            R.raw.cover_the_light_sensor
    ),

    PRESS_VOLUME_UP (
            Interaction.PRESS_VOLUME_UP,
            R.string.press_volume_up,
            R.raw.press_volume_up
    ),

    PRESS_VOLUME_DOWN (
            Interaction.PRESS_VOLUME_DOWN,
            R.string.press_volume_down,
            R.raw.press_volume_down
    ),

    BLOW_INTO_MICROPHONE (
            Interaction.BLOW_INTO_MICROPHONE,
            R.string.blow_into_the_microphone,
            R.raw.blow_into_the_microphone
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
