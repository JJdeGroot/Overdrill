package fr.ups.sensoractions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * LuxListener
 * TODO
 */
public class LuxListener implements SensorActionListener {

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
         * TODO
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

        //TODO CONSTANT
        if (lightQuantity < MAX_LIGHT_QUANTITY) {
            luxListener.onLightDark();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_LIGHT;
    }
}
