package restart.com.weathertask.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import restart.com.weathertask.R;
import restart.com.weathertask.bean.Day;

/**
 * Created by Administrator on 2018/4/13.
 */

public class WeatherAdapter extends BaseAdapter {
    private List<Day> datas;
    private Context context;
    private int[] image_type = {R.drawable.rain,R.drawable.overcast_sky,R.drawable.cloudy,R.drawable.sunny,R.drawable.b,R.drawable.b,R.drawable.b,R.drawable.b};
    private String[] type = {"小雨","阴","多云","晴","阵雨","中雨","暴雨","大雨"};
    private Map<String, Integer> map = new HashMap<>();
    private int imageType;

    public WeatherAdapter(List<Day> datas, Context context) {
        this.datas = datas;
        this.context = context;
        for (int i = 0; i < type.length; i++) {
            map.put(type[i], image_type[i]);
        }

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_weather, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_type = convertView.findViewById(R.id.id_iv_type);
            viewHolder.tv_api = convertView.findViewById(R.id.id_tv_api);
            viewHolder.tv_week = convertView.findViewById(R.id.id_tv_week);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        String week = datas.get(position).getDate().substring(datas.get(position).getDate().length()-3);
        viewHolder.tv_week.setText(week);
        String api = String.valueOf(datas.get(position).getAqi());
        viewHolder.tv_api.setText(api);
        Log.d("", "getView: ----------------------------" +
                datas.get(position).getType());
        if (datas.get(position).getType() != null) {
            try {
                imageType = map.get(datas.get(position).getType());
            } catch (NullPointerException e) {
                imageType = R.drawable.b;
            }
        }
        if (image_type != null) {
            viewHolder.iv_type.setImageResource(imageType);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_week;
        TextView tv_api;
        ImageView iv_type;

    }
}
