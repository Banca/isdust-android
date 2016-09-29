package com.isdust.www.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isdust.www.Module.Catagory;
import com.isdust.www.R;
import com.isdust.www.Module.BaseModule;

import java.util.List;

/**
 *
 * Created by zor on 2016/9/29.
 */

public class MyAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Catagory> deptList;

    public MyAdapter(Context context, List<Catagory> deptList) {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<BaseModule> productList = deptList.get(groupPosition)
                .getProductList();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {

        final BaseModule moudle = (BaseModule) getChild(groupPosition,
                childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.layout_item, null);
        }

        ImageView img = (ImageView) view.findViewById(R.id.img);
        img.setImageResource(moudle.getImage_id());
        TextView childItem = (TextView) view.findViewById(R.id.name);
        childItem.setText(moudle.getName());
        TextView childItem2 = (TextView) view.findViewById(R.id.info);
        childItem2.setText(moudle.getDesc());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moudle.lunchActivity(context);
            }
        });
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<BaseModule> productList = deptList.get(groupPosition)
                .getProductList();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view,
                             ViewGroup parent) {

        Catagory Catagory = (Catagory) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.layout_header, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.header);
        heading.setText(Catagory.getmCategoryName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

