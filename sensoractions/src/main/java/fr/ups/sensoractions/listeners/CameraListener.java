package fr.ups.sensoractions.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

/**
 * CameraListener
 * TODO
 */
public class CameraListener implements DeviceActionListener {

    // OnCameraListener that is called when the camera is detected.
    private OnCameraListener cameraListener;

    /**
     * The camera interface.
     * Implements methods related to camera.
     */
    public interface OnCameraListener {

        /**
         * TODO
         */
        void onAction();
    }

    /**
     * Setter for the OnCameraListener.
     *
     * @param listener instance of an OnCameraListener
     */
    public void setOnSensorActionListener(OnCameraListener listener) {
        cameraListener = listener;
    }

}
