package com.example.johnny.localhackday;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ShowLocation extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    private GoogleApiClient mGoogleApiClient;
    String mLastUpdateTime;
    Location mLastLocation;

    LocationRequest mLocationRequest;
    TextView latitude;
    TextView longitude;
    TextView time;
    Button getUpdate;
    boolean mRequestingLocationUpdates;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);

        latitude = (TextView) findViewById(R.id.Latitude);
        longitude = (TextView) findViewById(R.id.Longitude);
        time = (TextView) findViewById(R.id.time);

        mRequestingLocationUpdates = false;
        updateValuesFromBundle(savedInstanceState);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            createLocationRequest();

        }

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i("location-updates-sample", "Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mLastLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
            updateUI();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (mLastLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            //updateUI();
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void GetLocation(View view) {
        /*
        latitude = (TextView) findViewById(R.id.Latitude);
        longitude = (TextView) findViewById(R.id.Longitude);
        time = (TextView) findViewById(R.id.time);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            updateUI();
        }
        createLocationRequest();*/
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            //setButtonsEnabledState();
            startLocationUpdates();
            game = new Game(this, mLastLocation.getLongitude(), mLastLocation.getLatitude() );
            TextView overview = (TextView) findViewById(R.id.overview);
            overview.setText(game.toString());
        }
        /*if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            setButtonsEnabledState();
            stopLocationUpdates();
        }*/
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();

        if (game.isRunning()) {
            TextView overview = (TextView) findViewById(R.id.overview);
            overview.setText(game.gameLoop(mLastLocation.getLongitude(), mLastLocation.getLatitude()));

        }

    }
    private void updateUI() {

        Log.d("Latitude: ",String.valueOf(latitude==null));
        Log.d("Longitude: ",String.valueOf(longitude==null));
        Log.d("time: ",String.valueOf(time==null));


        if (mLastLocation != null) {
            latitude.setText(String.valueOf(mLastLocation.getLatitude()));
            longitude.setText(String.valueOf(mLastLocation.getLongitude()));
            time.setText(mLastUpdateTime);
        }
    }

    private class Game {

        private double ghostx;
        private double ghosty;
        private double ghostSpeed;
        private double prevGhostMovy;
        private double prevGhostMovx;

        private double initialPlayerx;
        private double initialPlayery;

        private double playerx;
        private double playery;

        private double prevplayerx;
        private double prevplayery;

        private boolean gameRunning;
        private boolean playerMoving;

        // declare the variables needed
        private String requestReply;
        private AlertDialog alertDialog;

        private ArrayList<Flag> flags;

        private int score;
        Random r;

        private int stopMotionCountdown;
        private int stopMotionMax;
        private int time;

        private Context context;

        public class Flag {

            private int count;
            private int value;
            private double xPos;
            private double yPos;

            public Flag () {

                count = 30;
                value = 100;

                xPos=100*r.nextDouble();
                yPos=100*r.nextDouble();

            }

            public void countDown() {
                count--;
            }

            public double getX() {
                return xPos;
            }

            public double getY() {
                return yPos;
            }

            public int getValue() {
                return value;
            }

            public int getCount() {
                return count;
            }

        }

        /*
         * Description: The asyncTask class constructor. Assigns the values used in its other methods.
         * @param context the application context, needed to create the dialog
         * @param parameterValue the pin number to toggle
         * @param ipAddress the ip address to send the request to
         * @param portNumber the port number of the ip address
         * @param parameter
         */
        public Game(Context context, double inputX, double inputY)
        {
            this.context = context;

            alertDialog = new AlertDialog.Builder(this.context)
                    .setTitle("Game Over")
                    .setCancelable(true)
                    .create();

            r = new Random();

            ghostx = 0;
            ghosty = 0;
            prevGhostMovx = 0;
            prevGhostMovy = 0;
            ghostSpeed = 1;

            stopMotionMax = 60;
            stopMotionCountdown = stopMotionMax;

            initialPlayerx = inputX;
            initialPlayery = inputY;

            playerx = 100;
            playery = 100;

            prevplayerx = playerx;
            prevplayery = playery;


            flags = new ArrayList<Flag>();

            for (int i = 0; i <= 6; i++){
                flags.add(new Flag());
            }

            playerMoving = false;
            score = 0;
            time = 0;

            gameRunning = true;
        }


        protected String gameLoop(double currentGPSX, double currentGPSY){


            playerx = (currentGPSX - initialPlayerx) + 100;
            playery = (currentGPSY - initialPlayery) + 100;


            if (prevplayerx == playerx && prevplayery == playery) {
                playerMoving = false;
                stopMotionCountdown--;
            } else if (playerMoving == false) {
                playerMoving = true;
                stopMotionMax--;
                stopMotionCountdown = stopMotionMax;
            }

            if (stopMotionCountdown < 0) {
                gameRunning = false;
            }

            for (int i = 0; i <= 6; i++) {

                flags.get(i).countDown();

                if(Math.sqrt(Math.pow(flags.get(i).getX()-playerx,2) + Math.pow(flags.get(i).getY()-playery,2)) < 5){

                    score += flags.get(i).getValue();
                    flags.add(i,new Flag());

                    continue;
                }

                if(flags.get(i).getCount() <= 0) {
                    flags.add(i,new Flag());
                }
            }

            if (ghostx < 0) {
                ghostx = 0;
            }
            if (ghostx > 200) {
                ghostx = 200;
            }
            if (ghosty < 0) {
                ghosty = 0;
            }
            if (ghosty > 200) {
                ghosty = 200;
            }

            if(Math.sqrt(Math.pow(ghostx-playerx,2) + Math.pow(ghosty-playery,2)) < ghostSpeed) {
                gameRunning = false;
            }

            if (playerMoving == true) {
                double angle = Math.atan((playerx-ghostx)/(playery-ghosty));
                angle = (angle-45) + 90*r.nextDouble();

                prevGhostMovy = ghostSpeed * Math.sin(angle);
                prevGhostMovx = ghostSpeed * Math.cos(angle);
            }

            ghosty = ghosty+prevGhostMovy;
            ghostx = ghostx+prevGhostMovx;

            ghostSpeed = 1+ Math.log10(1+time++);

            if (playerx < 0 || playerx > 200 || playery < 0 || playery > 200) {
                gameRunning = false;
                alertDialog.setMessage("Game Ending");
                if(!alertDialog.isShowing()) {
                    alertDialog.show();
                }
            }

            prevplayerx = playerx;
            prevplayery = playery;

            return this.toString();
        }

        public boolean isRunning(){

            return gameRunning;
        }

        public AlertDialog getAlertDialog() {
            return alertDialog;
        }

        public String toString() {

            String overview = "Player: X: " +playerx+ " Y: " +playery +"\n" ;
            overview += "Ghost: X: " +ghostx+ " Y: " +ghosty +"\n" ;

            for (int i = 0; i <= 6; i++){
                overview += "Flag"+i+": X: " + flags.get(i).getX()+ " Y: " + flags.get(i).getY() +"\n";
            }

            return overview;

        }

    }

}
