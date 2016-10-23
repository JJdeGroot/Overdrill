package fr.ups.interactions.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

/**
 * ButtonListener: listens to button actions.
 */
public class ButtonListener {

    // OnButtonListener that is called when button press is detected.
    private OnButtonListener buttonListener;
    /**
     * The button interaction interface.
     * Implements methods related to physical device buttons.
     */
    public interface OnButtonListener {

        /**
         * TODO
         */
        void onVolumeUp();

        /**
         * TODO
         */
        void onVolumeDown();
    }

    /**
     * Setter for the OnButtonListener.
     *
     * @param listener instance of an onButtonListener
     */
    public void setOnSensorActionListener(OnButtonListener listener) {
        buttonListener = listener;
    }

}
