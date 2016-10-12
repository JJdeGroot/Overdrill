package fr.ups.interactions;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import fr.ups.interactions.listeners.ButtonListener;
import fr.ups.interactions.listeners.LuxListener;
import fr.ups.interactions.listeners.ShakeListener;
import fr.ups.interactions.model.Interaction;
import fr.ups.interactions.model.InteractionManager;

/**
 * Abstract activity which provides easy access to sensor actions
 */
public abstract class InteractionActivity extends AppCompatActivity {

    private Interaction[] interactions;

    private SensorManager sensorManager;
    private InteractionManager sensorActionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sensor manager
        this.sensorActionManager = new InteractionManager();
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Register interactions
        this.interactions = getInteractions();
        registerActionListeners();
    }

    /**
     * Returns an array of all enabled interactions
     * @return Array of Interaction objects.
     */
    protected abstract Interaction[] getInteractions();


    /**
     * Registers the action listener based on the integer array passed.
     * The integer array reflects values from SensorListenerType.class.
     */
    private void registerActionListeners() {
        for (Interaction interaction : interactions) {

            switch (interaction.getID()) {
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

    /**
     * Called on an interaction event
     * @param interaction The interaction which occurred
     */
    protected abstract void handleInteraction(Interaction interaction);


    // SHAKE LISTENER ------------------------------------------------------------------------------
    private void registerShakeListener() {
        ShakeListener shakeListener = new ShakeListener();
        shakeListener.setOnSensorActionListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                handleInteraction(Interaction.SHAKE);
            }
        });
        sensorActionManager.addSensorEventListener(shakeListener);
    }


    // BUTTON LISTENER -----------------------------------------------------------------------------
    private void registerButtonListener() {
        ButtonListener buttonListener = new ButtonListener();
        buttonListener.setOnSensorActionListener(new ButtonListener.OnButtonListener() {
            @Override
            public void onVolumeUp() {
                handleInteraction(Interaction.VOLUME_UP);
            }

            @Override
            public void onVolumeDown() {
                handleInteraction(Interaction.VOLUME_DOWN);
            }
        });
        sensorActionManager.addSensorEventListener(buttonListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            handleInteraction(Interaction.VOLUME_UP);
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            handleInteraction(Interaction.VOLUME_DOWN);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // LUX LISTENER --------------------------------------------------------------------------------
    private void registerLuxListener() {
        LuxListener luxListener = new LuxListener();
        luxListener.setOnSensorActionListener(new LuxListener.OnLuxListener() {

            @Override
            public void onLightDark() {
                handleInteraction(Interaction.LUX);
            }
        });
        sensorActionManager.addSensorEventListener(luxListener);
    }

}