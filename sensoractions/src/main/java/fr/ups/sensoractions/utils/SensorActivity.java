package fr.ups.sensoractions.utils;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.ups.sensoractions.SensorActionManager;
import fr.ups.sensoractions.listeners.ButtonListener;
import fr.ups.sensoractions.listeners.LuxListener;
import fr.ups.sensoractions.listeners.ShakeListener;

/**
 * Abstract activity which provides easy access to sensor actions
 * Created by JJ on 05/10/2016.
 */
public abstract class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private SensorActionManager sensorActionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.sensorActionManager = new SensorActionManager();
    }

    /***** STATES *****/
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

    /***** SHAKE LISTENER *****/

    /**
     * Registering a shake listener
     */
    protected void registerShakeListener() {
        ShakeListener shakeListener = new ShakeListener();
        shakeListener.setOnSensorActionListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                handleShakeEvent();
            }
        });
        sensorActionManager.registerSensorEventListener(shakeListener);
    }

    /**
     * Called on shake events
     */
    protected abstract void handleShakeEvent();

    /***** BUTTON LISTENER *****/
    protected void registerButtonListener() {
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
        sensorActionManager.registerSensorEventListener(buttonListener);
    }

    /**
     * Called on volume up
     */
    protected abstract void handleVolumeUpEvent();

    /**
     * Called on volume down
     */
    protected abstract void handleVolumeDownEvent();

    /***** LUX LISTENER *****/
    protected void registerLuxListener() {
        LuxListener luxListener = new LuxListener();
        luxListener.setOnSensorActionListener(new LuxListener.OnLuxListener() {

            @Override
            public void onLightDark() {
                handleLightDark();
            }
        });
        sensorActionManager.registerSensorEventListener(luxListener);
    }

    /**
     * Called on dark environmental lightning
     */
    protected abstract void handleLightDark();


}