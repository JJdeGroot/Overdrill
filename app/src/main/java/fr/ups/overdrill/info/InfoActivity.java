package fr.ups.overdrill.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ups.overdrill.R;
import fr.ups.overdrill.game.Task;

/**
 * Activity where all sensors are listed.
 */
public class InfoActivity extends ParentActivity {

    private static final String TAG = "InfoActivity";
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
        this.adapter = new InteractionAdapter(this, Task.values());
        gridView.setAdapter(adapter);
    }

    /**
     * Listens to clicks on interaction items
     */
    private class InteractionListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "Clicked on position #" + position);

            Task task = adapter.getItem(position);

            Intent intent = new Intent(getApplicationContext(), TaskInfoActivity.class);
            intent.putExtra("task", task);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    /**
     * Adapter to display interactions
     */
    private class InteractionAdapter extends ArrayAdapter<Task> {

        public InteractionAdapter(Context context, Task[] tasks) {
            super(context, -1, tasks);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.layout_info, parent, false);
            }

            Task task = getItem(position);

            // IconView
            ImageView iconView = (ImageView) view.findViewById(R.id.layout_info_IconView);
            iconView.setImageResource(task.getIconID());

            // TextView
            TextView textView = (TextView) view.findViewById(R.id.layout_info_TextView);
            textView.setText(task.getTextID());

            return view;
        }
    }

}
