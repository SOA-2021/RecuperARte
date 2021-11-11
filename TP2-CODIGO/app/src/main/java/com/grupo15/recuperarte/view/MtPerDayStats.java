package com.grupo15.recuperarte.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.mvp.IMtPerDay;
import com.grupo15.recuperarte.persistence.RunDataService;
import com.grupo15.recuperarte.presenter.MtPerDayPresenter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MtPerDayStats extends AppCompatActivity implements IMtPerDay.View {
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mt_per_day_stats);

        this.table = findViewById(R.id.table_mt_perday);
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

        IMtPerDay.Presenter presenter = new MtPerDayPresenter(this);

        RunDataService rdService = new RunDataService(getBaseContext());
        presenter.getMetersPerDay(rdService);
    }

    @Override
    public void drawTable(List<RunDataService.MtPerDay> mtPerDay) {
        for ( RunDataService.MtPerDay mt: mtPerDay ) {
            /* Columna de metros */
            TableRow tbrow = new TableRow(this);
            tbrow.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            TextView t1v = new TextView(this);
            t1v.setText(String.format("%.2f mt       ", mt.getMeters()));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.START);
            tbrow.addView(t1v);

            /* Columna de fecha */
            TextView t2v = new TextView(this);
            t2v.setText(mt.getDate().format(DateTimeFormatter.ISO_DATE));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.END);
            tbrow.addView(t2v);
            this.table.addView(tbrow);
        }
    }
}