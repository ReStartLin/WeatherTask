package restart.com.weathertask.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Date;

import restart.com.weathertask.bean.Data;
import restart.com.weathertask.bean.Day;
import restart.com.weathertask.bean.Weather;
import restart.com.weathertask.config.Config;
import restart.com.weathertask.util.SPUtils;

/**
 * Created by Administrator on 2018/4/13.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static DatabaseHelper databaseHelper;
    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper =  new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, "weather.db", null, (Integer) SPUtils.getInstance().get(Config.DB_VERSION,1));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Weather.class);
            TableUtils.createTable(connectionSource, Data.class);
            TableUtils.createTable(connectionSource, Day.class);
            SPUtils.getInstance().put(Config.DAY,new Date().getDay());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Weather.class,true);
            TableUtils.dropTable(connectionSource, Data.class,true);
            TableUtils.dropTable(connectionSource, Day.class,true);
            TableUtils.createTable(connectionSource, Weather.class);
            TableUtils.createTable(connectionSource, Data.class);
            TableUtils.createTable(connectionSource, Day.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
