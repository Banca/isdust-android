package com.isdust.www.RecycleView;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isdust.www.R;
import com.isdust.www.baseactivity.BaseModule;

import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    //数据源
    private List<BaseModule> dataList;
    private Activity mContext;

    //构造函数
    public RecycleViewAdapter(Activity mContext,List<BaseModule> dataList) {
        this.dataList = dataList;
        this.mContext=mContext;
    }

    /**
     * 判断当前position是否处于第一个
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
            return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
            //其他条目中的逻辑在此
            ((BodyViewHolder) viewHolder).name.setText(dataList.get(position).getName());
    ((BodyViewHolder) viewHolder).info.setText(dataList.get(position).getDesc());
    ((BodyViewHolder) viewHolder).img.setBackgroundResource(dataList.get(position).getImage_id());
    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dataList.get(position).lunchActivity(mContext);
        }
    });
}

    /**
     * 总条目数量是数据源数量+1，因为我们有个Header
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList.size() ;
    }

    /**
     *
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //如果是0，就是头，否则则是其他的item
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    /**
     * 给头部专用的ViewHolder，大家根据需求自行修改
     */
    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            //textView = (TextView) itemView.findViewById(R.id.item_tv);
        }
        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    private class BodyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        private TextView info;
        private ImageView img;
        private BodyViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                name = (TextView) itemView.findViewById(R.id.name);
                info = (TextView) itemView.findViewById(R.id.info);
                img = (ImageView) itemView.findViewById(R.id.img);
            }

        }
    }
}
