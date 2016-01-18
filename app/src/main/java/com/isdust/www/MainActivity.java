package com.isdust.www;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.isdust.www.baseactivity.BaseMainActivity_new;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseMainActivity_new {
	protected MyApplication isdustapp;
	static boolean ishadopended = false;
	private Timer timer_wel = null;
	private boolean bool_wel = false;
	private View form_welcome;
	private MyApplication isdustapp1;





	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		MobclickAgent.updateOnlineConfig(mContext);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

		OnlineConfigAgent.getInstance().setDebugMode(true);
		OnlineConfigAgent.getInstance().updateOnlineConfig(mContext);
		String install = OnlineConfigAgent.getInstance().getConfigParams(mContext, "install");

		if (!install.equals("true")){
			Toast.makeText(mContext,"第一次运行该程序，请保证手机能访问网络，然后重启该应用",Toast.LENGTH_LONG).show();
		}
		String braoadcast= OnlineConfigAgent.getInstance().getConfigParams(mContext, "system_broadcast");


		if (ishadopended == true) {    //程序已经启动
			INIT(R.layout.activity_main, "首页");

		}else {
			INIT(R.layout.activity_main, "首页",0);

		if (!braoadcast.equals("null")&&!braoadcast.equals("")){
			TextView a=new TextView(this);
				a.setText(braoadcast);
			a.setTextSize(20);
			//a.setGravity(Gravity.CENTER);
			new AlertDialog.Builder(mContext)
					.setTitle("公告")
					.setView(a)
					.setIcon(R.mipmap.isdust)
					.setPositiveButton("确定", null).show();


			}
//			ishadopended = true;
//			LayoutInflater inflate = LayoutInflater.from(this);
//			form_welcome = inflate.inflate(R.layout.welcome,null);
//			setContentView(form_welcome);		//Show welcome page
//			//next add some load event
//			timer_wel = new Timer();
//			timer_wel.schedule(task_wel, 2000, 2);		// start a 5s's timer after 2s

		}

	}

	public void onFormMainClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.btn_main_gonet:
				intent.setClass(this, GoNetActivity.class);//上网登录
				this.startActivity(intent);
				finish();
				break;
			case R.id.btn_main_kuaitong:
				intent.setClass(this, KuaiTongActivity.class);//快通查询
				this.startActivity(intent);
				finish();
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
				intent.setClass(this, Library_guancang_main.class);//图书馆
				this.startActivity(intent);
				finish();
				break;
			case R.id.btn_main_card:
				intent.setClass(this, CardActivity.class);//校园卡
				this.startActivity(intent);
				finish();
				break;
			case R.id.btn_main_news:
				intent.setClass(this, NewsActivity.class);//咨询
				this.startActivity(intent);
				finish();
				break;
			case R.id.btn_main_about:
				intent.setClass(this, AboutActivity.class);//关于
				this.startActivity(intent);
				break;
		}


	}

	
	final Handler handler_wel = new Handler(){
        public void handleMessage(Message msg){
                switch(msg.what){
                   case 1:
                	   bool_wel = true;
                   break;
                   case 2:
                	   float alp = form_welcome.getAlpha();
                	   //System.out.println(alp);
                	   if (alp < 0.015) {
                		   INIT(R.layout.activity_main,"首页");
                		   timer_wel.cancel();		//销毁 timer_wel
                	   }
                	   else {
                		   form_welcome.setAlpha((float) (alp - 0.01));	//修改欢迎页面的透明度
                	   }
                   break;
                }
                super.handleMessage(msg);
        }
	};
	protected void INIT(int pageid,String title) {
		isdustapp = (MyApplication) this.getApplication();
		setContentView(pageid);
		mContext = this;
		TextView title_name = (TextView) findViewById(R.id.title_bar_name);
		title_name.setText(title);	//修改页面标题
	}   //初始化

	TimerTask task_wel = new TimerTask(){
		public void run(){
			Message message = new Message();
			if (bool_wel)
				message.what = 1 ;
			else
				message.what = 2 ;		// Change Transparency's command
			handler_wel.sendMessage(message);
		}
	};
}
