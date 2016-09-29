package com.isdust.www;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.isdust.www.Spinner.spinner;
import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.isdust.www.tab.KeCheng;
import com.umeng.onlineconfig.OnlineConfigAgent;

import java.util.Timer;


public class MainActivity extends BaseMainActivity_new {
	protected MyApplication isdustapp;
	static boolean ishadopended = false;
	static boolean broadcast=false;
	private Timer timer_wel = null;
	private boolean bool_wel = false;
	private spinner mSpinner;
	private SQLiteDatabase db;
	private KeCheng kecheng;
	private TextView kc;
    private String kechengInfo;
	private ListView listview;
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

        //开启数据库获取数据
		db = openOrCreateDatabase("jiaowu_schedule.db", Context.MODE_PRIVATE, null);
        kecheng=new KeCheng(db,this);
        kechengInfo=kecheng.getKecheng();
        //db.close();

        kc = (TextView)findViewById(R.id.kecheng);
        kc.setText(kechengInfo);

		/*listview = (ListView) findViewById(R.id.kechenglist);
		listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));*/

        mSpinner = new spinner(this);
		mSpinner.init();

		String braoadcast= OnlineConfigAgent.getInstance().getConfigParams(mContext, "system_broadcast");
		TextView info = (TextView)findViewById(R.id.notification);
		info.setText(braoadcast);

	/*

		MobclickAgent.updateOnlineConfig(mContext);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		OnlineConfigAgent.getInstance().setDebugMode(true);
		OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
		String install = OnlineConfigAgent.getInstance().getConfigParams(mContext, "install");



		if (ishadopended == true) {    //程序已经启动
			INIT(R.layout.act1, "首页",0);
			//String braoadcast= OnlineConfigAgent.getInstance().getConfigParams(mContext, "system_broadcast");
			if (!install.equals("true")){
				Toast.makeText(mContext,"第一次运行该程序，请保证手机能访问网络，然后重启该应用",Toast.LENGTH_LONG).show();
			}
//			if (!braoadcast.equals("null")&&!braoadcast.equals("")){
//				TextView a=new TextView(this);
//				a.setText(braoadcast);
//				a.setTextSize(20);
//				new AlertDialog.Builder(mContext)
//						.setTitle("公告")
//						.setView(a)
//						.setIcon(R.mipmap.isdust)
//						.setPositiveButton("确定", null).show();
//			}

		}else {

			//INIT(R.layout.welcome);
			ishadopended = true;
			timer_wel = new Timer();
			timer_wel.schedule(task_wel, 2000, 2);		// start a 5s's timer after 2s

		}


*/

		}
/*	private List<String> getData(){

		List<String> data = new ArrayList<String>();
		kecheng=new KeCheng(db,this);
		data=kecheng.getCourse_end();
		db.close();
		return data;
	}*/




	public void onFormMainClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.btn_main_gonet:
				intent.setClass(this, GoNetActivity.class);//上网登录
				this.startActivity(intent);
				//finish();
				break;
			case R.id.btn_main_kuaitong:
				intent.setClass(this, KuaiTongActivity.class);//快通查询
				this.startActivity(intent);
				//finish();
				break;
			case R.id.btn_main_schedule:
				intent.setClass(this, jiaowu_Schedule_main.class);//课程表
				this.startActivity(intent);
				break;
			case R.id.btn_main_emptyroom:
				intent.setClass(this, Jiaowu_EmptyRoom.class);//空自习室
				this.startActivity(intent);
				break;
			case R.id.btn_main_library:
				intent.setClass(this, Library_index.class);//图书馆
				this.startActivity(intent);
				//finish();
				break;
			case R.id.btn_main_card:
				intent.setClass(this, CardActivity.class);//校园卡
				this.startActivity(intent);
			//	finish();
				break;
/*			case R.id.btn_main_news:
				intent.setClass(this, NewsActivity.class);//咨询
				this.startActivity(intent);
				finish();
				break;
			case R.id.btn_main_about:
				intent.setClass(this, AboutActivity.class);//关于
				this.startActivity(intent);
				break;*/
			case R.id.btn_main_score:
				intent.setClass(this, Jiaowu_chengjichaxun_main.class);//查询成绩
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				this.startActivity(intent);
				break;
		}


	}
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
/*

	final Handler handler_wel = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
				case 1:
					bool_wel = true;
					break;
				case 2:
//                	   float alp = form_welcome.getAlpha();
					//System.out.println(alp);
//                	   if (alp < 0.015) {
					timer_wel.cancel();
					INIT(R.layout.act1, "首页",0);
					String braoadcast= OnlineConfigAgent.getInstance().getConfigParams(mContext, "system_broadcast");
					String install = OnlineConfigAgent.getInstance().getConfigParams(mContext, "install");
					if (!install.equals("true")){
						Toast.makeText(mContext,"第一次运行该程序，请保证手机能访问网络，然后重启该应用",Toast.LENGTH_LONG).show();
					}
					if (!braoadcast.equals("null")&&!braoadcast.equals("")){
						if (broadcast==false){
						TextView a=new TextView(mContext);
							broadcast=true;
						a.setText(braoadcast);
						a.setTextSize(20);
						new AlertDialog.Builder(mContext)
								.setTitle("公告")
								.setView(a)
								.setIcon(R.mipmap.isdust)
								.setPositiveButton("确定", null).show();
					}}
						//销毁 timer_wel
//                	   }
//                	   else {
//                		   form_welcome.setAlpha((float) (alp - 0.01));	//修改欢迎页面的透明度
//                	   }
					break;
			}
			super.handleMessage(msg);
		}
	};

	TimerTask task_wel = new TimerTask(){
		public void run(){
			Message message = new Message();
			if (bool_wel)
				message.what = 1 ;
			else
				message.what = 2 ;		// Change Transparency's command
			handler_wel.sendMessage(message);
		}
	};*/
	public void addqq(View v){
		joinQQGroup("EpMF_C2Na71cIhD2-BB80Vzy20xpLJ9o");
	}

	@Override
	protected void onStart() {
		super.onStart();
		mSpinner.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mSpinner.onStop();
	}

}