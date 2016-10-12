package fr.ups.interactions.listeners;

import android.hardware.SensorEventListener;

/**
 * Interface for sensor actions
 */
public interface SensorInteractionListener extends InteractionListener, SensorEventListener {
    int getSensorType();
}
