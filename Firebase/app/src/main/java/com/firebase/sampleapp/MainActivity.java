//        Copyright 2019 Google LLC
//
//        Licensed under the Apache License, Version 2.0 (the "License");
//        you may not use this file except in compliance with the License.
//        You may obtain a copy of the License at
//
//        https://www.apache.org/licenses/LICENSE-2.0
//
//        Unless required by applicable law or agreed to in writing, software
//        distributed under the License is distributed on an "AS IS" BASIS,
//        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//        See the License for the specific language governing permissions and
//        limitations under the License.

package com.firebase.sampleapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity; //
import android.util.Log;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

// Declare for Google Analytics for Firebase
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout crashTab;
    private RelativeLayout eventTab;
    private EditText search;
    private Button apple;
    private Button pear;
    private Button orange;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_crashes:
                    crashTab.setVisibility(View.VISIBLE);
                    crashTab.setClickable(true);
                    eventTab.setVisibility(View.GONE);
                    eventTab.setClickable(false);
                    return true;
                case R.id.navigation_events:
                    crashTab.setVisibility(View.GONE);
                    crashTab.setClickable(false);
                    eventTab.setVisibility(View.VISIBLE);
                    eventTab.setClickable(true);
                    return true;
            }
            return false;
        }
    };

    // [START declare_analytics]
    private FirebaseAnalytics mFirebaseAnalytics;
    // [END declare_analytics]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        // [END FirebaseAnalytics instance]

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);

        initializeUI();
    }

    public void initializeUI() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        crashTab = findViewById(R.id.crash_layout);
        eventTab = findViewById(R.id.event_layout);

        search = findViewById(R.id.search);
        apple = findViewById(R.id.add_apple);
        pear = findViewById(R.id.add_pear);
        orange = findViewById(R.id.add_orange);
    }

    /* Start Custom Keys, Custom Logs Set User Id */

    public void setUserIDs(View view) {
        // [START crash_set_User_ID_Email_Name]
        Crashlytics.setUserIdentifier("12345" /* crash set user id */);
        Crashlytics.setUserEmail("user@firebase.google.com" /* crash set user email */);
        Crashlytics.setUserName("Firebase Test User" /* crash set user name */);
        // [END crash_set_User_ID_Email_Name]
    }

    public void setCustomLogs(View view) {

        // Write to the Crashlytics crash report only
        // Crashlytics.log(msg);
        Crashlytics.log("Higgs-Boson detected! Bailing out...");
        // [END crash_log_report_only]
    }
    public void setCustomKeys(View view) {
        // [START crash_set_keys_basic]
        Crashlytics.setBool("condition_status", true /* boolean value */);
        Crashlytics.setDouble("total_money", 123.0 /* double value */);
        Crashlytics.setFloat("experience_points", (float) 7.5 /* float value */);
        Crashlytics.setInt("current_level", 4 /* int value */);
        Crashlytics.setString("next_powerup", "foo" /* string value */);
        // [END crash_set_keys_basic]
    }

    /* End Custom Keys, Custom Logs,  Set User Id */

    /* Start  Crash and Long Non-Fatal */

    public void crash(View view) {
        throw new RuntimeException("This is a Crash");
    }

    public void sendNonFatal(View view) {
        try {
            throw new NullPointerException("It is Pointer No-Fatal Error");
        } catch (Exception e) {
            Crashlytics.logException(e);
            // handle your exception here!

        }
        // [END crash_log_caught_ex]
    }

    /* Start Custom Events, Search, Add to cart, Purchase*/

    /* Start  Search Event ( string ) */

    public void setSearch() {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.SEARCH_TERM, "Apple");
        params.putString(FirebaseAnalytics.Param.SEARCH_TERM, "Pear");
        params.putString(FirebaseAnalytics.Param.SEARCH_TERM, "Orange");
    }
     // [END custom_event]


    //Firebase Google Analytics for Add to cart logEvent Apple , Pear, Orange

    public void setApple (){
        Bundle params = new Bundle();

        params.putDouble(FirebaseAnalytics.Param.VALUE, 1.99);
        params.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Fruit");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME,"Apple");
        params.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU-100");

    }

    public void setPear (){
        Bundle params = new Bundle();

        params.putDouble(FirebaseAnalytics.Param.VALUE, 2.99);
        params.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Fruit");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME,"Pear");
        params.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU-200");

    }

    public void setOrange (){
        Bundle params = new Bundle();

        params.putDouble(FirebaseAnalytics.Param.VALUE, 3.99);
        params.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Fruit");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME,"Orange");
        params.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU-300");

    }

    public void setPurchase (){
        Bundle params = new Bundle();

        params.putDouble(FirebaseAnalytics.Param.VALUE, 10.00);
        params.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Fruit Basket");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME,"Fruts");
        params.putString(FirebaseAnalytics.Param.ITEM_ID, "SKU-400");

    }

}
