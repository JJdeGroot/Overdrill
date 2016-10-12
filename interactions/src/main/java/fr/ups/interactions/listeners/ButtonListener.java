package fr.ups.interactions.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

/**
 * ButtonListener: listens to button actions.
 */
public class ButtonListener extends BroadcastReceiver implements DeviceActionListener {

    // OnButtonListener that is called when shake is detected.
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

    @Override
    public void onReceive(Context context, Intent intent) {
        KeyEvent keyEvent = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            buttonListener.onVolumeDown();
        }
    }

}
