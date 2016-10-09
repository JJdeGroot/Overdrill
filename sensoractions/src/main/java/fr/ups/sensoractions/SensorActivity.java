package fr.ups.sensoractions;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.ups.sensoractions.listeners.ButtonListener;
import fr.ups.sensoractions.listeners.LuxListener;
import fr.ups.sensoractions.listeners.ShakeListener;
import fr.ups.sensoractions.utils.SensorListenerTypes;

/**
 * Abstract activity which provides easy access to sensor actions
 */
public abstract class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private SensorActionManager sensorActionManager;

    public static final String EXTRA_SENSOR_CONFIG = "sensorActionConfiguration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // read the listener list from a putted extra
        // if none is passed then check for a configuration file
        // if none is found then initialize a SensorActionManager with all listeners
        List<Integer> listenerList = new ArrayList<>();
        this.sensorActionManager = new SensorActionManager();

        Intent myIntent = getIntent();
        String listenerStr = myIntent.getStringExtra(EXTRA_SENSOR_CONFIG);

        if (listenerStr != null) {
            for (String s : listenerStr.split(",")) {
                listenerList.add(Integer.parseInt(s));
            }

        } else if (false) { // TODO: How can I access the config file easily???
            /*
            List<Integer> integerList = res.getIntArray(R.array.sensorActionConfiguration);
            this.sensorActionManager = new SensorActionManager();
            */

        } else {
            listenerList.addAll(SensorListenerTypes.getAllListeners());
        }

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // register the action listeners
        registerActionListeners(listenerList);
    }

    /**
     * Registers the action listener based on the integer array passed.
     * The integer array reflects values from SensorListenerType.class.
     *
     * @param listenerList array with action listener id's
     */
    private void registerActionListeners(List<Integer> listenerList) {
        for (int listenerId : listenerList) {

            switch (listenerId) {
                case 1:
                    registerLuxListener();
                    break;

                case 2: //TODO registerCameraListener();
                    break;

                case 3:
                    registerShakeListener();
                    break;

                case 4:
                    registerButtonListener();
                    break;
            }
        }
    }

    // STATES --------------------------------------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorActionManager.onPauseSensorActions(sensorManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorActionManager.onResumeSensorActions(sensorManager);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // SHAKE LISTENER ------------------------------------------------------------------------------
    private void registerShakeListener() {
        ShakeListener shakeListener = new ShakeListener();
        shakeListener.setOnSensorActionListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                handleShakeEvent();
            }
        });
        sensorActionManager.addSensorEventListener(shakeListener);
    }

    /**
     * Called on shake events
     */
    protected abstract void handleShakeEvent();

    // BUTTON LISTENER -----------------------------------------------------------------------------
    private void registerButtonListener() {
        ButtonListener buttonListener = new ButtonListener();
        buttonListener.setOnSensorActionListener(new ButtonListener.OnButtonListener() {
            @Override
            public void onVolumeUp() {
                handleVolumeUpEvent();
            }

            @Override
            public void onVolumeDown() {
                handleVolumeDownEvent();
            }
        });
        sensorActionManager.addSensorEventListener(buttonListener);
    }

    /**
     * Called on volume up
     */
    protected abstract void handleVolumeUpEvent();

    /**
     * Called on volume down
     */
    protected abstract void handleVolumeDownEvent();

    // LUX LISTENER --------------------------------------------------------------------------------
    private void registerLuxListener() {
        LuxListener luxListener = new LuxListener();
        luxListener.setOnSensorActionListener(new LuxListener.OnLuxListener() {

            @Override
            public void onLightDark() {
                handleLightDark();
            }
        });
        sensorActionManager.addSensorEventListener(luxListener);
    }

    /**
     * Called on dark environmental lightning
     */
    protected abstract void handleLightDark();


}