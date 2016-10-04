package com.isdust.www;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.isdust.www.RecycleView.RecycleViewAdapter;
import com.isdust.www.baseactivity.BaseMainActivity_new;

import java.util.Timer;

import pw.isdust.isdust.OnlineConfig;
import pw.isdust.isdust.function.ScheduleDB;

import static com.isdust.www.R.id.kecheng;


public class MainActivity extends BaseMainActivity_new {
	protected MyApplication isdustapp;
	static boolean ishadopended = false;
	static boolean broadcast=false;
	private Timer timer_wel = null;
	private boolean bool_wel = false;

	private TextView mTextView_kecheng;
	private RecycleViewAdapter adapter;
	private GridLayoutManager manager;
	private RecyclerView rcv;

//	private View form_welcome;
	//private MyApplication isdustapp1;





	public void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;


		INIT(R.layout.act1, "首页",0);
		rcv = (RecyclerView)findViewById(R.id.module);

		//开启数据库获取数据
		manager = new GridLayoutManager(this, 6);
       	rcv.setLayoutManager(manager);


		String kecheng_brief=(new ScheduleDB()).schedule_get_brief();

		mTextView_kecheng = (TextView)findViewById(kecheng);
		mTextView_kecheng.setText(kecheng_brief);

		/*listview = (ListView) findViewById(R.id.kechenglist);
		listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));*/

        //mSpinner = new spinner(this);
		//mSpinner.init();

		String braoadcast= OnlineConfig.getConfigParams( "system_broadcast");
		TextView info = (TextView)findViewById(R.id.notification);
		info.setText(braoadcast);



		}
/*	private List<String> getData(){

		List<String> data = new ArrayList<String>();
		kecheng=new KeCheng(db,this);
		data=kecheng.getCourse_end();
		db.close();
		return data;
	}*/




	public boolean joinQQGroup(String key) {
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try {
			startActivity(intent);
			return true;
		} catch (Exception e) {
			// 未安装手Q或安装的版本不支持
			return false;
		}
	}

	public void addqq(View v){
		joinQQGroup("EpMF_C2Na71cIhD2-BB80Vzy20xpLJ9o");
	}

	@Override
	protected void onStart() {
		super.onStart();
		//mSpinner.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		//mSpinner.onStop();
	}

}