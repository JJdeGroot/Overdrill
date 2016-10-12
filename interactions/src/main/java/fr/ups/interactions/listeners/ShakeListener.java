package fr.ups.interactions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * ShakeListener: listens for shake events.tc
 */
public class ShakeListener implements SensorActionListener {

    private static final int MIN_MOVEMENT = 15; // minimal movement from x, y & z axis

    private static final int MIN_DIRECTION_CHANGE = 5; // minimum times the shake gesture has to change directions

    private static final int MAX_DURATION_OF_SHAKE = 400; // maximum allowed time for shake gesture

    // first gesture starting time
    private long firstChangeTime = 0;

    // how many movements are considered so far.
    private int directionChangeCount = 0;

    // the last x position.
    private float lastX = 0;

    // the last y position.
    private float lastY = 0;

    // the last z position.
    private float lastZ = 0;

    // OnShakeListener that is called when shake is detected.
    private OnShakeListener shakeListener;

    /**
     * The shake gesture interface.
     * Implements methods related to shake gestures.
     */
    public interface OnShakeListener {

        /**
         * Will be called when a shake gesture is detected.
         */
        void onShake();
    }

    /**
     * Setter for the OnShakeListener.
     *
     * @param listener instance of an onShakeListener
     */
    public void setOnSensorActionListener(OnShakeListener listener) {
        shakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent se) {
        // get axis data from the sensor
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];

        // calculate movement
        float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

        if (totalMovement > MIN_MOVEMENT) {

            // get current milliseconds
            long now = System.currentTimeMillis();

            // store first movement
            if (firstChangeTime == 0) {
                firstChangeTime = now;
            }

            // store movement data
            directionChangeCount++;

            // store current axis sensor data
            lastX = x;
            lastY = y;
            lastZ = z;

            // check how many movements are so far
            if (directionChangeCount >= MIN_DIRECTION_CHANGE) {

                // check total duration
                long totalDuration = now - firstChangeTime;
                if (totalDuration < MAX_DURATION_OF_SHAKE) {

                    // call the onShake method
                    shakeListener.onShake();

                    // reset the parameters
                    reset();
                }
            }

        } else {
            reset();
        }
    }

    /**
     * Resets the shake parameters to their default values.
     */
    private void reset() {
        firstChangeTime = 0;
        directionChangeCount = 0;
        lastX = 0;
        lastY = 0;
        lastZ = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public int getSensorType() {
        return Sensor.TYPE_ACCELEROMETER;
    }
}
