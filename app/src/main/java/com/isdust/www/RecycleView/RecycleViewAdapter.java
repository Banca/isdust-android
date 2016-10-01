package com.isdust.www.RecycleView;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isdust.www.Module.Catagory;
import com.isdust.www.R;
import com.isdust.www.Module.BaseModule;

import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //数据源
    private List<BaseModule> dataList;
    private Activity mContext;

    //构造函数
    public RecycleViewAdapter(Activity mContext,List<BaseModule> dataList) {
        this.dataList = dataList;
        this.mContext=mContext;
    }
    public void setDataList(List<BaseModule>modules){
        this.dataList=modules;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_module, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final BaseModule tmp = dataList.get(position);
        ((BodyViewHolder)viewHolder).img.setImageResource(tmp.getImage_id());
        ((BodyViewHolder)viewHolder).name.setText(tmp.getName());
        ((BodyViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.lunchActivity(mContext);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size() ;
    }


    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    private class BodyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;
        private BodyViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                name = (TextView) itemView.findViewById(R.id.name);
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }
}
