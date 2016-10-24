package fr.ups.interactions;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract activity to request permissions
 */
public abstract class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "PermissionActivity";
    private static final int REQUEST_CODE_PERMISSIONS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
    }

    /**
     * Returns a String array containing all permissions that are needed
     * @return Array of required permissions
     */
    protected abstract String[] getRequiredPermissions();

    /**
     * Returns a list containing all permissions that have not been granted
     * @return List of permissions yet to be granted
     */
    private String[] getUngrantedPermissions() {
        ArrayList<String> list = new ArrayList<String>();

        for(String permission : getRequiredPermissions()) {
            int granted = ActivityCompat.checkSelfPermission(this, permission);
            if(granted != PackageManager.PERMISSION_GRANTED) {
                list.add(permission);
            }
        }

        return list.toArray(new String[list.size()]);
    }

    /*
    * Checks if all permissions have been granted
    */
    public boolean checkPermissions() {
        // Have all permissions been granted?
        String[] permissions = getUngrantedPermissions();
        if(permissions.length == 0) {
            onPermissionsGranted();
            return true;
        }

        // No: request permissions
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "Request code: " + requestCode + ", permissions: " + Arrays.toString(permissions) + ", grantResults: " + Arrays.toString(grantResults));

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if(arePermissionsGranted(grantResults)) {
                onPermissionsGranted();
            }else{
                onUngrantedPermissions(permissions, grantResults);
            }
        }
    }

    /**
     * Checks if all requested permissions are now granted
     * @param grantResults Grant results
     * @return True if all have been granted, false if not
     */
    private boolean arePermissionsGranted(int[] grantResults) {
        for(int grantResult : grantResults) {
            if(grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Called if all permissions have been granted
     */
    public abstract void onPermissionsGranted();

    /**
     * Called if not all permissions have been granted
     */
    public abstract void onUngrantedPermissions(String[] permissions, int[] grantResults);

}