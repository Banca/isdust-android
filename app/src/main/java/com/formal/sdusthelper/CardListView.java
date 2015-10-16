package com.formal.sdusthelper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.formal.sdusthelper.datatype.PurchaseHistory;
import com.formal.sdusthelper.view.PullToRefreshView;
import com.formal.sdusthelper.view.PullToRefreshView.OnFooterRefreshListener;
import com.formal.sdusthelper.view.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import pw.isdust.isdust.function.Xiaoyuanka;

/**
 * 实现OnHeaderRefreshListener,OnFooterRefreshListener接口
 * @author Administrator
 *
 */
public class CardListView extends ListActivity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	//线程池
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private String xiancheng_username,xiancheng_password,xiancheng_login_status;
	private PurchaseHistory[] xiancheng_ph;
	private boolean xiancheng_bollean;


	private Context mContext;
	PullToRefreshView mPullToRefreshView;
	private String username,password;	//存储校园卡 用户名、密码
	private Xiaoyuanka usercard;
	private String[][] userdata;
	private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();	//列表框的数据
	SimpleAdapter adapter;	//列表的适配器

	final android.os.Handler handler = new android.os.Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0){//登录成功
				TextView textname = (TextView) findViewById(R.id.textView_card_name);
				TextView textnum = (TextView) findViewById(R.id.textView_card_number);
				TextView textclass = (TextView) findViewById(R.id.textView_card_class);
				TextView textbala = (TextView) findViewById(R.id.textView_card_balance);
				textname.setText(usercard.getStuName());
				textnum.setText(usercard.getStuNumber());
				textclass.setText(usercard.getStuClass());
				textbala.setText("￥" + usercard.getBalance()); //显示余额
				Toast.makeText(mContext, xiancheng_login_status, 1000).show();
				executorService.execute(mRunnable_xiancheng_getdata);
			}
			if (msg.what == 1){
				Toast.makeText(mContext, xiancheng_login_status, 1000).show();
			}
			if (msg.what == 2){



				Map<String, Object> map;

				for (int i=0;i<xiancheng_ph.length;i++) {
					map = new HashMap<String, Object>();
					map.put("name",xiancheng_ph[i].getBehavior() + xiancheng_ph[i].getMoney().replace("-","") + "元");
					map.put("ima",R.drawable.ic_card_mark);
					map.put("addr",xiancheng_ph[i].getAddr());
					map.put("time",xiancheng_ph[i].getTime());
					map.put("bala","￥" + xiancheng_ph[i].getBala().replace("-",""));
					listdata.add(map);}
				if(xiancheng_bollean==false){
					xiancheng_bollean=true;

				adapter = new SimpleAdapter(mContext, listdata,
						R.layout.card_item, new String[] { "name", "ima", "addr", "time", "bala"},
						new int[] { R.id.tv_gridview_item_name, R.id.iv_gridview_item,
								R.id.tv_gridview_item_addr,	R.id.tv_gridview_item_time,R.id.tv_gridview_item_bala});
				setListAdapter(adapter);	//捆绑适配器}
				}
				adapter.notifyDataSetChanged();	//列表刷新

				mPullToRefreshView.onFooterRefreshComplete();
			}
		}
	};


	Runnable mRunnable_xiancheng_login = new Runnable() {
		@Override
		public void run() {
			xiancheng_login_status = usercard.login(xiancheng_username,xiancheng_password);
			if (xiancheng_login_status.equals("登录成功")){
				xiancheng_bollean=false;
				Message message = new Message();
				message.what = 0;
				handler.sendMessage(message);;


			}else {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);;

			}
		}
	};
	Runnable mRunnable_xiancheng_getdata = new Runnable() {
		public void run() {

			xiancheng_ph = usercard.getPurData();
			Message message = new Message();
			message.what = 2;
			handler.sendMessage(message);;

		}
	};




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext=this;





		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_listview);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);







		Intent intent = getIntent();
		//获取数据
		Bundle data = intent.getExtras();
		xiancheng_username = data.getString("un");
		xiancheng_password = data.getString("up");
		//校园卡
		usercard = new Xiaoyuanka(this);


		executorService.execute(mRunnable_xiancheng_login);




	}



	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(this, "positon = "+position, 1000).show();
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				executorService.execute(mRunnable_xiancheng_getdata);;	//加数据

			}
		}, 1000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				//设置更新时间
				//mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 1000);
		
	}
}
