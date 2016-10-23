package fr.ups.interactions.listeners;

import android.hardware.SensorEventListener;

import java.util.List;

/**
 * Interface for sensor actions
 */
public interface SensorInteractionListener extends InteractionListener, SensorEventListener {
    List<Integer> getSensorTypes();
}
