package com.grupo15.recuperarte.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.persistence.RunData;
import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.persistence.RunDataDbHelper;
import com.grupo15.recuperarte.persistence.RunDataService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MtPerDayStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mt_per_day_stats);
        TableLayout table = findViewById(R.id.table_mt_perday);

        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Metros ");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText(" Dia ");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        table.addView(tbrow0);

        RunDataService rdService = new RunDataService(getBaseContext());
        for(int i = 0;i< rdService.listMtPerDay().size();i++){
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(String.valueOf(rdService.listMtPerDay().get(i).getMeters()));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(String.valueOf(rdService.listMtPerDay().get(i).getDate()));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            table.addView(tbrow);
        }
    }
}