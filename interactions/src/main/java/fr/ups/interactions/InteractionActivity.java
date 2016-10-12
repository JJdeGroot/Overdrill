package fr.ups.interactions;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.ArrayList;

import fr.ups.interactions.listeners.ButtonListener;
import fr.ups.interactions.listeners.LuxListener;
import fr.ups.interactions.listeners.ShakeListener;
import fr.ups.interactions.model.Interaction;
import fr.ups.interactions.model.InteractionManager;

/**
 * Abstract activity which provides easy access to sensor actions
 */
public abstract class InteractionActivity extends AppCompatActivity {

    private ArrayList<Interaction> interactions;

    private SensorManager sensorManager;
    private InteractionManager interactionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Managers
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.interactionManager = new InteractionManager();

        // Register interactions
        this.interactions = getInteractions();
        registerActionListeners();
    }

    /**
     * Returns a list of all enabled interactions
     * @return List of Interaction objects.
     */
    protected abstract ArrayList<Interaction> getInteractions();

    /**
     * Registers the action listener based on the integer array passed.
     * The integer array reflects values from SensorListenerType.class.
     */
    private void registerActionListeners() {
        for(Interaction interaction : interactions) {
            switch(interaction) {

                case LUX:
                    registerLuxListener();
                    break;

                case CAMERA:
                    // TODO: Register camera listener
                    break;

                case SHAKE:
                    registerShakeListener();
                    break;

                case VOLUME_UP:
                    registerVolumeUpListener();
                    break;

                case VOLUME_DOWN:
                    registerVolumeDownListener();
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
        interactionManager.onPauseInteractions(sensorManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        interactionManager.onResumeInteractions(sensorManager);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }





    /***** INTERACTIONS *****/

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
        interactionManager.addInteractionListener(shakeListener);
    }

    // BUTTON LISTENERS ----------------------------------------------------------------------------

    private boolean volumeUp, volumeDown;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            // Check if volume up interaction is enabled.
            if (volumeUp) {
                onVolumeUp();
                return true;
            }
        }

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            // Check if volume down interaction is enabled
            if(volumeDown) {
                onVolumeDown();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /***** VOLUME UP *****/

    private void registerVolumeUpListener() {
        volumeUp = true;
    }

    private void onVolumeUp() {
        handleInteraction(Interaction.VOLUME_UP);
    }

    /***** VOLUME DOWN *****/
    private void registerVolumeDownListener() {
        volumeDown = true;
    }

    private void onVolumeDown() {
        handleInteraction(Interaction.VOLUME_DOWN);
    }

    //
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
        interactionManager.addInteractionListener(buttonListener);
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
        interactionManager.addInteractionListener(luxListener);
    }

}