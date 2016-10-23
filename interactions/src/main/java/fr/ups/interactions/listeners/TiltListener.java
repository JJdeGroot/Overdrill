package fr.ups.interactions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * TiltListener: listens for tilting the device events
 */
public class TiltListener implements SensorInteractionListener {

    // OnTiltListener that is called when tilt is detected.
    private OnTiltListener tiltListener;

    private float[] inclineGravity = new float[3];
    private float[] gravity;
    private float[] geomagnetic;
    private float orientation[] = new float[3];
    private float pitch;
    private float roll;

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
        sensorList.add(Sensor.TYPE_MAGNETIC_FIELD);
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

            //If type is accelerometer only assign values to global property mGravity
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values;

            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values;

                if (isTiltDown()) {
                    Log.d("Tilt: ", "Down");
                    tiltListener.onTiltDown();

                } else if (isTiltUp()) {
                    Log.d("Tilt: ", "Up");
                    tiltListener.onTiltUp();
                }

                // TODO
                //tiltListener.onTiltRight();
                //tiltListener.onTiltLeft();
            }
        }
    }


    private boolean isTiltUp() {
        if (gravity != null && geomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                /*
                * If the roll is positive, you're in reverse landscape (landscape right), and if the roll is negative you're in landscape (landscape left)
                *
                * Similarly, you can use the pitch to differentiate between portrait and reverse portrait.
                * If the pitch is positive, you're in reverse portrait, and if the pitch is negative you're in portrait.
                *
                * orientation -> azimut, pitch and roll
                *
                *
                */

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = gravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on ground or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                /*
                * Float obj1 = new Float("10.2");
                * Float obj2 = new Float("10.20");
                * int retval = obj1.compareTo(obj2);
                *
                * if(retval > 0) {
                * System.out.println("obj1 is greater than obj2");
                * }
                * else if(retval < 0) {
                * System.out.println("obj1 is less than obj2");
                * }
                * else {
                * System.out.println("obj1 is equal to obj2");
                * }
                */
                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 30 && inclination < 40)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean isTiltDown() {
        if (gravity != null && geomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);

            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                pitch = orientation[1];
                roll = orientation[2];

                inclineGravity = gravity.clone();

                double norm_Of_g = Math.sqrt(inclineGravity[0] * inclineGravity[0] + inclineGravity[1] * inclineGravity[1] + inclineGravity[2] * inclineGravity[2]);

                // Normalize the accelerometer vector
                inclineGravity[0] = (float) (inclineGravity[0] / norm_Of_g);
                inclineGravity[1] = (float) (inclineGravity[1] / norm_Of_g);
                inclineGravity[2] = (float) (inclineGravity[2] / norm_Of_g);

                //Checks if device is flat on groud or not
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(inclineGravity[2])));

                Float objPitch = new Float(pitch);
                Float objZero = new Float(0.0);
                Float objZeroPointTwo = new Float(0.2);
                Float objZeroPointTwoNegative = new Float(-0.2);

                int objPitchZeroResult = objPitch.compareTo(objZero);
                int objPitchZeroPointTwoResult = objZeroPointTwo.compareTo(objPitch);
                int objPitchZeroPointTwoNegativeResult = objPitch.compareTo(objZeroPointTwoNegative);

                if (roll < 0 && ((objPitchZeroResult > 0 && objPitchZeroPointTwoResult > 0) || (objPitchZeroResult < 0 && objPitchZeroPointTwoNegativeResult > 0)) && (inclination > 140 && inclination < 170)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

}
