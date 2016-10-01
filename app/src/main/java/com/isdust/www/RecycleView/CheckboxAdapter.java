package com.isdust.www.RecycleView;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

/**
 *
 * Created by zor on 2016/10/1.
 */

public class CheckboxAdapter extends BaseAdapter {

    Context context;
    List<BaseModule>listData;
    //记录checkbox的状态
    public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();

    boolean ischecked = false;
    //构造函数
    public CheckboxAdapter(Context context,List<BaseModule>listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    // 重写View
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater mInflater = LayoutInflater.from(context);
        convertView = mInflater.inflate(R.layout.layout_item_checkbox, null);
        ImageView image = (ImageView) convertView.findViewById(R.id.img);
        image.setImageResource(listData.get(position).getImage_id());
        TextView username = (TextView) convertView.findViewById(R.id.name);
        username.setText(listData.get(position).getName());
        TextView id = (TextView) convertView.findViewById(R.id.info);
        id.setText(listData.get(position).getDesc());
        final CheckBox check = (CheckBox) convertView.findViewById(R.id.selected);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischecked=!ischecked;
                check.setChecked(ischecked);
            }
        });

        check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    if(!state.containsKey(position))
                    state.put(position, isChecked);
                } else {
                    state.remove(position);
                }
            }
        });
        check.setChecked((state.get(position) == null ? false : true));
        return convertView;
    }
}