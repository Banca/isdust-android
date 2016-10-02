package com.isdust.www.RecycleView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.isdust.www.Module.BaseModule;
import com.isdust.www.R;

import java.util.HashMap;
import java.util.List;

/**
 *
 * Created by zor on 2016/10/1.
 */

public class CheckboxAdapter extends BaseAdapter {

    private Context context;
    private List<BaseModule>listData;

    //记录checkbox的状态
    @SuppressLint("UseSparseArrays")
    public HashMap<Integer, Boolean> state = new HashMap<>();

    //构造函数
    public CheckboxAdapter(Context context,List<BaseModule>listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 重写View
    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_checkbox, null);

        //获取view
        ImageView image = (ImageView) convertView.findViewById(R.id.img);
        TextView username = (TextView) convertView.findViewById(R.id.name);
        TextView id = (TextView) convertView.findViewById(R.id.info);
        final CheckBox check = (CheckBox) convertView.findViewById(R.id.selected);

        //设置view资源
        image.setImageResource(listData.get(position).getImage_id());
        username.setText(listData.get(position).getName());
        id.setText(listData.get(position).getDesc());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setChecked(!check.isChecked());
            }
        });

        check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    if(!state.containsKey(position))
                    state.put(position, true);
                } else {
                    state.remove(position);
                }
            }
        });
        check.setChecked((state.get(position) != null));
        return convertView;
    }
}