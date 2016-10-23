package fr.ups.interactions.model;

/**
 * Enum describing all possible Sensor actions.
 */
public enum Interaction {

    SHAKE_DEVICE(1),
    TILT_DEVICE_UP(2),
    TILT_DEVICE_RIGHT(3),
    TILT_DEVICE_DOWN(4),
    TILT_DEVICE_LEFT(5),
    COVER_FRONT_CAMERA(6),
    COVER_REAR_CAMERA(7),
    COVER_LIGHT_SENSOR(8),
    PRESS_VOLUME_UP(9),
    PRESS_VOLUME_DOWN(10),
    BLOW_INTO_MICROPHONE(11);

    private int id;

    private Interaction(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
