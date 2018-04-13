package restart.com.weathertask.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import restart.com.weathertask.R;
import restart.com.weathertask.bean.Data;
import restart.com.weathertask.bean.Day;
import restart.com.weathertask.bean.Weather;
import restart.com.weathertask.biz.WeatherBiz;
import restart.com.weathertask.db.Dao.MyDao;

public class MainActivity extends BaseActivity {
    private EditText et_city;
    private Button query;
    private WeatherBiz myBiz = new WeatherBiz();
    private Weather weather;
    private MyDao myDao;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDao = new MyDao(this);
        initView();

        initEven();
    }

    private void initEven() {
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = et_city.getText().toString();
                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(MainActivity.this, "城市不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (myDao.query(city) != null) {
                    weather = myDao.query(city);
                    Data data = myDao.query_data(weather.get_id());
                    List<Day> days = myDao.query_day(data.get_id());
                    data.setForecast(days);
                    weather.setData(data);
                    WeatherActivity.launch(MainActivity.this, weather, "缓存");
                    return;
                }

                startLoadingProgress();
                myBiz.getWeather(city, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        stopLoadingProgress();
                        Toast.makeText(MainActivity.this, "该城市的数据不存在或者发生未知错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        stopLoadingProgress();
                        Gson gson = new Gson();
                        weather = gson.fromJson(response, Weather.class);
                        Log.d("", "onResponse: " + response);
                        if (weather.getMessage().equals("Check the parameters.")) {
                            Toast.makeText(MainActivity.this, "请输入中文/合法的城市名称", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (weather.getStatus() == 304) {
                            Toast.makeText(MainActivity.this, "频繁调用!稍后再试", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (weather.getStatus() != 200) {
                            Toast.makeText(MainActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        myDao.insert(weather);


                        //存储data信息
                        Data data = weather.getData();
                        Weather dbWeather = myDao.query(weather.getCity());
                        data.setWeather(dbWeather);
                        myDao.insert_data(data);

                        Data dbData = myDao.query_data(dbWeather.get_id());
                        //存储五天的天气状况
                        for (int i = 0; i < 5; i++) {
                            Day day = weather.getData().getForecast().get(i);
                            day.setData(dbData);
                            myDao.insert_day(day);
                        }
                        WeatherActivity.launch(MainActivity.this, weather, "网络");

                    }
                });
            }
        });
    }

    private void initView() {
        et_city = findViewById(R.id.id_edt_city);
        query = findViewById(R.id.id_btn_query);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBiz.onDestory();
    }
}
