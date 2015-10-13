package com.formal.sdusthelper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter{
	Context mContext;
	static int count = 0;
	public DataAdapter(Context context){
		mContext = context;
	}

	public DataAdapter(Context context,int num){
		mContext = context;
		count = num;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//观察convertView随ListView滚动情况
		Log.v("MyListViewBase", "getView " + position + " " + convertView);
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.card_item, null);
			TextView texttest = (TextView) convertView.findViewById(R.id.tv_gridview_item_name);
			texttest.setText("test" + position);
			//convertView.setTag();
		}
		return convertView;
	}
}