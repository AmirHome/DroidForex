package com.example.droidforex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
                            textViewCurrency = findViewById(R.id.textView1);
                            textViewCurrency.setText(ds.getKey());
                            textViewCost = findViewById(R.id.textView2);
                            textViewCost.setText(ds.child("cost").getValue().toString());
                            imageView = findViewById(R.id.imageView1);
                            imageView.setImageResource(R.mipmap.fl_ic_eur_tr);
                            break;
                        case "USD_EUR":
                            textViewCurrency = findViewById(R.id.textView3);
                            textViewCurrency.setText(ds.getKey());
                            textViewCost= findViewById(R.id.textView4);
                            textViewCost.setText(ds.child("cost").getValue().toString());
                            imageView = findViewById(R.id.imageView2);
                            imageView.setImageResource(R.mipmap.fl_ic_us_eur);
                            break;
                        case "USD_TRY":
                            textViewCurrency = findViewById(R.id.textView5);
                            textViewCurrency.setText(ds.getKey());
                            textViewCost = findViewById(R.id.textView6);
                            textViewCost.setText(ds.child("cost").getValue().toString());
                            imageView = findViewById(R.id.imageView3);
                            imageView.setImageResource(R.mipmap.fl_ic_us_tr);
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

    }

}
