package fr.ups.overdrill.info;

import android.os.Bundle;
import android.widget.TextView;

import fr.ups.interactions.model.Interaction;
import fr.ups.overdrill.R;

/**
 * Gives an explanation about a certain interaction
 */
public class InteractionInfoActivity extends ParentActivity {

    private Interaction interaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction_info);

        // Interaction
        this.interaction = (Interaction) getIntent().getSerializableExtra("interaction");
        setTitle(interaction.toString());

        // TextView
        TextView textView = (TextView) findViewById(R.id.activity_interaction_info_TextView);
        textView.setText(interaction.toString());

    }
}
