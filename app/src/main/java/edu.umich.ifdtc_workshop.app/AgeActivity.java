package edu.umich.ifdtc_workshop.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


public class AgeActivity extends ActionBarActivity {

    public static final String AGE_EXTRA = "ageExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        setupViews();
    }

    private void setupViews() {
        Button doneButton = (Button) findViewById(R.id.ageDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgeActivity.this.handleDoneButtonClick((Button) view);
            }
        });
    }

    private void handleDoneButtonClick(Button theButton) {
        Spinner ageSpinner = (Spinner) findViewById(R.id.ageSpinner);
        String theValue = ageSpinner.getSelectedItem().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(AGE_EXTRA, theValue); //a name/value returned to caller
        setResult(RESULT_OK, resultIntent);
        finish();//Need to finish() to send data back to calling activity
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.age, menu);
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
