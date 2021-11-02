package com.grupo15.recuperarte.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.grupo15.recuperarte.R;

import java.util.Random;

public class MtPerDayStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mt_per_day_stats);
        TableLayout table = findViewById(R.id.table_mt_perday);
        for (int i=0; i < 10; i++) {
            TableRow row = new TableRow(this);
            Random random = new Random();
            int value = random.nextInt(100) + 1;
            TextView tv = new TextView(this);
            tv.setText(String.valueOf(value));
            row.addView(tv);
            table.addView(row);
        }
    }
}