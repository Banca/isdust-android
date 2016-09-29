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
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int TYPE_CATEGORY_ITEM = 0;
    private static final int TYPE_ITEM = 1;
    //数据源
    private List<Catagory> dataList;
    private Activity mContext;

    //构造函数
    public RecycleViewAdapter(Activity mContext,List<Catagory> dataList) {
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
        switch (viewType) {
            case TYPE_CATEGORY_ITEM:
                return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_header, null));
            case TYPE_ITEM:
                return new BodyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        switch (getItemViewType(position)){
            case TYPE_ITEM:
               // (HeaderViewHolder)viewHolder.
                break;
            case TYPE_CATEGORY_ITEM:
                //其他条目中的逻辑在此
                final BaseModule tmp = (BaseModule)getItem(position);
                ((BodyViewHolder) viewHolder).name.setText(tmp.getName());
                ((BodyViewHolder) viewHolder).info.setText(tmp.getDesc());
                ((BodyViewHolder) viewHolder).img.setBackgroundResource(tmp.getImage_id());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tmp.lunchActivity(mContext);
                    }
                });
                break;
        }



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

    public int getItemViewType(int position) {
        // 异常情况处理
        if (null == dataList || position <  0|| position > getCount()) {
            return TYPE_ITEM;
        }


        int categroyFirstIndex = 0;

        for (Catagory category : dataList) {
            int size = category.getItemCount();
            // 在当前分类中的索引值
            int categoryIndex = position - categroyFirstIndex;
            if (categoryIndex == 0) {
                return TYPE_CATEGORY_ITEM;
            }

            categroyFirstIndex += size;
        }

        return TYPE_ITEM;
    }
    public int getCount() {
        int count = 0;

        if (null != dataList) {
            //  所有分类中item的总和是ListVIew  Item的总个数
            for (Catagory catagory : dataList) {
                count += catagory.getItemCount();
            }
        }

        return count;
    }
    public Object getItem(int position) {

        // 异常情况处理
        if (null == dataList || position <  0|| position > getCount()) {
            return null;
        }

        // 同一分类内，第一个元素的索引值
        int categroyFirstIndex = 0;

        for (Catagory category : dataList) {
            int size = category.getItemCount();
            // 在当前分类中的索引值
            int categoryIndex = position - categroyFirstIndex;
            // item在当前分类内
            if (categoryIndex < size) {
                return  category.getItem( categoryIndex );
            }

            // 索引移动到当前分类结尾，即下一个分类第一个元素索引
            categroyFirstIndex += size;
        }

        return null;
    }
}
