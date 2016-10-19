package fr.ups.overdrill.info;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fr.ups.interactions.model.Interaction;
import fr.ups.overdrill.R;
import fr.ups.overdrill.database.DbHandler;
import fr.ups.overdrill.database.Score;

/**
 * Activity where the hiscore list is shown.
 */
public class HiscoreActivity extends ParentActivity {

    private static final String TAG = "HiscoreActivity";
    private ScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiscore);

        // GridView
        GridView gridView = (GridView) findViewById(R.id.activity_hiscore_GridView);
        gridView.setOnItemClickListener(new ScoreListener());

        // Adapter
        this.adapter = new ScoreAdapter(this);
        gridView.setAdapter(adapter);

        // Retrieve scores from local database
        new ScoreTask().execute();
    }

    /**
     * Retrieves scores in a seperate thread
     */
    private class ScoreTask extends AsyncTask<String, Void, ArrayList<Score>> {

        @Override
        protected ArrayList<Score> doInBackground(String... params) {
            DbHandler handler = new DbHandler(getApplicationContext());
            return handler.getScoresHighToLow();
        }

        @Override
        protected void onPostExecute(ArrayList<Score> scores) {
            adapter.addAll(scores);
        }
    }

    /**
     * Listens to clicks on hiscore items
     */
    private class ScoreListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "Clicked on position #" + position);

            Score score = adapter.getItem(position);

            // TODO: Something maybe?
        }
    }

    /**
     * Adapter to display scores
     */
    private class ScoreAdapter extends ArrayAdapter<Score> {

        private SimpleDateFormat format;

        public ScoreAdapter(Context context) {
            super(context, -1, new ArrayList<Score>());
            this.format = new SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss");
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = getLayoutInflater().inflate(R.layout.layout_score, parent, false);
            }

            Score score = getItem(position);

            // Player name
            TextView playerView = (TextView) view.findViewById(R.id.layout_score_PlayerView);
            playerView.setText(score.getPlayer());

            // Score
            TextView scoreView = (TextView) view.findViewById(R.id.layout_score_ScoreView);
            String points = "" + score.getScore();
            scoreView.setText(points);

            // Timestamp
            TextView timestampView = (TextView) view.findViewById(R.id.layout_score_TimestampView);
            String date = getDate(score.getTimestamp());
            timestampView.setText(date);

            return view;
        }

        /**
         * Formates a timestamp to a readable date
         * @param timestamp The timestamp to format
         * @return Readable date, or unknown.
         */
        private String getDate(long timestamp) {
            try{
                Date netDate = new Date(timestamp);
                return format.format(netDate);
            }catch(Exception ex){
                return "Unknown";
            }
        }
    }

}
