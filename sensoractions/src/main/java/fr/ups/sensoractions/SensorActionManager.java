package fr.ups.sensoractions;

import android.hardware.Sensor;
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

    /**
     * Returns the sensor action list
     *
     * @return List of all the registered sensors
     */
    public List<ActionListener> getSensors() {
        return sensorActionList;
    }

    /**
     * TODO: Is possible?
     *
     * @param androidVersion
     * @return
     */
    public List<ActionListener> getAllSupportedSensors(String androidVersion) {
        return null;
    }


}
