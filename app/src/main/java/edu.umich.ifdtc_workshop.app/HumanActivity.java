package edu.umich.ifdtc_workshop.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;


public class HumanActivity extends ActionBarActivity {
    public static final String HUMAN_EXTRA = "humanExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human);

        setupViews();
    }

    private void setupViews() {
        Button doneButton = (Button) findViewById(R.id.humanDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HumanActivity.this.handleDoneButtonClick((Button) view);
            }
        });
    }

    private void handleDoneButtonClick(Button view) {
        Switch humanSwitch = (Switch) findViewById(R.id.humanSwitch);
        boolean theValue = humanSwitch.isChecked();
        Intent intent = new Intent();
        intent.putExtra(HUMAN_EXTRA, String.valueOf(theValue));
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.human, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
