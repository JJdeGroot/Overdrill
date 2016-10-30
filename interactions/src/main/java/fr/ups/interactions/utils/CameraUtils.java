package fr.ups.interactions.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import java.io.File;

public class CameraUtils {

    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = android.hardware.Camera.open();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Get the picture taken and process it.
     *
     * @return the picture taken
     */
    public static File getOutputMediaFile() {
        /*File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "OverdrillCameraListener");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
        */
        return null;
    }
}
