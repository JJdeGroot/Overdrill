package fr.ups.interactions.listeners;

import android.hardware.SensorEventListener;

/**
 * Interface for sensor actions
 */
public interface SensorActionListener extends ActionListener, SensorEventListener {
    int getSensorType();
}
