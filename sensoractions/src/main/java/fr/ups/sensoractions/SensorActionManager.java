package fr.ups.sensoractions;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

import fr.ups.sensoractions.listeners.ActionListener;
import fr.ups.sensoractions.listeners.SensorActionListener;

/**
 * TODO
 * Base class for all the sensor actions
 */
public class SensorActionManager {

    private List<ActionListener> sensorActionList = new ArrayList<>();

    public SensorActionManager() {
    }

    public SensorActionManager(List<Integer> listeners) {
        // TODO for having a config
    }

    //TODO: Check if it is already registered
    public void registerSensorEventListener(ActionListener eventListener) {
        if (eventListener != null) {
            sensorActionList.add(eventListener);
        }
    }

    //TODO remove eventListener

    /**
     * Register all the listeners when the android application resumes.
     * This helps with battery consumption.
     *
     * @param sensorManager
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
     * @param sensorManager
     */
    public void onPauseSensorActions(SensorManager sensorManager) {
        for (ActionListener listener : sensorActionList) {
            if (listener instanceof SensorEventListener) {
                SensorEventListener sensorListener = (SensorEventListener) listener;
                sensorManager.unregisterListener(sensorListener);
            }
        }
    }

    public void registerListener(List<Integer> listenerList) {
        for (int listener : listenerList) {
            registerListener(listener);
        }
    }

    public void registerListener(int listener) {
        //TODO
    }

    public void deregisterListener(List<Integer> listenerList) {
        for (int listener : listenerList) {
            deregisterListener(listener);
        }
    }

    public void deregisterListener(int listener) {
        // TODO
    }
}
