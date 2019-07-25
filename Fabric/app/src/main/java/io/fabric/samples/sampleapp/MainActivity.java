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


package io.fabric.samples.sampleapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.AddToCartEvent;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.PurchaseEvent;
import com.crashlytics.android.answers.SearchEvent;



import java.math.BigDecimal;
import java.util.Currency;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void purchaseEvent(View view) {
        // Do something in response to button click
        Answers.getInstance().logPurchase(new PurchaseEvent()
                .putItemPrice(BigDecimal.valueOf(13.50))
                .putCurrency(Currency.getInstance("USD"))
                .putItemName("Answers Shirt")
                .putItemType("Apparel")
                .putItemId("sku-350")
                .putSuccess(true));
    }

    public void cartEvent(View view) {
        switch (view.getId()) {
            case R.id.add_apple:
                sendCartEvent(apple.getText().toString());
                break;
            case R.id.add_orange:
                sendCartEvent(orange.getText().toString());
                break;
            case R.id.add_pear:
                sendCartEvent(pear.getText().toString());
                break;
        }
    }

    private void sendCartEvent(String cartItem) {
        Answers.getInstance().logAddToCart(new AddToCartEvent()
                .putItemPrice(BigDecimal.valueOf(10.00))
                .putCurrency(Currency.getInstance("USD"))
                .putItemName(cartItem)
                .putItemType("Fruit Basket")
                .putItemId("sku-400"));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void searchEvent(View view) {
        // Do something in response to button click
        Answers.getInstance().logSearch(new SearchEvent()
                .putQuery(search.getText().toString()));
    }


    /* CCrashes, Non-Fatals, and Customized Crash Reports */

    public void setUserIDs(View view) {
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }

    public void setCustomLogs(View view) {
        // Write to Crashlytics and the Logcat using:
        // Crashlytics.log(int priority, String tag, String msg);
        Crashlytics.log(1, "yourMethod():", "logging info here...");

        // Or write only to the Crashlytics crash report
        // Crashlytics.log(msg);
        Crashlytics.log("Higgs-Boson detected! Bailing out...");
    }

    public void setCustomKeys(View view) {
        Crashlytics.setBool("condition_status", true);
        Crashlytics.setDouble("total_money", 123);
        Crashlytics.setFloat("experience_points", (float) 7.5);
        Crashlytics.setInt("current_level", 4);
        Crashlytics.setString("next_powerup", "extra life");
    }

    public void crash(View view) {
        throw new RuntimeException("This is a crash");
    }

    public void sendNonFatal(View view) {
        try {
            throw new NullPointerException("Network error");
        } catch (Exception e) {
            Crashlytics.logException(e);
            // handle your exception here!

        }
    }

}
