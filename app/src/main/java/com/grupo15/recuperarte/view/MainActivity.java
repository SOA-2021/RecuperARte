package com.grupo15.recuperarte.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.background.MainBackground;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.persistence.RunDataService;

public class MainActivity extends AppCompatActivity {
    private MainBackground background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        final IApiClient api = Conf.getInstance().apiClient();
        final RunDataDAO dao = new RunDataDAO(getBaseContext());
        this.background = new MainBackground(sm, api, dao);
        new Thread(this.background).start();

        setContentView(R.layout.activity_main);

        final RunDataService service = new RunDataService(getBaseContext());
        runOnUiThread(() -> {
            RunDataService.MtPerDay meters = service.getMtToday();
            TextView t = findViewById(R.id.mt_text_view);
            t.setText(String.format("%.2f mt", meters.getMeters()));
        });

        Button mtPerDayButton = findViewById(R.id.mt_perday_button);
        mtPerDayButton.setOnClickListener((b) -> {
            Intent intent = new Intent(this, MtPerDayStats.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.background.terminate();
    }
}