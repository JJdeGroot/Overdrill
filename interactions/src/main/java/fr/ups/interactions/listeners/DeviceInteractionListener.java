package fr.ups.interactions.listeners;

import android.content.Context;

/**
 * Interface for phyical device action listeners
 */
public interface DeviceInteractionListener extends InteractionListener {

    /**
     * Register the device specific action listener.
     *
     * @param context The Activity context
     */
    void register(Context context);

    /**
     * Deregister the device specific action listener.
     *
     * @param context The Activity context.
     */
    void deregister(Context context);
}
