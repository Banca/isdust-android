package com.formal.sdusthelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends Activity implements OnClickListener{

	private String xiancheng_username,xiancheng_password,xiancheng_login_status;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private ProgressDialog dialog;

	Context mContext;

	private MyApplication isdustapp;	//通过app调全局变量
	private SlideMenu slideMenu;
	private View form_welcome = null,
	 			 form_main = null,
			     form_cmcc = null,
			     form_jiaowu = null,
			     form_library = null,
			     form_card = null,
			     form_life = null,
			     form_news = null,
			     form_set = null;
	private Timer timer_wel = null;
	private boolean bool_wel = false;
	final android.os.Handler handler = new android.os.Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0){//登录成功
				Button btnlogin = (Button) findViewById(R.id.FormCard_button_login);
				ImageButton btnquery = (ImageButton) findViewById(R.id.FormCard_button_query);
				ImageButton btnchangepwd = (ImageButton) findViewById(R.id.FormCard_button_changepwd);
				ImageButton btnloss = (ImageButton) findViewById(R.id.FormCard_button_loss);
				ImageButton btnlogout = (ImageButton) findViewById(R.id.FormCard_button_logout);

				btnlogin.setEnabled(false);
				btnquery.setEnabled(true);
				btnchangepwd.setEnabled(true);
				btnloss.setEnabled(true);
				btnlogout.setEnabled(true);
				btnlogin.setBackgroundColor(getResources().getColor(R.color.color_btn_gray));
				btnquery.setBackgroundResource(R.drawable.btn_purchhistory);
				btnchangepwd.setBackgroundResource(R.drawable.btn_changepwd);
				btnloss.setBackgroundResource(R.drawable.btn_loss);
				btnlogout.setBackgroundResource(R.drawable.btn_logout);
//				Intent intent = new Intent();
//				intent.setClass(mContext,CardListView.class);
//				mContext.startActivity(intent);
			}
			if (msg.what == 1){
				Toast.makeText(mContext, xiancheng_login_status, 1000).show();
			}

		}
	};

	Runnable mRunnable_xiancheng_login = new Runnable() {
		@Override
		public void run() {
			xiancheng_login_status = isdustapp.getUsercard().login(xiancheng_username,xiancheng_password);
			dialog.dismiss();
			if (xiancheng_login_status.equals("登录成功")){

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isdustapp = (MyApplication)this.getApplication();
		mContext=this;

		LayoutInflater inflate = LayoutInflater.from(this);

		form_welcome = inflate.inflate(R.layout.welcome,null);
		setContentView(form_welcome);		//Show welcome page
		form_main = inflate.inflate(R.layout.activity_main,null);
		form_cmcc = inflate.inflate(R.layout.helper_cmcc,null);
		form_jiaowu = inflate.inflate(R.layout.helper_jiaowu,null);
		form_library = inflate.inflate(R.layout.helper_library,null);
		form_card = inflate.inflate(R.layout.helper_card,null);
		form_life = inflate.inflate(R.layout.helper_life,null);
		form_news = inflate.inflate(R.layout.helper_news,null);
		form_set = inflate.inflate(R.layout.helper_set,null);
		
		//next add some load event
		timer_wel = new Timer();
		timer_wel.schedule(task_wel, 500, 2);		// start a 5s's timer after 2s
		//reDimUI(form_main);		//Menu and title's xml to band this And Show home page

//		Kongzixishi a=new Kongzixishi();
//		a.huoquzixishi("J7-310室",5);

//		Xiaoyuanka b=new Xiaoyuanka(this);
//		String c= b.login("1501060225", "960826");
		//c=b.changepassword("061406","061406","370112199606264517");
		//b.guashi("960826");
//		Xiaoli b=new Xiaoli();
//
//		Xuankepingtai a=new Xuankepingtai();
//		a.losin("201501060225","960826wang");
//		a.chaxun(Xiaoli.get_xiaoli()+"","2015-2016","1");


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_menu_btn:
			if (slideMenu.isMainScreenShowing()) {
				slideMenu.openMenu();
			} else {
				slideMenu.closeMenu();
			}
			break;
		}
	}

	public void onFormCardClick(View v) {
		EditText textuser = (EditText) findViewById(R.id.FormCard_editText_user);
		EditText textpwd = (EditText) findViewById(R.id.FormCard_editText_pwd);
		CheckBox checkkeeppwd = (CheckBox) findViewById(R.id.FormCard_checkBox_savepwd);
		Button btnlogin = (Button) findViewById(R.id.FormCard_button_login);
		ImageButton btnquery = (ImageButton) findViewById(R.id.FormCard_button_query);
		ImageButton btnchangepwd = (ImageButton) findViewById(R.id.FormCard_button_changepwd);
		ImageButton btnloss = (ImageButton) findViewById(R.id.FormCard_button_loss);
		ImageButton btnlogout = (ImageButton) findViewById(R.id.FormCard_button_logout);
		//设置传递方向
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.FormCard_button_login:	//登陆按钮
				//实例化SharedPreferences对象
				SharedPreferences mySharedPreferences= getSharedPreferences("CardData",	Activity.MODE_PRIVATE);
				//实例化SharedPreferences.Editor对象
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				//用putString的方法保存数据
				editor.putString("username", textuser.getText().toString());
				if (checkkeeppwd.isChecked()) {	//记住密码
					editor.putBoolean("keeppwd", true);
					editor.putString("password", textpwd.getText().toString());
				}
				else {	//不记住密码
					editor.putBoolean("keeppwd", false);
					editor.putString("password", "");
				}
				//提交当前数据
				editor.commit();

				dialog = ProgressDialog.show(
						mContext, "提示",
						"正在登录中", true, true);
				xiancheng_username=textuser.getText().toString();
				xiancheng_password=textpwd.getText().toString();
				executorService.execute(mRunnable_xiancheng_login);

				break;
			case R.id.FormCard_button_query:	//查询按钮
				intent.setClass(this,CardListView.class);
				//启动activity
				this.startActivity(intent);
				break;
			case R.id.FormCard_button_changepwd:	//改密按钮
				intent.setClass(this,CardChangePwdActivity.class);
				//启动activity
				this.startActivity(intent);
			case R.id.FormCard_button_logout:	//注销按钮
				btnlogin.setEnabled(true);
				btnquery.setEnabled(false);
				btnchangepwd.setEnabled(false);
				btnloss.setEnabled(false);
				btnlogout.setEnabled(false);
				btnlogin.setBackgroundColor(getResources().getColor(R.color.color_btn_blue));
				btnquery.setBackgroundResource(R.drawable.btn_purchhistory_gray);
				btnchangepwd.setBackgroundResource(R.drawable.btn_changepwd_gray);
				btnloss.setBackgroundResource(R.drawable.btn_loss_gray);
				btnlogout.setBackgroundResource(R.drawable.btn_logout_gray);
				break;
			case R.id.FormCard_button_loss:	//挂失按钮
				intent.setClass(this, CardLossActivity.class);
				//启动activity
				this.startActivity(intent);
				break;
		}

	}

	public void onFormLifeClick(View v) {
		switch (v.getId()) {
			case R.id.FormLife_btn_map:
				//设置传递方向
				Intent intent = new Intent();

				intent.setClass(this,SchoolMapActivity.class);

				//启动activity
				this.startActivity(intent);
				break;
		}
	}
	public void onFormJiaowuClick(View v) {
		//设置传递方向
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.FormJiaowu_button_emptyroom:
				intent.setClass(this,EmptyRoomActivity.class);
				//启动activity
				this.startActivity(intent);
				break;
			case R.id.FormJiaowu_button_schedule:
				intent.setClass(this,ScheduleActivity.class);
				//启动activity
				this.startActivity(intent);
				break;
		}
	}
	public void onMenuClick(View v) {
		switch (v.getId()) {
		case R.id.slide_menu_home:
			reDimUI(form_main,R.string.menu_home);
			break;
		case R.id.slide_menu_cmcc:
			reDimUI(form_cmcc,R.string.menu_cmcc);
			break;
		case R.id.slide_menu_jiaowu:
			reDimUI(form_jiaowu, R.string.menu_jiaowu);
			break;
		case R.id.slide_menu_library:
			reDimUI(form_library,R.string.menu_library);
			break;
		case R.id.slide_menu_card:
			reDimUI(form_card,R.string.menu_card);
			EditText textuser = (EditText) findViewById(R.id.FormCard_editText_user);
			EditText textpwd = (EditText) findViewById(R.id.FormCard_editText_pwd);
			CheckBox checkkeeppwd = (CheckBox) findViewById(R.id.FormCard_checkBox_savepwd);
			ImageButton btnquery = (ImageButton) findViewById(R.id.FormCard_button_query);
			ImageButton btnchangepwd = (ImageButton) findViewById(R.id.FormCard_button_changepwd);
			ImageButton btnloss = (ImageButton) findViewById(R.id.FormCard_button_loss);
			ImageButton btnlogout = (ImageButton) findViewById(R.id.FormCard_button_logout);
			//在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
			SharedPreferences sharedPreferences= getSharedPreferences("CardData", Activity.MODE_PRIVATE);
			// 使用getString方法获得value，注意第2个参数是value的默认值
			String name =sharedPreferences.getString("username", "");
			String pwd =sharedPreferences.getString("password", "");
			Boolean keeppwd = sharedPreferences.getBoolean("keeppwd", true);
			textuser.setText(name);
			textpwd.setText(pwd);
			checkkeeppwd.setChecked(keeppwd);

			btnquery.setEnabled(false);
			btnchangepwd.setEnabled(false);
			btnloss.setEnabled(false);
			btnlogout.setEnabled(false);
			break;
		case R.id.slide_menu_life:
			reDimUI(form_life,R.string.menu_life);
			break;
		case R.id.slide_menu_news:
			reDimUI(form_news,R.string.menu_news);
			break;
		case R.id.slide_menu_set:
			reDimUI(form_set,R.string.menu_set);
			break;
		}
		
		if (!slideMenu.isMainScreenShowing()) {
			slideMenu.closeMenu();
		}
	}
	
	private void reDimUI(View formid, int layoutid) {
		setContentView(formid);		//Show page
		slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
		TextView title_name = (TextView) findViewById(R.id.title_bar_name);
		title_name.setText(getResources().getString(layoutid));	//修改页面标题
		menuImg.setOnClickListener(this);
	}
	
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
	
	final Handler handler_wel = new Handler(){
        public void handleMessage(Message msg){
                switch(msg.what){
                   case 1:
                	   bool_wel = true;
                   break;
                   case 2:
                	   float alp = form_welcome.getAlpha();
                	   System.out.println(alp);
                	   if (alp < 0.015) {
                		   reDimUI(form_main,R.string.menu_home);
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
	
}
