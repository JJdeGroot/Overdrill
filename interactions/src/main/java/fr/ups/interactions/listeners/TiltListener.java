package fr.ups.interactions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * TiltListener: listens for tilting the device events
 */
public class TiltListener implements SensorInteractionListener {

    // OnTiltListener that is called when tilt is detected.
    private OnTiltListener tiltListener;

    // Tilt settings
    private static final int TILT_SLOP_TIME_MS = 500;

    private long tiltTimestamp;
    private float[] history = new float[2];

    /**
     * The tilt gesture interface.
     * Implements methods related to tilt gestures.
     */
    public interface OnTiltListener {

        /**
         * Will be called when a tilt up gesture is detected.
         */
        void onTiltUp();

        /**
         * Will be called when a tilt down gesture is detected.
         */
        void onTiltDown();

        /**
         * Will be called when a tilt left gesture is detected.
         */
        void onTiltLeft();

        /**
         * Will be called when a tilt right gesture is detected.
         */
        void onTiltRight();
    }

    /**
     * Setter for the OnTiltListener.
     *
     * @param listener instance of an onTiltListener
     */
    public void setOnSensorActionListener(OnTiltListener listener) {
        tiltListener = listener;
    }

    @Override
    public List<Integer> getSensorTypes() {
        List<Integer> sensorList = new ArrayList<>();
        sensorList.add(Sensor.TYPE_ACCELEROMETER);
        return sensorList;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (tiltListener != null) {
            float xChange = history[0] - event.values[0];
            float yChange = history[1] - event.values[1];

            final long now = System.currentTimeMillis();

            // Ignore tilt events too close to each other
            if (tiltTimestamp + TILT_SLOP_TIME_MS > now) {
                return;
            }

            tiltTimestamp = now;
            history[0] = event.values[0];
            history[1] = event.values[1];

            if (xChange > 2) {
                tiltListener.onTiltLeft();
                Log.d("Tilt: ", "Left");

            } else if (xChange < -2) {
                tiltListener.onTiltRight();
                Log.d("Tilt: ", "Right");
            }

            if (yChange > 2) {
                tiltListener.onTiltUp();
                Log.d("Tilt: ", "Up");

            } else if (yChange < -2) {
                tiltListener.onTiltDown();
                Log.d("Tilt: ", "Down");
            }
        }
    }
}
