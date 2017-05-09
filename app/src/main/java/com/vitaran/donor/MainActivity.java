package com.vitaran.donor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OpenProfileInterface, GPSInterface {

    private SharedPreferences pref;// = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
    private TrackGPS gps;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        initFragment();
    }

    private void initFragment(){
        Toast.makeText(this,"Main Activity Opened : "+pref.getBoolean(Constants.LOGGEDIN_SHARED_PREF,false),Toast.LENGTH_LONG).show();
        if(pref.getBoolean(Constants.LOGGEDIN_SHARED_PREF,false)){
            Toast.makeText(this,"Opening Profile Activity : "+pref.getBoolean(Constants.LOGGEDIN_SHARED_PREF,false),Toast.LENGTH_LONG).show();
            //goToProfile();
        }else {
            Fragment fragment = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }

    @Override
    public void goToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        //finish();
    }

    @Override
    public void locateDonor() {
        gps = new TrackGPS(MainActivity.this);
        if(gps.canGetLocation()){

            longitude = gps.getLongitude();
            latitude = gps.getLatitude();

            Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();

            TrackGPS.getAddressFromLocation(latitude, longitude, getApplicationContext(), new GeocoderHandler());
        }
        else
        {
            gps.showSettingsAlert();
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Toast.makeText(getApplicationContext(),""+locationAddress,Toast.LENGTH_SHORT).show();
            //.setText(locationAddress);
        }
    }

    @Override
    protected void onResumeFragments() {

    }

}
