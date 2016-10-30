package fr.ups.interactions.listeners;

import android.hardware.SensorEventListener;

import java.util.List;

/**
 * Interface for sensor action listeners
 */
public interface SensorInteractionListener extends InteractionListener, SensorEventListener {

    /**
     * Returns a list of the sensor type to register the listener.
     *
     * @return A list of Sensors
     * @See android.hardware.Sensor
     */
    List<Integer> getSensorTypes();
}
