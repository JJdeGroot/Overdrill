package fr.ups.overdrill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import fr.ups.sensoractions.ShakeListener;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity implements ShakeListener.OnShakeListener {

    private ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Listen to shake events
        this.shakeListener = new ShakeListener(this);
        shakeListener.setOnShakeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_info) {
            // Info

        }else if(id == R.id.action_sound) {
            // Sound

        }else if(id == R.id.action_hiscore) {
            // Hisores
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShake() {
        Toast.makeText(this, "SHAKE", Toast.LENGTH_SHORT).show();
    }

}
