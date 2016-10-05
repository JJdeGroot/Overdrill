package fr.ups.sensoractions.listeners;

import android.hardware.SensorEventListener;


public interface SensorActionListener extends ActionListener, SensorEventListener {
    int getSensorType();
}
