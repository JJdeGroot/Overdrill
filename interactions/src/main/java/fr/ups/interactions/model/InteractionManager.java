package fr.ups.interactions.model;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

import fr.ups.interactions.listeners.InteractionListener;
import fr.ups.interactions.listeners.SensorInteractionListener;

/**
 * The InteractionManager class manages all interactions.
 */
public class InteractionManager {

    private List<InteractionListener> listeners = new ArrayList<>();

    public InteractionManager() {
    }

    /**
     * Adds an action listener to the listeners.
     *
     * @param listener An ActionListener instance
     */
    public void addInteractionListener(InteractionListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Removes an action listener to the listeners.
     *
     * @param listener An ActionListener instance
     */
    public void removeInteractionListener(InteractionListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Register all the listeners when the android application resumes.
     * This helps with battery consumption.
     *
     * @param sensorManager SensorManager instance
     */
    public void onResumeInteractions(SensorManager sensorManager) {
        for (InteractionListener listener : listeners) {
            if (listener instanceof SensorEventListener) {
                SensorInteractionListener sensorListener = (SensorInteractionListener) listener;
                sensorManager.registerListener(sensorListener,
                        sensorManager.getDefaultSensor(sensorListener.getSensorType()),
                        SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    /**
     * Deregister all the listeners when the android application pauses.
     * This helps with battery consumption.
     *
     * @param sensorManager SensorManager instance
     */
    public void onPauseInteractions(SensorManager sensorManager) {
        for (InteractionListener listener : listeners) {
            if (listener instanceof SensorEventListener) {
                SensorEventListener sensorListener = (SensorEventListener) listener;
                sensorManager.unregisterListener(sensorListener);
            }
        }
    }


}