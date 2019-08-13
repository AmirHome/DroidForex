package com.example.droidforex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progress.progressview.ProgressView;

import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AmirHome";

    String forecastCurrency = "EUR_TRY";
    final String[] forecastEurTry = new String[4];
    final String[] forecastUsdTry = new String[4];
    final String[] forecastUsdEur = new String[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int[] colorList = new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};

        ProgressView lProgressView = findViewById(R.id.progressView1);
        lProgressView.applyGradient(colorList);

        ProgressView rProgressView = findViewById(R.id.progressView2);
        rProgressView.applyGradient(colorList);

        lProgressView.setProgress((float) 0.7);
        rProgressView.setProgress(1);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("forex/latest");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    TextView textViewCost;
                    TextView textViewCurrency;
                    ImageView imageView;
                    switch (ds.getKey()) {
                        case "EUR_TRY":
                            setMain(ds, R.id.textView1, R.id.textView2, R.id.imageView1, R.mipmap.fl_ic_eur_tr, forecastEurTry);

                            if ("EUR_TRY" == forecastCurrency){
                                setForecast(R.mipmap.fl_ic_eur_tr, forecastEurTry);
                            }
                            break;
                        case "USD_EUR":
                            setMain(ds, R.id.textView3, R.id.textView4, R.id.imageView2, R.mipmap.fl_ic_us_eur, forecastUsdEur);

                            if ("USD_EUR" == forecastCurrency){
                                setForecast(R.mipmap.fl_ic_us_eur, forecastUsdEur);
                            }
                            break;
                        case "USD_TRY":
                            setMain(ds, R.id.textView5, R.id.textView6, R.id.imageView3, R.mipmap.fl_ic_us_tr, forecastUsdTry);

                            if ("USD_TRY" == forecastCurrency){
                                setForecast(R.mipmap.fl_ic_us_tr, forecastUsdTry);
                            }
                            break;
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);

        imageView1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                forecastCurrency = "EUR_TRY";
                setForecast(R.mipmap.fl_ic_eur_tr, forecastEurTry);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                forecastCurrency = "USD_EUR";
                setForecast(R.mipmap.fl_ic_us_eur, forecastUsdEur);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                forecastCurrency = "USD_TRY";
                setForecast(R.mipmap.fl_ic_us_tr, forecastUsdTry);

            }
        });

    }

    private void setMain(DataSnapshot ds, int p, int p2, int p3, int p4, String[] forecastEurTry) {
        TextView textViewCurrency;
        TextView textViewCost;
        ImageView imageView;
        textViewCurrency = findViewById(p);
        textViewCurrency.setText(ds.getKey());
        textViewCost = findViewById(p2);
        textViewCost.setText(ds.child("cost").getValue().toString());
        imageView = findViewById(p3);
        imageView.setImageResource(p4);

        if (ds.child("next_hour").exists()) {
            forecastEurTry[0] = ds.child("next_hour").child("cost").getValue().toString();
            forecastEurTry[1] = ds.child("next_hour").child("status").getValue().toString();
        }
        if (ds.child("tomorrow").exists()) {
            forecastEurTry[2] = ds.child("tomorrow").child("cost").getValue().toString();
            forecastEurTry[3] = ds.child("tomorrow").child("status").getValue().toString();
        }
    }

    private void setForecast(int p, String[] forecastCurrencyValues) {
        ImageView imageView = findViewById(R.id.imageView4);
        imageView.setImageResource(p);

        TextView textView7 = findViewById(R.id.textView7);
        textView7.setText(forecastCurrency);

        TextView textView10 = findViewById(R.id.textView10);
        textView10.setText(forecastCurrencyValues[0]);
        TextView textView11 = findViewById(R.id.textView11);
        textView11.setText(forecastCurrencyValues[2]);

        ProgressView lProgressView = findViewById(R.id.progressView1);
        lProgressView.setProgress(Float.parseFloat(forecastCurrencyValues[1]));

        ProgressView rProgressView = findViewById(R.id.progressView2);
        rProgressView.setProgress(Float.parseFloat(forecastCurrencyValues[3]));
    }

}
