package restart.com.weathertask;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import restart.com.weathertask.config.Config;
import restart.com.weathertask.util.SPUtils;

/**
 * Created by Administrator on 2018/4/10.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtils.init(this, "sp_res.pref");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        Date date = new Date();
       if (date.getDay() != (int)SPUtils.getInstance().get(Config.DAY, -1)) {
            int version = (int) SPUtils.getInstance().get(Config.DB_VERSION,1);
            SPUtils.getInstance().put(Config.DB_VERSION,++version);
       }
    }
}
