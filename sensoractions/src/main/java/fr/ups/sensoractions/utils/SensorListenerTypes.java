package fr.ups.sensoractions.utils;

import java.util.ArrayList;
import java.util.List;

public class SensorListenerTypes {

    public static final int LISTENER_LUX = 1;
    public static final int LISTENER_CAMERA = 2;
    public static final int LISTENER_SHAKE = 3;
    public static final int LISTENER_BUTTON = 4;

    public static List<Integer> getAllListeners() {
        List<Integer> listeners = new ArrayList<>();
        listeners.add(LISTENER_LUX);
        listeners.add(LISTENER_CAMERA);
        listeners.add(LISTENER_SHAKE);
        listeners.add(LISTENER_BUTTON);

        return listeners;
    }
}
