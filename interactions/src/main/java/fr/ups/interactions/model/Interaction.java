package fr.ups.interactions.model;

/**
 * Enum describing all possible Sensor actions.
 */
public enum Interaction {

    LUX(1),
    CAMERA(2),
    SHAKE(3),
    BUTTON(4),
    VOLUME_UP(5),
    VOLUME_DOWN(6);

    private int id;

    private Interaction(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
