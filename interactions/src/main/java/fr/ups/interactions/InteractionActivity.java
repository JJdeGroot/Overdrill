package fr.ups.interactions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;

import fr.ups.interactions.listeners.ButtonListener;
import fr.ups.interactions.listeners.CameraListener;
import fr.ups.interactions.listeners.LuxListener;
import fr.ups.interactions.listeners.ShakeListener;
import fr.ups.interactions.listeners.SoundListener;
import fr.ups.interactions.listeners.TiltListener;
import fr.ups.interactions.model.Interaction;
import fr.ups.interactions.model.InteractionManager;

/**
 * Abstract activity which provides easy access to sensor actions
 */
public abstract class InteractionActivity extends AppCompatActivity {

    private static final String TAG = "InteractionActivity";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap cameraBitmapImage;

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
        registerActionListener(Interaction.COVER_LIGHT_SENSOR);
    }

    /*****
     * STATES
     *****/

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "PAUSING INTERACTIONS");
        super.onPause();
        interactionManager.onPauseInteractions(sensorManager);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "RESUMING INTERACTIONS");
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
     * Returns a list of all enabled interactions
     *
     * @return List of Interaction objects.
     */
    protected abstract ArrayList<Interaction> getInteractions();

    /**
     * Registers the action listener based on the Interaction passed.
     * The interaction reflects values from Interaction.class.
     */
    public void registerActionListener(Interaction interaction) {
        Log.d(TAG, "REGISTERING ACTION LISTENER: " + interaction);

        switch (interaction) {
            case SHAKE_DEVICE:
                Log.d(TAG, "Shake listener registered");
                registerShakeListener();
                break;

            case TILT_DEVICE_UP:
                registerTiltListener();
                break;

            case TILT_DEVICE_RIGHT:
                registerTiltListener();
                break;

            case TILT_DEVICE_DOWN:
                registerTiltListener();
                break;

            case TILT_DEVICE_LEFT:
                registerTiltListener();
                break;

            case COVER_FRONT_CAMERA:
                registerCameraListener();
                break;

            case COVER_REAR_CAMERA:
                registerCameraListener();
                break;

            case COVER_LIGHT_SENSOR:
                registerLuxListener();
                break;

            case PRESS_VOLUME_UP:
                registerVolumeUpListener();
                break;

            case PRESS_VOLUME_DOWN:
                registerVolumeDownListener();
                break;

            case BLOW_INTO_MICROPHONE:
                registerSoundListener();
                break;
        }

        // Resume interactions
        interactionManager.onResumeInteractions(sensorManager);
    }

    /**
     * Removes all interaction listeners
     */
    public void removeActionListeners() {
        Log.d(TAG, "REMOVING ALL ACTION LISTENERS");
        interactionManager.onPauseInteractions(sensorManager);
        interactionManager.removeAllInteractionListeners();
    }

    /**
     * Called on an interaction event
     *
     * @param interaction The interaction which occurred
     */
    protected abstract void handleInteraction(Interaction interaction);


    // SHAKE LISTENER
    private void registerShakeListener() {
        ShakeListener shakeListener = new ShakeListener();
        shakeListener.setOnSensorActionListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                handleInteraction(Interaction.SHAKE_DEVICE);
            }
        });
        interactionManager.addInteractionListener(shakeListener);
    }

    // TILT LISTENER
    private void registerTiltListener() {
        TiltListener tiltListener = new TiltListener();
        tiltListener.setOnSensorActionListener(new TiltListener.OnTiltListener() {
            @Override
            public void onTiltUp() {
                handleInteraction(Interaction.TILT_DEVICE_UP);
            }

            @Override
            public void onTiltDown() {
                handleInteraction(Interaction.TILT_DEVICE_DOWN);
            }

            @Override
            public void onTiltLeft() {
                handleInteraction(Interaction.TILT_DEVICE_LEFT);
            }

            @Override
            public void onTiltRight() {
                handleInteraction(Interaction.TILT_DEVICE_RIGHT);
            }
        });
        interactionManager.addInteractionListener(tiltListener);
    }

    // BUTTON LISTENERS
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

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // Check if volume down interaction is enabled
            if (volumeDown) {
                onVolumeDown();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    // VOLUME UP
    private void registerVolumeUpListener() {
        volumeUp = true;
    }

    private void onVolumeUp() {
        handleInteraction(Interaction.PRESS_VOLUME_UP);
    }

    // VOLUME DOWN
    private void registerVolumeDownListener() {
        volumeDown = true;
    }

    private void onVolumeDown() {
        handleInteraction(Interaction.PRESS_VOLUME_DOWN);
    }

    // BUTTON LISTENER -- TODO: Is not used because onKeyDown event is fired and overridden.
    private void registerButtonListener() {
        ButtonListener buttonListener = new ButtonListener();
        buttonListener.setOnSensorActionListener(new ButtonListener.OnButtonListener() {
            @Override
            public void onVolumeUp() {
                handleInteraction(Interaction.PRESS_VOLUME_UP);
            }

            @Override
            public void onVolumeDown() {
                handleInteraction(Interaction.PRESS_VOLUME_DOWN);
            }
        });
        //interactionManager.addInteractionListener(buttonListener);
    }

    // LUX LISTENER
    private void registerLuxListener() {
        LuxListener luxListener = new LuxListener();
        luxListener.setOnSensorActionListener(new LuxListener.OnLuxListener() {

            @Override
            public void onLightDark() {
                handleInteraction(Interaction.COVER_LIGHT_SENSOR);
            }
        });
        interactionManager.addInteractionListener(luxListener);
    }

    // SOUND LISTENER
    private void registerSoundListener() {
        SoundListener soundListener = new SoundListener();
        soundListener.setOnSensorActionListener(new SoundListener.OnSoundListener() {
            @Override
            public void onMicrophoneBlow() {
                handleInteraction(Interaction.BLOW_INTO_MICROPHONE);
            }
        });

        interactionManager.addInteractionListener(soundListener);
    }

    // CAMERA LISTENER
    // TODO / FIXME => Camera preview will open each time the register gets called
    private void registerCameraListener() {
        CameraListener cameraListener = new CameraListener();
        cameraListener.setOnSensorActionListener(new CameraListener.OnCameraListener() {
            @Override
            public void onFrontCameraCovered() {
                handleInteraction(Interaction.COVER_FRONT_CAMERA);
            }

            @Override
            public void onRearCameraCovered() {
                handleInteraction(Interaction.COVER_REAR_CAMERA);
            }
        });

        Intent cameraPictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraPictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraPictureIntent, REQUEST_IMAGE_CAPTURE);
        }

        interactionManager.addInteractionListener(cameraListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            cameraBitmapImage = (Bitmap) extras.get("data");
        }
    }
}