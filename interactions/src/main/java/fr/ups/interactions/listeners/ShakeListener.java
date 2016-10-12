package fr.ups.interactions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.FloatMath;

/**
 * ShakeListener: listens for shake events.tc
 */
public class ShakeListener implements SensorInteractionListener {

    // Shake detection settings
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private long mShakeTimestamp;
    private int mShakeCount;

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
    public int getSensorType() {
        return Sensor.TYPE_ACCELEROMETER;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shakeListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0;
                }

                mShakeTimestamp = now;
                mShakeCount++;

                shakeListener.onShake();
            }
        }
    }



}
