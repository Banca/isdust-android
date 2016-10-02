package com.isdust.www.RecycleView;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isdust.www.Module.BaseModule;
import com.isdust.www.R;

import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //数据源
    private List<BaseModule> dataList;
    private Activity mContext;

    //构造函数
    public RecycleViewAdapter(Activity mContext, List<BaseModule> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    public void setDataList(List<BaseModule> modules) {
        this.dataList = modules;
        notifyDataSetChanged();
    }


    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_module, null));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        BodyViewHolder body = (BodyViewHolder) viewHolder;
        body.setIsRecyclable(false);
        final BaseModule tmp = dataList.get(position);
        body.img.setImageResource(tmp.getImage_id());
        body.name.setText(tmp.getName());
        if (0 == position % 2) {
            body.itemView.setBackgroundResource(R.color.color_btn_white);
        }
        body.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.lunchActivity(mContext);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 条目用的ViewHolder，里面只有一个TextView
     */
    private class BodyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;

        private BodyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
