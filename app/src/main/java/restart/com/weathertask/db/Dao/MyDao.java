package restart.com.weathertask.db.Dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restart.com.weathertask.bean.Data;
import restart.com.weathertask.bean.Day;
import restart.com.weathertask.bean.Weather;
import restart.com.weathertask.db.DatabaseHelper;

/**
 * Created by Administrator on 2018/4/13.
 */

public class MyDao {
    private Dao<Weather, Integer> dao;
    private Dao<Day, Integer> dao_day;
    private Dao<Data, Integer> dao_data;


    private DatabaseHelper databaseHelper;

    public MyDao(Context context) {
        try {
            databaseHelper = DatabaseHelper.getInstance(context);
            dao = databaseHelper.getDao(Weather.class);
            dao_day = databaseHelper.getDao(Day.class);
            dao_data = databaseHelper.getDao(Data.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入数据
     *
     * @param weather
     */
    public int insert(Weather weather) {
        try {
            int rsp = dao.create(weather);
            Log.d("", "insert:  插入成功---id为:" + weather.get_id());
            return rsp;
        } catch (SQLException e) {
            Log.d("", "insert:  插入失败---id为:" + weather.get_id());
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * 插入数据
     *
     * @param day
     */
    public int insert_day(Day day) {
        try {
            int rsp = dao_day.create(day);
            return rsp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }
    public int insert_data(Data data) {
        try {
            int rsp = dao_data.create(data);
            return rsp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    /*
       查询单个
    */
    public Weather query(String city) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("city", city);
            List<Weather> weather = dao.queryForFieldValues(map);
            if (weather.size() > 0) {
                return weather.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
      查询多个
   */
    public List<Day> query_day(int data) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("data_id", data);
            List<Day> days = dao_day.queryForFieldValues(map);
            if (days.size() > 0) {
                return days;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
      查询单个
   */
    public Data query_data(int _id) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("weather_id", _id);
            List<Data> weather = dao_data.queryForFieldValues(map);
            if (weather.size() > 0) {
                return weather.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
