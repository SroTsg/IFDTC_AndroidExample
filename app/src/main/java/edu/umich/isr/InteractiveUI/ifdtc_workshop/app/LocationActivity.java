package edu.umich.isr.InteractiveUI.ifdtc_workshop.app;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class LocationActivity extends ActionBarActivity {
    public static final String LOCATION_EXTRA = "locationExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        setUpViews();
    }

    private void setUpViews() {
        TextView locatinTextView = (TextView) findViewById(R.id.locationTextView);

        Button doneButton = (Button) findViewById(R.id.locationDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationActivity.this.handleDoneButtonClick((Button) view);
            }
        });

        try{
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        locatinTextView.setText(lastKnownLocation.getLatitude() + ", " + lastKnownLocation.getLongitude());
        }
        catch(Exception ex){
            locatinTextView.setText("40,-199");
            Toast.makeText(this, "Setting default location ", Toast.LENGTH_LONG).show();
        }

    }

    private void handleDoneButtonClick(Button view) {
        TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
        String theValue = locationTextView.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(LOCATION_EXTRA, theValue);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);
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
