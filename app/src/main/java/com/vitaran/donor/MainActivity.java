package com.vitaran.donor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity /*implements OpenProfile*/ {

    private SharedPreferences pref;// = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);

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
            ft.replace(R.id.fragment_frame,fragment);
            ft.commit();
        }
    }

    /*@Override
    public void goToProfile() {
        //Intent intent = new Intent(this, ProfileActivity.class);
        //startActivity(intent);
        //finish();
    }*/

    /*@Override
    public void sharedPrefDataTransfer() {

    }*/

    @Override
    protected void onResumeFragments() {

    }

}
