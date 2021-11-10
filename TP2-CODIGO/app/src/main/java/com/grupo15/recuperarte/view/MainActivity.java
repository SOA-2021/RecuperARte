package com.grupo15.recuperarte.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.grupo15.recuperarte.R;
import com.grupo15.recuperarte.api_catedra.api.IApiClient;
import com.grupo15.recuperarte.background.MainBackground;
import com.grupo15.recuperarte.global.Conf;
import com.grupo15.recuperarte.mvp.IMain;
import com.grupo15.recuperarte.persistence.RunDataDAO;
import com.grupo15.recuperarte.persistence.RunDataService;
import com.grupo15.recuperarte.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMain.View {
    private IMain.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        this.presenter = new MainPresenter(this);

        /* Comienzo la tarea de fondo */
        final SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        final RunDataDAO dao = new RunDataDAO(getBaseContext());
        this.presenter.runBackground(sm, dao);


        final RunDataService service = new RunDataService(getBaseContext());
        runOnUiThread(() -> {
            RunDataService.MtPerDay meters = service.getMtToday();
            TextView t = findViewById(R.id.mt_text_view);
            t.setText(String.format("%.2f mt", meters.getMeters()));
        });

        /* Boton de estadisticas de metros por dia */
        Button mtPerDayButton = findViewById(R.id.mt_perday_button);
        mtPerDayButton.setOnClickListener((b) -> {
            Intent intent = new Intent(this, MtPerDayStats.class);
            startActivity(intent);
        });

        /* Boton de estadisticas de tiempo corrido */
        Button mtTimeRunningButton = findViewById(R.id.mt_time_running_button);
        mtTimeRunningButton.setOnClickListener((b) -> {
            Intent intent = new Intent(this, TimeRunningStats.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.terminateBackground();
    }
}