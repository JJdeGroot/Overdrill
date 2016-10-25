package fr.ups.interactions.listeners;

import android.content.Context;

/**
 * Interface for device actions
 */
public interface DeviceInteractionListener extends InteractionListener {
    /**
     * TODO
     */
    void register(Context context);

    /**
     * TODO
     */
    void deregister(Context context);
}
