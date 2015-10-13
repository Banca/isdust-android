package com.formal.sdusthelper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
	private List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();	//列表框的数据
	SimpleAdapter adapter;	//列表的适配器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_listview);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterRefreshListener(this);

		Intent intent = getIntent();
		//获取数据
		Bundle data = intent.getExtras();
		username = data.getString("un");
		password = data.getString("up");
		//校园卡
		usercard = new Xiaoyuanka(this);
		String result;
		result = usercard.login(username,password);

		Toast.makeText(this, result, 1000).show();

		TextView textname = (TextView) findViewById(R.id.textView_card_name);
		TextView textnum = (TextView) findViewById(R.id.textView_card_number);
		TextView textclass = (TextView) findViewById(R.id.textView_card_class);
		TextView textbala = (TextView) findViewById(R.id.textView_card_balance);
		if (result == "登录成功") {
			textname.setText(usercard.getStuName());
			textnum.setText(usercard.getStuNumber());
			textclass.setText(usercard.getStuClass());
			textbala.setText("￥" + usercard.getBalance()); //显示余额

			getData();	//加数据
		}
		adapter = new SimpleAdapter(this, listdata,
				R.layout.card_item, new String[] { "name", "ima", "addr", "time", "bala"},
				new int[] { R.id.tv_gridview_item_name, R.id.iv_gridview_item,
						R.id.tv_gridview_item_addr,	R.id.tv_gridview_item_time,R.id.tv_gridview_item_bala});
		setListAdapter(adapter);	//捆绑适配器
	}

	private void getData() {
		Map<String, Object> map;
		PurchaseHistory[] ph;
		ph = usercard.getPurData();
		for (int i=0;i<ph.length;i++) {
			map = new HashMap<String, Object>();
			map.put("name",ph[i].getBehavior() + ph[i].getMoney() + "元");
			map.put("ima",R.drawable.ic_card_mark);
			map.put("addr",ph[i].getAddr());
			map.put("time",ph[i].getTime());
			map.put("bala","￥" + ph[i].getBala());
			listdata.add(map);
		}
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
				getData();	//加数据
				adapter.notifyDataSetChanged();	//列表刷新
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
