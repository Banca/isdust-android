package com.formal.sdusthelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;

import java.util.Timer;
import java.util.TimerTask;

import pw.isdust.isdust.function.Tushuguan;


public class MainActivity extends BaseMainActivity {

	private Timer timer_wel = null;
	private boolean bool_wel = false;
	private View form_welcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflate = LayoutInflater.from(this);
		form_welcome = inflate.inflate(R.layout.welcome,null);
		setContentView(form_welcome);		//Show welcome page
		//next add some load event
		timer_wel = new Timer();
		timer_wel.schedule(task_wel, 500, 2);		// start a 5s's timer after 2s
		//reDimUI(form_main);		//Menu and title's xml to band this And Show home page

		Tushuguan a=new Tushuguan();
		a.login("1301051618", "1301051618");
		a.get_borrwingdetail();
		a.renew_all();



//Networklogin_CMCC a =new Networklogin_CMCC();
//		a.login("1501060225", "960826wang");
//		a.cmcc_init();
//		//a.cmcc_geyanzheng("15762284638");
//		a.cmcc_login("15762284638","287157");
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
	
}
