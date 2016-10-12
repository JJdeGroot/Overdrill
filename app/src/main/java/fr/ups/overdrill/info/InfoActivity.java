package fr.ups.overdrill.info;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.ups.interactions.model.Interaction;
import fr.ups.overdrill.R;

/**
 * Activity where all sensors are listed.
 */
public class InfoActivity extends ParentActivity {

    private static final String TAG = "InteractionActivity";
    private static final int REQUEST_CODE = 1;
    private InteractionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // GridView
        GridView gridView = (GridView) findViewById(R.id.activity_info_GridView);
        gridView.setOnItemClickListener(new InteractionListener());

        // Adapter
        this.adapter = new InteractionAdapter(this, Interaction.values());
        gridView.setAdapter(adapter);
    }

    /**
     * Listens to clicks on interaction items
     */
    private class InteractionListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "Clicked on position #" + position);

            Interaction interaction = adapter.getItem(position);

            Intent intent = new Intent(getApplicationContext(), InteractionInfoActivity.class);
            intent.putExtra("interaction", interaction);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    /**
     * Adapter to display interactions
     */
    private class InteractionAdapter extends ArrayAdapter<Interaction> {

        public InteractionAdapter(Context context, Interaction[] interactions) {
            super(context, -1, interactions);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.layout_info, parent, false);
            }

            Interaction interaction = getItem(position);

            // TextView
            TextView textView = (TextView) view.findViewById(R.id.layout_info_TextView);
            textView.setText(interaction.toString());

            return view;
        }
    }

}
