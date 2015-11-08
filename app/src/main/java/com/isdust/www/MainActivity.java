package com.isdust.www;

<<<<<<< HEAD
import android.content.Context;
=======
>>>>>>> origin/master
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.isdust.www.baseactivity.BaseMainActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseMainActivity {
	static boolean ishadopended = false;
	private Timer timer_wel = null;
	private boolean bool_wel = false;
	private View form_welcome;
	private MyApplication isdustapp1;

	private ImageView img_could;
	private Matrix mMatrix;

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


//		Http b=new Http();
//		String c=b.get_string("http://kzxs.isdust.com/chaxun.php?method=4&building=J1&zhoushu=9&xingqi=5&jieci=3");
//		Network_Kuaitong a=new Network_Kuaitong(this);
//		a.loginSmartCard("1501060238", "147147");
//		a.gaitaocan("1");
//		a.gaitaocan("2");
//		a.gaitaocan("3");
//		a.gaitaocan("4");
//		a.gaitaocan("5");
//		a.gaitaocan("6");
//		a.gaitaocan("7");
//		a.gaitaocan("8");
//		a.gaitaocan("9");
		isdustapp1=(MyApplication)getApplication();
		mContext=this;
		MobclickAgent.updateOnlineConfig(mContext);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);

//		Intent intent = new Intent();
//		intent.setClass(this, Card_login.class);
//		startActivityForResult(intent, 1);
//		Network_Kuaitong a=new Network_Kuaitong(this);
//		try {
//			a.loginSmartCard("1501060225","960826");
//			a.gaitaocan("11");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		if (ishadopended == true) {    //程序已经启动
			INIT(R.layout.activity_main, "首页");
//			img_could = (ImageView) findViewById(R.id.img_main_could);
///** 设置位移动画 向右位移150 */
//			final TranslateAnimation animation = new TranslateAnimation(0,-500,0, 0);
//			animation.setDuration(2000);//设置动画持续时间
//			animation.setRepeatCount(2);//设置重复次数
//			animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
//
//					img_could.setAnimation(animation);
///** 开始动画 */
//					animation.startNow();

/** 结束动画 */
//					animation.cancel();

//			img_could = (ImageView) findViewById(R.id.img_main_could);
//			mMatrix = new Matrix();
//			mMatrix.postScale(1.5f, 1.5f, 0.5f, 0.5f);
//			img_could.setImageMatrix(mMatrix);
//			img_could.invalidate();
//			img_could.startAnimation(getAnimation());
		}else {
			INIT(R.layout.activity_main, "首页");

//			ishadopended = true;
//			LayoutInflater inflate = LayoutInflater.from(this);
//			form_welcome = inflate.inflate(R.layout.welcome,null);
//			setContentView(form_welcome);		//Show welcome page
//			//next add some load event
//			timer_wel = new Timer();
//			timer_wel.schedule(task_wel, 2000, 2);		// start a 5s's timer after 2s

		}

//		Library a=new Library();
//		String b=a.login("1501060225","960826wang");

	}

//	public void onFormMainClick(View v) {
//		switch (v.getId()) {
//			case R.id.btn_main_set:  //设置
//				break;
//			case R.id.btn_main_about:  //关于
//				break;
//			case R.id.btn_main_gonet:  //上网登录
//				break;
//			case R.id.btn_main_kuaitong:  //快通有线
//				break;
//			case R.id.btn_main_schedule:  //查课表
//				break;
//			case R.id.btn_main_emptyroom:  //空自习室
//				break;
//			case R.id.btn_main_library:  //图书馆
//				break;
//			case R.id.btn_main_card:  //校园卡
//				break;
//			case R.id.btn_main_map:  //校园地图
//				break;
//			case R.id.btn_main_news:  //校园资讯
//				break;
//		}
//	}
	private Animation getAnimation() {
		// TranslateAnimation translateAnimation = new TranslateAnimation(0.0f,
		// 200f, 0.0f,
		// 0.0f);
		// translateAnimation.setDuration(5000);
		// translateAnimation.setRepeatMode(Animation.REVERSE);
		// translateAnimation.setRepeatCount(Animation.INFINITE);
		// return translateAnimation;
		MAnimation animation = new MAnimation();
		animation.setDuration(5000);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setRepeatCount(Animation.INFINITE);
		return animation;
	}
	public class MAnimation extends Animation {
		private float pre = 0.0f;
		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			System.out.println(interpolatedTime);
			if (pre < interpolatedTime) {
				pre = interpolatedTime;
				mMatrix.postTranslate(-interpolatedTime * 3, 0);
			} else {
				pre = interpolatedTime;
				mMatrix.postTranslate(interpolatedTime * 3, 0);
			}
//			 mMatrix.postScale(interpolatedTime, 1);
			img_could.setImageMatrix(mMatrix);
			img_could.invalidate();
		}
	}
	public void onFormMainClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
			case R.id.btn_main_gonet:
				intent.setClass(this, GoNetActivity.class);//上网登录
				break;
			case R.id.btn_main_kuaitong:
				intent.setClass(this, KuaiTongActivity.class);//快通查询
				break;
			case R.id.btn_main_schedule:
				intent.setClass(this, ScheduleActivity.class);//课程表
				break;
			case R.id.btn_main_emptyroom:
				intent.setClass(this, EmptyRoomActivity.class);//空自习室
				break;
			case R.id.btn_main_library:
				intent.setClass(this, LibraryActivity.class);//图书馆
				break;
			case R.id.btn_main_card:
				intent.setClass(this, CardActivity.class);//校园卡
				break;
			case R.id.btn_main_map:
				intent.setClass(this, MapchooseActivity.class);//地图服务
				break;
			case R.id.btn_main_set:
				intent.setClass(this, SetActivity.class);//设置
				break;
			case R.id.btn_main_news:
				intent.setClass(this, NewsActivity.class);//咨询
				break;
			case R.id.btn_main_about:
				intent.setClass(this, AboutActivity.class);//关于
				break;
		}
		this.startActivity(intent);



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

}
