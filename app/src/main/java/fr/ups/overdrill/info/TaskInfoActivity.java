package fr.ups.overdrill.info;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ups.overdrill.R;
import fr.ups.overdrill.game.Task;

/**
 * Gives an explanation about a certain task
 */
public class TaskInfoActivity extends ParentActivity {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        // Task
        this.task = (Task) getIntent().getSerializableExtra("task");
        setTitle(task.getTextID());

        // TextView
        TextView textView = (TextView) findViewById(R.id.activity_task_info_TextView);
        textView.setText(task.getExplanationID());

        // IconView
        ImageView iconView = (ImageView) findViewById(R.id.activity_task_info_IconView);
        iconView.setImageResource(task.getIconID());
    }

}
