package com.formal.sdusthelper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.formal.sdusthelper.view.PullToRefreshView;
import com.formal.sdusthelper.view.PullToRefreshView.OnFooterRefreshListener;
import com.formal.sdusthelper.view.PullToRefreshView.OnHeaderRefreshListener;

import pw.isdust.isdust.function.Xiaoyuanka;

/**
 * 实现OnHeaderRefreshListener,OnFooterRefreshListener接口
 * @author Administrator
 *
 */
public class CardListView extends ListActivity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	PullToRefreshView mPullToRefreshView;
	private String username,password;	//存储校园卡 用户名、密码
	private Xiaoyuanka usercard;
	private String[][] userdata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_listview);

		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		setListAdapter(new DataAdapter(this));
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);

		Intent intent = getIntent();
		//获取数据
		Bundle data = intent.getExtras();
		username = data.getString("un");
		password = data.getString("up");
		//校园卡
		/*usercard = new Xiaoyuanka(this);
		String result;
		result = usercard.login(username,password);
		Toast.makeText(this, result, 1000).show();
		userdata = usercard.chaxun();
		Toast.makeText(this, userdata[0][1], 1000).show();*/
		//String b=a.login("1501060225", "960826");
		//a.chaxun();
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
				mPullToRefreshView.onFooterRefreshComplete();
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
		},1000);
		
	}
}
