package fr.ups.interactions.listeners;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.ups.interactions.utils.CameraUtils;

/**
 * CameraListener: listens to camera actions
 */
public class CameraListener implements DeviceInteractionListener {

    private static final String TAG = "CameraListener";
    private static final int POLL_FREQ_MS = 500;

    // OnCameraListener that is called when the camera is detected.
    private OnCameraListener cameraListener;

    private Camera camera;
    private CameraPreview cameraPreview;
    private boolean safeToTakePicture = false;
    private Camera.PictureCallback picture;

    /**
     * The camera interface.
     * Implements methods related to camera.
     */
    public interface OnCameraListener {

        /**
         * TODO
         */
        void onFrontCameraCovered();

        /**
         * TODO
         */
        void onRearCameraCovered();
    }

    /**
     * Setter for the OnCameraListener.
     *
     * @param listener instance of an OnCameraListener
     */
    public void setOnSensorActionListener(OnCameraListener listener) {
        cameraListener = listener;
    }

    @Override
    public void register(Context context) {
        if (CameraUtils.checkCameraHardware(context)) {
            this.camera = CameraUtils.getCameraInstance();

            cameraPreview = new CameraPreview(context, camera);

            picture = new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Log.d(TAG, "picture was taken!!!");

                    File pictureFile = CameraUtils.getOutputMediaFile();
                    if (pictureFile == null) {
                        return;
                    }

                    try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        fos.write(data);
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    safeToTakePicture = true;
                }
            };

            // Handler
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (safeToTakePicture) {
                        // Taking a picture
                        Log.d(TAG, "Take Picture");
                        camera.takePicture(null, null, picture);

                    } else {
                        handler.postDelayed(this, POLL_FREQ_MS);
                    }
                }
            }, POLL_FREQ_MS);
        }
    }

    @Override
    public void deregister(Context context) {
        camera.release();
    }

    private class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

        private Camera camera;
        private SurfaceHolder holder;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            this.camera = camera;

            holder = getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
                safeToTakePicture = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder.getSurface() == null) {
                return;
            }

            try {
                camera.stopPreview();

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
