package edu.umich.isr.InteractiveUI.ifdtc_workshop.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    static final int SHOW_AGE_REQUEST_CODE = 1000;
    static final int SHOW_HUMAN_REQUEST_CODE = 1001;
    static final int SHOW_OPEN_END_REQUEST_CODE = 1002;
    static final int SHOW_LOCATION_REQUEST_CODE = 1003;
    static final int TAKE_PICTURE_REQUEST_CODE = 1004;
    static final String ageState = "ageState";
    static final String humanState = "humanState";
    static final String openEndState = "openEndState";
    static final String locationState = "locationState";

    String age;
    String human;
    String openEnd;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        if(savedInstanceState != null)
            restoreState(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void restoreState(Bundle savedInstanceState) {
        age = savedInstanceState.getString(ageState, null);
        human = savedInstanceState.getString(humanState, null);
        openEnd = savedInstanceState.getString(openEndState, null);
        location = savedInstanceState.getString(locationState, null);
        updateTextDataSummary();
    }

    private void setupViews() {
        Button enterAgeButton = (Button) findViewById(R.id.enterAgeButton );
        Button enterHumanButton = (Button) findViewById(R.id.enterHumanButton );
        Button enterOpenEndButton = (Button) findViewById(R.id.enterOpenEndButton );
        Button enterLocationButton = (Button) findViewById(R.id.enterLocationButton );
        Button takePictureButton = (Button) findViewById(R.id.takePictureButton );
        Button sendButton = (Button) findViewById(R.id.sendButton);

        enterAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.handleEnterAgeButtonClick((Button) view);
            }
        });

        enterHumanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.handleEnterHumanButtonClick((Button) view);
            }
        });

        enterOpenEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.handleEnterOpenEndButtonClick((Button) view);
            }
        });

        enterLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.handleEnterLocationButtonClick((Button) view);
            }
        });

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.handleTakePictureButtonClick((Button) view);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.handleSendButtonClick((Button) view);
            }
        });
    }

    private void handleEnterOpenEndButtonClick(Button view) {
        Intent intent = new Intent(this,OpenEndActivity.class);
        startActivityForResult(intent,SHOW_OPEN_END_REQUEST_CODE);
    }

    private void handleEnterHumanButtonClick(Button view) {
        Intent intent = new Intent(this,HumanActivity.class);
        startActivityForResult(intent,SHOW_HUMAN_REQUEST_CODE);
    }

    private void handleEnterLocationButtonClick(Button view) {
        Intent intent = new Intent(this, LocationActivity.class);//the type info of hat we want to display
        startActivityForResult(intent,SHOW_LOCATION_REQUEST_CODE);
    }

    private void handleTakePictureButtonClick(Button theButton) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
    }

    private void handleEnterAgeButtonClick(Button theButton) {
        Intent intent = new Intent(this, AgeActivity.class);//the type info of hat we want to display
        startActivityForResult(intent,SHOW_AGE_REQUEST_CODE);
    }

    private void handleSendButtonClick(Button theButton) {
        try{
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //post it
            } else {
                Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            }

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://ifdtc.azurewebsites.net/api/MobileData");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("Age", age));
            pairs.add(new BasicNameValuePair("Human", human));
            pairs.add(new BasicNameValuePair("OpenEnd", openEnd));
            pairs.add(new BasicNameValuePair("Location", location));
            post.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(post);

            Toast.makeText(this, "Sent", Toast.LENGTH_LONG).show();
        }
        catch(ClientProtocolException ex){
            Toast.makeText(this, "ClientProtocolException", Toast.LENGTH_LONG).show();
        }
        catch(UnsupportedEncodingException ex) {
            Toast.makeText(this, "UnsupportedEncodingException", Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){
            Toast.makeText(this, "Error sending data ", Toast.LENGTH_LONG).show();
        }
    }

    @Override//To add this, goto Code menu and type onActivityResult
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        switch (requestCode) {
            case SHOW_AGE_REQUEST_CODE:
                handleAgeResult(resultCode, resultIntent);
                break;
            case SHOW_HUMAN_REQUEST_CODE:
                handleHumanResult(resultCode, resultIntent);
                break;
            case SHOW_OPEN_END_REQUEST_CODE:
                handleOpenEndResult(resultCode, resultIntent);
                break;
            case SHOW_LOCATION_REQUEST_CODE:
                handleLocationResult(resultCode, resultIntent);
                break;
            case TAKE_PICTURE_REQUEST_CODE:
                handleTakePictureResult(resultCode, resultIntent);
                break;
        }
    }

    private void handleOpenEndResult(int resultCode, Intent resultIntent) {
        if(resultCode == RESULT_OK)
        {
            openEnd = resultIntent.getStringExtra(OpenEndActivity.OPEN_END_EXTRA);
            updateTextDataSummary();
        }
    }

    private void handleHumanResult(int resultCode, Intent resultIntent) {
        if(resultCode == RESULT_OK)
        {
            human = resultIntent.getStringExtra(HumanActivity.HUMAN_EXTRA);
            updateTextDataSummary();
        }
    }

    private void handleAgeResult(int resultCode, Intent resultIntent) {
        if(resultCode == RESULT_OK)
        {
            age = resultIntent.getStringExtra(AgeActivity.AGE_EXTRA);
            updateTextDataSummary();
        }
    }

    private void handleLocationResult(int resultCode, Intent resultIntent) {
        if(resultCode == RESULT_OK)
        {
            location = resultIntent.getStringExtra(LocationActivity.LOCATION_EXTRA);
            updateTextDataSummary();
        }
    }

    private void handleTakePictureResult(int resultCode, Intent resultIntent) {
        if(resultCode == RESULT_OK)
        {
            Toast.makeText(this, "Photo taken and stored", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "User Canceled", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_close:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ageState, age);
        outState.putString(humanState, human);
        outState.putString(openEndState, openEnd);
        outState.putString(locationState, location);
    }

    private void updateTextDataSummary(){
        TextView textDataSummary =(TextView) findViewById(R.id.textDataSummary);
        String text = "";
        if(age != null)
            text += "Age: " + age + "\n";
        if(human != null)
            text += "Human: " + human + "\n";
        if(openEnd != null)
            text += "OpenEnd: " + openEnd + "\n";
        if(location != null)
            text += "Location: " + location + "\n";

        textDataSummary.setText(text);

    }
}
