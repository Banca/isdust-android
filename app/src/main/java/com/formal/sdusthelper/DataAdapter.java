package com.formal.sdusthelper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter{
		Context mContext;
		Context a;
		String [] [] mxiaofeijilu;
		public DataAdapter(Context context,String[] [] xiaofei){
			mContext = context;

			mxiaofeijilu=xiaofei;
		}
		@Override
		public int getCount() {
			return mxiaofeijilu.length;
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
			View vi = convertView;
			if(convertView == null){
				int len=mxiaofeijilu.length;
				for (int i=0;i<len;i++){
					vi = View.inflate(mContext, R.layout.card_item, null);
					TextView mTextview_item_time = (TextView) vi.findViewById(R.id.tv_gridview_item_time);
					mTextview_item_time.setText(mxiaofeijilu[i][0]);
					TextView mTextview_item_addr = (TextView) vi.findViewById(R.id.tv_gridview_item_addr);
					mTextview_item_addr.setText(mxiaofeijilu[i][2]);
					TextView mTextview_item_bala = (TextView) vi.findViewById(R.id.tv_gridview_item_bala);
					mTextview_item_bala.setText("余额 ￥"+mxiaofeijilu[i][4]);


				}

			}
			return vi;
		}
	}