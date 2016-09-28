package fr.ups.overdrill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public void onShake() {
        Toast.makeText(this, "SHAKE", Toast.LENGTH_SHORT).show();
    }

}
