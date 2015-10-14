package com.formal.sdusthelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnClickListener{
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		timer_wel.schedule(task_wel,2000,2);		// start a 5s's timer after 2s
		//reDimUI(form_main);		//Menu and title's xml to band this And Show home page


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
		switch (v.getId()) {
			case R.id.FormCard_button_login:
				EditText textuser = (EditText) findViewById(R.id.FormCard_editText_user);
				EditText textpwd = (EditText) findViewById(R.id.FormCard_editText_pwd);

				//记住密码
				//实例化SharedPreferences对象
				SharedPreferences mySharedPreferences= getSharedPreferences("CardData",	Activity.MODE_PRIVATE);
				//实例化SharedPreferences.Editor对象
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				//用putString的方法保存数据
				editor.putString("name", "Karl");
				editor.putString("habit", "sleep");
				//提交当前数据
				editor.commit();

				//设置传递方向
				Intent intent = new Intent();
				intent.setClass(this,CardListView.class);
				//绑定数据
				Bundle data = new Bundle();

				data.putString("un", textuser.getText().toString());
				data.putString("up", textpwd.getText().toString());
				intent.putExtras(data);
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
			reDimUI(form_jiaowu,R.string.menu_jiaowu);
			break;
		case R.id.slide_menu_library:
			reDimUI(form_library,R.string.menu_library);
			break;
		case R.id.slide_menu_card:
			reDimUI(form_card,R.string.menu_card);
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
