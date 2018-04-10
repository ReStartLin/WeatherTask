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

import okhttp3.Call;
import restart.com.weathertask.R;
import restart.com.weathertask.bean.Weather;
import restart.com.weathertask.biz.WeatherBiz;

public class MainActivity extends BaseActivity {
    private EditText et_city;
    private Button query;
    private WeatherBiz myBiz = new WeatherBiz();
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initEven();
    }

    private void initEven() {
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 查询

                String city = et_city.getText().toString();
                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(MainActivity.this, "城市不能为空", Toast.LENGTH_SHORT).show();
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
                        weather =  gson.fromJson(response, Weather.class);
                        Log.d("", "onResponse: "+response);
                        WeatherActivity.launch(MainActivity.this,weather);

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
