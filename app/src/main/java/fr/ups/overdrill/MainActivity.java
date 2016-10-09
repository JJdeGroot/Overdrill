package fr.ups.overdrill;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ups.overdrill.hiscore.HiscoreActivity;
import fr.ups.overdrill.info.InfoActivity;
import fr.ups.sensoractions.SensorActivity;

/**
 * Main activity
 */
public class MainActivity extends SensorActivity {

    private static final int REQUEST_CODE_INFO = 1,
                             REQUEST_CODE_HISCORE = 2;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set configuration for registering listener over extra intent
        String listenerList = "1,3";
        getIntent().putExtra(SensorActivity.EXTRA_SENSOR_CONFIG, listenerList);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    // Shake callback

    @Override
    public void handleShakeEvent() {
        Toast.makeText(MainActivity.this, "SHAKE", Toast.LENGTH_SHORT).show();
    }

    // Button callback

    @Override
    public void handleVolumeUpEvent() {
        Toast.makeText(MainActivity.this, "Volume UP", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleVolumeDownEvent() {
        Toast.makeText(MainActivity.this, "Volume DOWN", Toast.LENGTH_SHORT).show();
    }

    // Lux callback

    @Override
    public void handleLightDark() {
        Toast.makeText(MainActivity.this, "Light Sensor is Dark", Toast.LENGTH_SHORT).show();
    }

}
