package com.formal.sdusthelper;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
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

/**
 * 实现OnHeaderRefreshListener,OnFooterRefreshListener接口
 * @author Administrator
 *
 */
public class CardListView extends ListActivity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	//线程池
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private PurchaseHistory[] xiancheng_ph;
	private boolean xiancheng_bollean;
	private MyApplication isdustapp;
	private ProgressDialog dialog;


	private Context mContext;
	PullToRefreshView mPullToRefreshView;

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
				textname.setText( isdustapp.getUsercard().getStuName());
				textnum.setText( isdustapp.getUsercard().getStuNumber());
				textclass.setText( isdustapp.getUsercard().getStuClass());
				textbala.setText("￥" +  isdustapp.getUsercard().getBalance()); //显示余额
//				Toast.makeText(mContext, xiancheng_login_status, 1000).show();
				executorService.execute(mRunnable_xiancheng_getdata);
			}
//			if (msg.what == 1){
//				Toast.makeText(mContext, xiancheng_login_status, 1000).show();
//			}
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

					TextView textname = (TextView) findViewById(R.id.textView_card_name);
					TextView textnum = (TextView) findViewById(R.id.textView_card_number);
					TextView textclass = (TextView) findViewById(R.id.textView_card_class);
					TextView textbala = (TextView) findViewById(R.id.textView_card_balance);
					textname.setText( isdustapp.getUsercard().getStuName());
					textnum.setText( isdustapp.getUsercard().getStuNumber());
					textclass.setText( isdustapp.getUsercard().getStuClass());
					textbala.setText("￥" +  isdustapp.getUsercard().getBalance()); //显示余额
					xiancheng_bollean=true;
					dialog.dismiss();

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



	Runnable mRunnable_xiancheng_getdata = new Runnable() {
		public void run() {

			xiancheng_ph =  isdustapp.getUsercard().getPurData();
			Message message = new Message();
			message.what = 2;
			handler.sendMessage(message);

		}
	};




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mContext=this;
		isdustapp=(MyApplication)getApplication();
		isdustapp.getUsercard().chongzhijilu();
		xiancheng_bollean=false;







		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_listview);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);






//		Intent intent = getIntent();
//		//获取数据
//		Bundle data = intent.getExtras();
//		xiancheng_username = data.getString("un");
//		xiancheng_password = data.getString("up");
//		//校园卡


		dialog = ProgressDialog.show(
				mContext, "提示",
				"正在拉取您的消费纪录", true, true);
		executorService.execute(mRunnable_xiancheng_getdata);




	}



	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		//Toast.makeText(this, "positon = "+position, 1000).show();
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
