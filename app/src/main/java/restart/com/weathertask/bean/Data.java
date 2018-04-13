package restart.com.weathertask.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */
@DatabaseTable(tableName = "tb_data")
public class Data implements Serializable{
    @DatabaseField(generatedId = true)
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @DatabaseField
    private String shidu;
    @DatabaseField
    private int pm25;
    @DatabaseField
    private int pm10;
    @DatabaseField
    private String quality;
    @DatabaseField
    private String wendu;
    @DatabaseField
    private String ganmao;
    private List<Day> forecast;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @DatabaseField(foreign = true,useGetSet = true)

    private Weather weather;


    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public List<Day> getForecast() {
        return forecast;
    }

    public void setForecast(List<Day> forecast) {
        this.forecast = forecast;
    }
}
