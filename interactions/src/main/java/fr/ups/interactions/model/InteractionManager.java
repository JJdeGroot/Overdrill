package fr.ups.interactions.model;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

import fr.ups.interactions.listeners.ActionListener;
import fr.ups.interactions.listeners.SensorActionListener;

/**
 * The InteractionManager class manages all interactions.
 */
public class InteractionManager {

    private List<ActionListener> sensorActionList = new ArrayList<>();

    public InteractionManager() {
    }

    /**
     * Adds an action listener to the sensorActionList.
     *
     * @param actionListener an ActionListener instance
     */
    public void addSensorEventListener(ActionListener actionListener) {
        if (actionListener != null) {
            sensorActionList.add(actionListener);
        }
    }

    /**
     * Removes an action listener to the sensorActionList.
     *
     * @param actionListener an ActionListener instance
     */
    public void removeSensorEventListener(ActionListener actionListener) {
        if (actionListener != null) {
            sensorActionList.remove(actionListener);
        }
    }

    /**
     * Register all the listeners when the android application resumes.
     * This helps with battery consumption.
     *
     * @param sensorManager SensorManager instance
     */
    public void onResumeSensorActions(SensorManager sensorManager) {
        for (ActionListener listener : sensorActionList) {
            if (listener instanceof SensorEventListener) {
                SensorActionListener sensorListener = (SensorActionListener) listener;
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
    public void onPauseSensorActions(SensorManager sensorManager) {
        for (ActionListener listener : sensorActionList) {
            if (listener instanceof SensorEventListener) {
                SensorEventListener sensorListener = (SensorEventListener) listener;
                sensorManager.unregisterListener(sensorListener);
            }
        }
    }
}
