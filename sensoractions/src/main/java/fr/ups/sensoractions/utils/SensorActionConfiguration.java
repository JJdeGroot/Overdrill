package fr.ups.sensoractions.utils;

import java.util.ArrayList;
import java.util.List;

public class SensorActionConfiguration {

    public static final int LISTENER_LUX = 1;
    public static final int LISTENER_CAMERA = 1;
    public static final int LISTENER_SHAKE = 1;
    public static final int LISTENER_BUTTON = 1;

    public SensorActionConfiguration() {
        registerListener(getAllListeners());
    }

    public SensorActionConfiguration(List<Integer> config) {
        if (config.isEmpty()) {
            //TODO throw exception
        }

        registerListener(config);
    }

    public void registerListener(List<Integer> listenerList) {
        // TODO register
    }

    public void registerListener(int listener) {
        registerListener(new ArrayList<Integer>(listener));
    }

    public void deregisterListener(List<Integer> listenerList) {

    }

    public void deregisterListener(int listener) {
        deregisterListener(new ArrayList<Integer>(listener));
    }

    public List<Integer> getAllListeners() {
        List<Integer> config = new ArrayList<>();
        config.add(LISTENER_LUX);
        config.add(LISTENER_CAMERA);
        config.add(LISTENER_SHAKE);
        config.add(LISTENER_BUTTON);

        return config;
    }
}
