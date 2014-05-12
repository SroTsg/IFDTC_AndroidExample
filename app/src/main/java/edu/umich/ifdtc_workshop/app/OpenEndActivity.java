package edu.umich.ifdtc_workshop.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class OpenEndActivity extends ActionBarActivity {
    public static final String OPEN_END_EXTRA = "openEndExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_end);

        setupViews();
    }

    private void setupViews() {
        Button doneButton = (Button) findViewById(R.id.openEndDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenEndActivity.this.handleOpenEndButtonClick((Button) view);
            }
        });
    }

    private void handleOpenEndButtonClick(Button view) {
        TextView openEndText = (TextView) findViewById(R.id.openEndText);
        String theValue = openEndText.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(OPEN_END_EXTRA, theValue); //a name/value returned to caller
        setResult(RESULT_OK, resultIntent);
        finish();//Need to finish() to send data back to calling activity
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.open_end, menu);
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
