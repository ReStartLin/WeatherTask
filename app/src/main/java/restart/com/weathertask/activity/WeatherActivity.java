package restart.com.weathertask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import restart.com.weathertask.R;
import restart.com.weathertask.bean.Weather;

public class WeatherActivity extends AppCompatActivity {
    public static final String WEATHER = "weather";
    private TextView test;
    private Weather weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        weather = (Weather) intent.getSerializableExtra(WEATHER);
        if (weather == null) {
            Toast.makeText(this, "数据传输异常", Toast.LENGTH_SHORT).show();
            return;
        }
        test.setText(weather.toString());


    }

    private void initView() {
        test = findViewById(R.id.tv_test);
    }

    public static void launch(Context context,Weather weather) {
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra(WEATHER, weather);
        context.startActivity(intent);
    }
}
