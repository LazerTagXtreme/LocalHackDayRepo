package com.example.johnny.localhackday;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.purchase.InAppPurchase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class ShowLocation extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    double mLatitudeText;
    double mLongitudeText;
    private ImageView ghost_icon, item_icon[];

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .build();

        }
        this.item_icon = new ImageView[6];

        this.ghost_icon = (ImageView) findViewById(R.id.ghost);
        this.item_icon[0] = (ImageView) findViewById(R.id.item1);
        this.item_icon[1] = (ImageView) findViewById(R.id.item2);
        this.item_icon[2] = (ImageView) findViewById(R.id.item3);
        this.item_icon[3] = (ImageView) findViewById(R.id.item4);
        this.item_icon[4] = (ImageView) findViewById(R.id.item5);
        this.item_icon[5] = (ImageView) findViewById(R.id.item6);
    }

    public void updateRadar(float[][] loc){
        
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void GetLocation(View view){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        TextView latitude = (TextView) findViewById(R.id.Latitude);
//        TextView longitude = (TextView) findViewById(R.id.Longitude);
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastLocation != null) {
//            latitude.setText(String.valueOf(mLastLocation.getLatitude()));
//            longitude.setText(String.valueOf(mLastLocation.getLongitude()));
//        }
    }

}
