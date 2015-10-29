package com.formal.sdusthelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.formal.sdusthelper.baseactivity.BaseMainActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseMainActivity {
	static boolean ishadopended = false;
	private Timer timer_wel = null;
	private boolean bool_wel = false;
	private View form_welcome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (ishadopended == true)	//程序已经启动
			INIT(R.layout.activity_main,"首页");
		else {
			ishadopended = true;
			LayoutInflater inflate = LayoutInflater.from(this);
			form_welcome = inflate.inflate(R.layout.welcome,null);
			setContentView(form_welcome);		//Show welcome page
			//next add some load event
			timer_wel = new Timer();
			timer_wel.schedule(task_wel, 2000, 2);		// start a 5s's timer after 2s
		}

		//Match_lib.getBook("{\"loanWorkMap\":{\"0197099\":{\"logType\":null,\"rdid\":\"201403202320\",\"loanDate\":1445764846000,\"returnDate\":1453541470376,\"regTime\":null,\"year\":null,\"serNo\":null,\"holding\":null,\"biblios\":null,\"barcode\":\"0197099\",\"ruleState\":1,\"loanCount\":0,\"dueTime\":1445764846000,\"attachMent\":0,\"barcodeList\":null,\"returnDateInStr\":null,\"loanDateInStr\":null,\"regTimeInStr\":null,\"rowid\":null,\"ruleno\":null,\"underlease\":false,\"rowidList\":null},\"2979902\":{\"logType\":null,\"rdid\":\"1401061430\",\"loanDate\":1437525658000,\"returnDate\":1447894276554,\"regTime\":null,\"year\":null,\"serNo\":null,\"holding\":null,\"biblios\":null,\"barcode\":\"2979902\",\"ruleState\":1,\"loanCount\":1,\"dueTime\":1437525658000,\"attachMent\":0,\"barcodeList\":null,\"returnDateInStr\":null,\"loanDateInStr\":null,\"regTimeInStr\":null,\"rowid\":null,\"ruleno\":null,\"underlease\":false,\"rowidList\":null},\"2979903\":{\"logType\":null,\"rdid\":\"1001101132\",\"loanDate\":1442387163000,\"returnDate\":1450163183129,\"regTime\":null,\"year\":null,\"serNo\":null,\"holding\":null,\"biblios\":null,\"barcode\":\"2979903\",\"ruleState\":1,\"loanCount\":0,\"dueTime\":1442387163000,\"attachMent\":0,\"barcodeList\":null,\"returnDateInStr\":null,\"loanDateInStr\":null,\"regTimeInStr\":null,\"rowid\":null,\"ruleno\":null,\"underlease\":false,\"rowidList\":null}},\"holdingList\":[{\"recno\":2309729,\"bookrecno\":1900453720,\"state\":2,\"barcode\":\"2979901\",\"callno\":\"TP311.56/404\",\"orglib\":\"01000\",\"orglocal\":\"QZYK\",\"curlib\":\"01000\",\"curlocal\":\"QZYK\",\"cirtype\":\"001\",\"regdate\":\"2013-03-25\",\"indate\":\"2013-03-15\",\"singlePrice\":59.0,\"totalPrice\":59.0,\"totalLoanNum\":0,\"totalResNum\":0,\"totalRenewNum\":0,\"totalLibNum\":0,\"volnum\":1,\"volInfo\":null,\"memoinfo\":null,\"shelfno\":null,\"regno\":null,\"biblios\":null,\"loan\":null,\"packageno\":null,\"stateStr\":null},{\"recno\":2556718,\"bookrecno\":1900453720,\"state\":3,\"barcode\":\"0197099\",\"callno\":\"TP311.56/168\",\"orglib\":\"03000\",\"orglocal\":\"JNZK\",\"curlib\":\"03000\",\"curlocal\":\"JNZK\",\"cirtype\":\"001\",\"regdate\":\"2014-04-03\",\"indate\":\"2014-04-03\",\"singlePrice\":59.0,\"totalPrice\":59.0,\"totalLoanNum\":2,\"totalResNum\":0,\"totalRenewNum\":0,\"totalLibNum\":0,\"volnum\":1,\"volInfo\":null,\"memoinfo\":null,\"shelfno\":null,\"regno\":null,\"biblios\":null,\"loan\":null,\"packageno\":null,\"stateStr\":null},{\"recno\":2309731,\"bookrecno\":1900453720,\"state\":3,\"barcode\":\"2979903\",\"callno\":\"TP311.56/404\",\"orglib\":\"01000\",\"orglocal\":\"QZKK\",\"curlib\":\"01000 \",\"curlocal\":\"Q007\",\"cirtype\":\"001\",\"regdate\":\"2013-03-25\",\"indate\":\"2013-03-15\",\"singlePrice\":59.0,\"totalPrice\":59.0,\"totalLoanNum\":11,\"totalResNum\":0,\"totalRenewNum\":2,\"totalLibNum\":0,\"volnum\":1,\"volInfo\":null,\"memoinfo\":null,\"shelfno\":null,\"regno\":null,\"biblios\":null,\"loan\":null,\"packageno\":null,\"stateStr\":null},{\"recno\":2309730,\"bookrecno\":1900453720,\"state\":3,\"barcode\":\"2979902\",\"callno\":\"TP311.56/404\",\"orglib\":\"01000\",\"orglocal\":\"QZKK\",\"curlib\":\"01000 \",\"curlocal\":\"Q007\",\"cirtype\":\"001\",\"regdate\":\"2013-03-25\",\"indate\":\"2013-03-15\",\"singlePrice\":59.0,\"totalPrice\":59.0,\"totalLoanNum\":8,\"totalResNum\":0,\"totalRenewNum\":1,\"totalLibNum\":0,\"volnum\":1,\"volInfo\":null,\"memoinfo\":null,\"shelfno\":null,\"regno\":null,\"biblios\":null,\"loan\":null,\"packageno\":null,\"stateStr\":null}],\"holdStateMap\":{\"0\":{\"stateType\":0,\"stateName\":\"流通还回上架中\"},\"1\":{\"stateType\":1,\"stateName\":\"编目\"},\"2\":{\"stateType\":2,\"stateName\":\"在馆\"},\"3\":{\"stateType\":3,\"stateName\":\"借出\"},\"4\":{\"stateType\":4,\"stateName\":\"丢失\"},\"5\":{\"stateType\":5,\"stateName\":\"剔除\"},\"6\":{\"stateType\":6,\"stateName\":\"交换\"},\"7\":{\"stateType\":7,\"stateName\":\"赠送\"},\"8\":{\"stateType\":8,\"stateName\":\"装订\"},\"9\":{\"stateType\":9,\"stateName\":\"锁定\"},\"10\":{\"stateType\":10,\"stateName\":\"预借\"},\"12\":{\"stateType\":12,\"stateName\":\"清点\"},\"13\":{\"stateType\":13,\"stateName\":\"闭架\"},\"14\":{\"stateType\":14,\"stateName\":\"修补\"},\"15\":{\"stateType\":15,\"stateName\":\"查找中\"}},\"barcodeLocationUrlMap\":{\"02000\":\"\",\"T\":\"\",\"04000\":\"\",\"Q\":\"\",\"01000\":\"\",\"03000\":\"\",\"X\":\"\",\"J\":\"\",\"999\":\"\",\"05000\":\"\"},\"libcodeDeferDateMap\":{\"02000\":1,\"04000\":1,\"01000\":1,\"03000\":3,\"999\":1,\"05000\":1},\"pBCtypeMap\":{\"008\":{\"cirtype\":\"008\",\"libcode\":\"999\",\"name\":\"韩文图书\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"010\":{\"cirtype\":\"010\",\"libcode\":\"01000\",\"name\":\"法文图书\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"009\":{\"cirtype\":\"009\",\"libcode\":\"999\",\"name\":\"学位论文\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"004\":{\"cirtype\":\"004\",\"libcode\":\"999\",\"name\":\"外文期刊\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"005\":{\"cirtype\":\"005\",\"libcode\":\"999\",\"name\":\"非书资料\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"006\":{\"cirtype\":\"006\",\"libcode\":\"999\",\"name\":\"日文图书\",\"descripe\":\"日文图书\",\"loanNumSign\":0,\"isPreviService\":0},\"011\":{\"cirtype\":\"011\",\"libcode\":\"03000\",\"name\":\"复印A4\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"007\":{\"cirtype\":\"007\",\"libcode\":\"999\",\"name\":\"俄文图书\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"001\":{\"cirtype\":\"001\",\"libcode\":\"999\",\"name\":\"中文图书\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"002\":{\"cirtype\":\"002\",\"libcode\":\"999\",\"name\":\"英文图书\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0},\"003\":{\"cirtype\":\"003\",\"libcode\":\"999\",\"name\":\"中文期刊\",\"descripe\":null,\"loanNumSign\":0,\"isPreviService\":0}},\"localMap\":{\"QBCK\":\"青岛科图版书库\",\"JNJC\":\"济南教材参考库\",\"QXKYL\":\"青岛现刊阅览室\",\"QZKK\":\"青岛自科书库\",\"QWYK\":\"青岛外文书样本库\",\"QWWK\":\"青岛外文书库\",\"TDZKK\":\"泰东自科现刊\",\"QZYK\":\"青岛中文书样本库\",\"TDYB\":\"泰东样本库\",\"KJGK\":\"泰西过刊\",\"JNGK\":\"中文期刊\",\"JNWWK\":\"济南外文刊\",\"TDZT\":\"泰东中图库\",\"TDSKK\":\"泰东社科现刊\",\"JNGP\":\"济南随书光盘库\",\"QSKK\":\"青岛社科书库\",\"JNQK\":\"济南期刊库\",\"JNSK\":\"济南社科借阅区\",\"QJCK\":\"青岛教材样本库\",\"TDKT\":\"泰东科图库\",\"KJZKK\":\"泰西自科现刊\",\"QMJK\":\"青岛密集库\",\"QDZY\":\"青岛电子阅览室\",\"TDGK\":\"泰东过刊库\",\"TDXS\":\"泰东学生阅览室\",\"JNGJ\":\"济南工具书\",\"TDKY\":\"泰东考研库\",\"QGKK\":\"青岛过期期刊库\",\"Q007\":\"青岛未分配流通库\",\"JNXS\":\"济南学生借书处\",\"JNBC\":\"济南保存库\",\"TDWW\":\"泰东外文库\",\"TDZH\":\"泰东综合库\",\"KJTC\":\"特藏图书\",\"JNZK\":\"济南自科借阅区\",\"JNFY\":\"济南复印\",\"TDTC\":\"泰东特藏库\",\"KJZT\":\"泰西中图库\",\"TDZLS\":\"泰文法资料室\",\"JNWW\":\"济南外文借书处\",\"WFFG\":\"文法分馆\",\"QGJK\":\"青岛工具书库\",\"JNLS\":\"济南临时库\",\"QTCK\":\"青岛特藏书库\",\"KJSKK\":\"泰西社科现刊\",\"JNJS\":\"济南教师借书处\",\"JNDZ\":\"济南电子阅览室\",\"TDJS\":\"泰东教师阅览室\"},\"libcodeMap\":{\"02000\":\"泰安东校区\",\"04000\":\"泰山科技学院\",\"01000\":\"青岛校区\",\"01000 \":null,\"03000\":\"济南校区\",\"999\":\"山东科技大学图书馆\",\"05000\":\"文法分馆\"}}");




//		SelectCoursePlatform a=new SelectCoursePlatform();




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
