package fr.ups.interactions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * LuxListener: listens to changes in light sensitivity.
 */
public class LuxListener implements SensorInteractionListener {

    // OnLuxListener that is called when the light sensor is detected.
    private OnLuxListener luxListener;

    // Ambient light level in SI lux units
    private final int MAX_LIGHT_QUANTITY = 5;

    /**
     * The light sensor interface.
     * Implements methods related to camera and light.
     */
    public interface OnLuxListener {

        /**
         * Callback function for when the light sensor gets covered.
         */
        void onLightDark();
    }

    /**
     * Setter for the OnLuxListener.
     *
     * @param listener instance of an OnLuxListener
     */
    public void setOnSensorActionListener(OnLuxListener listener) {
        luxListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent se) {
        float lightQuantity = se.values[0];

        if (lightQuantity < MAX_LIGHT_QUANTITY) {
            luxListener.onLightDark();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public List<Integer> getSensorTypes() {
        List<Integer> sensorList = new ArrayList<>();
        sensorList.add(Sensor.TYPE_LIGHT);
        return sensorList;
    }
}
