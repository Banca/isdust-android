package com.formal.sdusthelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;

import java.util.Timer;
import java.util.TimerTask;

import pw.isdust.isdust.function.SelectCoursePlatform;


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
		timer_wel.schedule(task_wel, 2000, 2);		// start a 5s's timer after 2s
		//reDimUI(form_main);		//Menu and title's xml to band this And Show home page


		SelectCoursePlatform a=new SelectCoursePlatform();
		a.login_zhengfang("201501060225","960826wang");



//		Network_Kuaitong a =new Network_Kuaitong();
//		String b=a.kuaitong_chongzhi_login("1501060238","147147");
//		a.kuaitong_chongzhi("0.01");

		//a.kuaitong_getinfo();



//		writeToFile("wzq");
//		String a=readFromFile();
//		a="";






//		Intent a=new Intent()
//		this.setClass(this, JiaowuActivity.class);
//		SelectCoursePlatform b=new SelectCoursePlatform();
//		Kebiao[] kebiao=new Kebiao[3];
//		kebiao[0]=new Kebiao();
//		kebiao[0].zhoushu ="1";
//		kebiao[0].xingqi="3";
//		kebiao[0].jieci ="2";
//		kebiao[0].kecheng="wzq";
//
//		kebiao[1]=new Kebiao();
//		kebiao[1].zhoushu ="1";
//		kebiao[1].xingqi="3";
//		kebiao[1].jieci ="2";
//		kebiao[1].kecheng="wzq";
//		kebiao[2]=new Kebiao();
//		kebiao[2].zhoushu ="1";
//		kebiao[2].xingqi="3";
//		kebiao[2].jieci ="2";
//		kebiao[2].kecheng="wzq";
//		b.scheduletojson(kebiao);


//		Tushuguan a=new Tushuguan();
//		a.json_analyze();
//		a.login("1301051618", "1301051618");
//		a.get_borrwingdetail();
//		a.renew_all();
//		try{
//			a.xml_analyze();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


//Networklogin_CMCC a =new Networklogin_CMCC();
//		a.login("1501060225", "960826wang");
//		a.cmcc_init();
//		//a.cmcc_geyanzheng("15762284638");
//		a.cmcc_login("15762284638","287157");
//		Kongzixishi a=new Kongzixishi();
//		a.getEmptyClassroom("J7-310室",5);

//		Xiaoyuanka b=new Xiaoyuanka(this);
//		String c= b.login("1501060225", "960826");
		//c=b.changepassword("061406","061406","370112199606264517");
		//b.guashi("960826");
//		Xiaoli b=new Xiaoli();
//
//		Xuankepingtai a=new Xuankepingtai();
//		a.login("201501060225","960826wang");
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
