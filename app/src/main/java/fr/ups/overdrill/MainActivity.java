package fr.ups.overdrill;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.ups.overdrill.hiscore.HiscoreActivity;
import fr.ups.overdrill.info.InfoActivity;
import fr.ups.sensoractions.SensorActionManager;
import fr.ups.sensoractions.listeners.ButtonListener;
import fr.ups.sensoractions.listeners.LuxListener;
import fr.ups.sensoractions.listeners.ShakeListener;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_INFO = 1,
            REQUEST_CODE_HISCORE = 2;

    private SensorManager sensorManager;
    private SensorActionManager sensorActionManager;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Listen to sensor actions
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorActionManager = new SensorActionManager();

        // TODO: Move that stuff to a better and nicer place. Create a factory?
        // TODO: What if we move that into the library, but don't think so....
        // register a shake listener
        ShakeListener shakeListener = new ShakeListener();
        shakeListener.setOnSensorActionListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                handleShakeEvent();
            }
        });
        sensorActionManager.registerSensorEventListener(shakeListener);

        // register a button listener
        ButtonListener buttonListener = new ButtonListener();
        buttonListener.setOnSensorActionListener(new ButtonListener.OnButtonListener() {
            @Override
            public void onVolumeUp() {
                handleVolumeUpEvent();
            }

            @Override
            public void onVolumeDown() {
                handleVolumeDownEvent();
            }
        });
        sensorActionManager.registerSensorEventListener(buttonListener);

        // register a camera listener
        LuxListener luxListener = new LuxListener();
        luxListener.setOnSensorActionListener(new LuxListener.OnLuxListener() {

            @Override
            public void onLightDark() {
                handleLightDark();
            }
        });
        sensorActionManager.registerSensorEventListener(luxListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            // Info
            Intent intent = new Intent(this, InfoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_INFO);
        } else if (id == R.id.action_sound) {
            // Sound
            // TODO: Change sound setting
        } else if (id == R.id.action_hiscore) {
            // Hisores
            Intent intent = new Intent(this, HiscoreActivity.class);
            startActivityForResult(intent, REQUEST_CODE_HISCORE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorActionManager.onResumeSensorActions(sensorManager);
    }

    @Override
    protected void onPause() {
        sensorActionManager.onPauseSensorActions(sensorManager);
        super.onPause();
    }

    public void handleShakeEvent() {
        Toast.makeText(MainActivity.this, "SHAKE", Toast.LENGTH_SHORT).show();
    }

    public void handleVolumeUpEvent() {
        Toast.makeText(MainActivity.this, "Volume UP", Toast.LENGTH_SHORT).show();
    }

    public void handleVolumeDownEvent() {
        Toast.makeText(MainActivity.this, "Volume DOWN", Toast.LENGTH_SHORT).show();
    }

    public void handleLightDark() {
        Toast.makeText(MainActivity.this, "Front Camera Dark", Toast.LENGTH_SHORT).show();
    }

    public void handleRearCameraDark() {
        Toast.makeText(MainActivity.this, "Rear Camera Dark", Toast.LENGTH_SHORT).show();
    }
}
