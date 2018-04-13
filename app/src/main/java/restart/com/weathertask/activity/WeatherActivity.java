package restart.com.weathertask.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import restart.com.weathertask.R;
import restart.com.weathertask.adapter.WeatherAdapter;
import restart.com.weathertask.bean.Weather;

public class WeatherActivity extends AppCompatActivity {
    public static final String WEATHER = "weather";

    private TextView tv_city;
    private TextView tv_notice;
    private TextView tv_wendu;
    private ListView lv_content;
    private LinearLayout bg;


    private Weather weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        this.weather = (Weather) intent.getSerializableExtra(WEATHER);
        if (weather == null) {
            Toast.makeText(this, "数据传输异常", Toast.LENGTH_SHORT).show();
            return;
        }
        initView();
        bindData();


    }

    private void bindData() {
        tv_city.setText(weather.getCity());
        tv_notice.setText(weather.getData().getGanmao());
        tv_wendu.setText(weather.getData().getWendu()+".0℃");

        lv_content.setAdapter(new WeatherAdapter(weather.getData().getForecast(),this));
        if (weather.getData().getForecast().get(0).getType().equals("小雨")) {
            bg.setBackgroundResource(R.drawable.rain_background);
        } else {
            bg.setBackgroundResource(R.drawable.weather);
        }
    }

    private void initView() {
        tv_city = findViewById(R.id.id_tv_city);
        tv_notice = findViewById(R.id.id_tv_notice);
        tv_wendu = findViewById(R.id.id_tv_wendu);
        lv_content = findViewById(R.id.id_lv_content);
        bg = findViewById(R.id.id_ll_bg);
    }

    public static void launch(Context context,Weather weather) {
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra(WEATHER, weather);
        context.startActivity(intent);
    }
}
