package fr.ups.sensoractions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * LuxListener
 * TODO
 */
public class LuxListener implements SensorActionListener {

    // OnCameraListener that is called when the camera is detected.
    private OnLuxListener luxListener;

    /**
     * The camera interface.
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
        if (lightQuantity < 5) {
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
