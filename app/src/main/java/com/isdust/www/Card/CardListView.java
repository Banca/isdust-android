package com.isdust.www.Card;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.isdust.www.MyApplication;
import com.isdust.www.R;
import com.isdust.www.baseactivity.BaseSubPageActivity_new;
import com.isdust.www.datatype.PurchaseHistory;
import com.isdust.www.view.IsdustDialog;
import com.isdust.www.view.PullToRefreshView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leng Hanchao.
 * Midified and Refactored by Wang Ziqiang
 * isdust
Copyright (C) <2015>  <Wang Ziqiang,Leng Hanchao,Qing Wenkai,Huyang>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
public class CardListView extends BaseSubPageActivity_new {
	ListView mListView;
	IsdustDialog customRuningDialog;

	//线程池
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private PurchaseHistory[] xiancheng_ph;
	private boolean xiancheng_bollean;
	private MyApplication isdustapp;
//	private ProgressDialog dialog;


	private Context mContext;
	PullToRefreshView mPullToRefreshView;

	private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();	//列表框的数据
	SimpleAdapter adapter;	//列表的适配器

	final android.os.Handler handler = new android.os.Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0){//登录成功
//				Toast.makeText(mContext, xiancheng_login_status, 1000).show();
				executorService.execute(mRunnable_xiancheng_getdata);
			}
//			if (msg.what == 1){
//				Toast.makeText(mContext, xiancheng_login_status, 1000).show();
//			}
			if (msg.what == 2){//获取纪录



				Map<String, Object> map;

				for (int i=0;i<xiancheng_ph.length;i++) {
					map = new HashMap<String, Object>();
					map.put("name",xiancheng_ph[i].getBehavior() + xiancheng_ph[i].getMoney().replace("-","") + "元");
					map.put("ima", R.drawable.ic_card_mark);
					map.put("addr",xiancheng_ph[i].getAddr());
					map.put("time",xiancheng_ph[i].getTime());
					map.put("bala","￥" + xiancheng_ph[i].getBala().replace("-",""));
					listdata.add(map);}
				if(xiancheng_bollean==false){
					xiancheng_bollean=true;
					customRuningDialog.dismiss();

					adapter = new SimpleAdapter(mContext, listdata,
						R.layout.card_item, new String[] { "name", "ima", "addr", "time", "bala"},
						new int[] { R.id.TextView_library_title, R.id.iv_gridview_item,
								R.id.TextView_library_id,	R.id.TextView_library_bookrecnos,R.id.tv_gridview_item_bala});

						mListView.setAdapter(adapter);	//捆绑适配器}



				}
				adapter.notifyDataSetChanged();	//列表刷新

				mPullToRefreshView.onFooterRefreshComplete();
			}
			if (msg.what == 10){
				Toast.makeText(mContext, "网络访问超时，请重试", Toast.LENGTH_SHORT).show();
			}
		}
	};



	Runnable mRunnable_xiancheng_getdata = new Runnable() {
		public void run() {

			try {
				xiancheng_ph =  isdustapp.getUsercard().getPurData();
			} catch (IOException e) {
				e.printStackTrace();
				Message message = new Message();
				message.what = 10;
				handler.sendMessage(message);;
				return;
			}
			Message message = new Message();
			message.what = 2;
			handler.sendMessage(message);

		}
	};




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		isdustapp=(MyApplication)getApplication();
		isdustapp.getUsercard().chongzhijilu();
		xiancheng_bollean = false;


		MobclickAgent.onEvent(this, "schoolcard_xiaofeirecord");





		INIT(R.layout.card_listview,"消费记录");
		mListView=(ListView)findViewById(R.id.card_lisitview_detail);
		customRuningDialog = new IsdustDialog(mContext,
				IsdustDialog.RUNING_DIALOG, R.style.DialogTheme);   //初始化加载对话框
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				mPullToRefreshView.onHeaderRefreshComplete();

			}
		});
        mPullToRefreshView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				executorService.execute(mRunnable_xiancheng_getdata);;	//加数据

			}
		});






//		Intent intent = getIntent();
//		//获取数据
//		Bundle data = intent.getExtras();
//		xiancheng_username = data.getString("un");
//		xiancheng_password = data.getString("up");
//		//校园卡
		customRuningDialog.show();
		customRuningDialog.setMessage("正在拉取您的消费纪录");

//		dialog = ProgressDialog.show(
//				mContext, "提示",
//				"正在拉取您的消费纪录", true, true);
		executorService.execute(mRunnable_xiancheng_getdata);




	}



//	@Override
//	public void onFooterRefresh(PullToRefreshView view) {
//		mPullToRefreshView.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				executorService.execute(mRunnable_xiancheng_getdata);;	//加数据
//
//			}
//		}, 1000);
//	}
//	@Override
//	public void onHeaderRefresh(PullToRefreshView view) {
//		mPullToRefreshView.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				//设置更新时间
//				//mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
//				mPullToRefreshView.onHeaderRefreshComplete();
//			}
//		}, 1000);
//
//	}
}
