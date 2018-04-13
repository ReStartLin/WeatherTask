package restart.com.weathertask.biz;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import restart.com.weathertask.config.Config;

/**
 * Created by Administrator on 2018/4/10.
 */

public class WeatherBiz {

    public void getWeather(String city,StringCallback callback) {
        try {
           // city = "上海";//测试用
            String url = URLEncoder.encode(city, "UTF-8");

            OkHttpUtils
                    .get()
                    .url(Config.BASE_URL+url)
                    .tag(this)
                    .build()
                    .execute(callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onDestory() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
