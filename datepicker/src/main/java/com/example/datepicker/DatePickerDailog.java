package com.example.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.wheel.ArrayWheelAdapter;
import com.wheel.NumericWheelAdapter;
import com.wheel.OnWheelChangedListener;
import com.wheel.WheelView;

import java.util.Calendar;

public class DatePickerDailog extends Dialog {

	private Context Mcontex;
	public static int xingqi_num,jieci_num;

	private int NoOfYear = 40; 
	
	public DatePickerDailog(Context context,
			final DatePickerListner dtp) {

		super(context);
		Mcontex = context;
		LinearLayout lytmain = new LinearLayout(Mcontex);
		lytmain.setOrientation(LinearLayout.VERTICAL);
		LinearLayout lytdate = new LinearLayout(Mcontex);
		LinearLayout lytbutton = new LinearLayout(Mcontex);
		LinearLayout lytextview=new LinearLayout(Mcontex);

		TextView textView_title=new TextView(Mcontex);
		textView_title.setText("选择上课节次");
		textView_title.setTextColor(0xFF686868);
		textView_title.setTextSize(18);
		textView_title.setGravity(Gravity.CENTER);


		Button btnset = new Button(Mcontex);
		btnset.setTextColor(0xFF3E95DE);
		btnset.setBackgroundColor(0xFFF6F6F6);

		//btnset.divi
		Button btncancel = new Button(Mcontex);
		btncancel.setTextColor(0xFF3E95DE);
		btncancel.setBackgroundColor(0xFFF6F6F6);
		btnset.setText("确定");
		btncancel.setText("取消");

		final WheelView jieci = new WheelView(Mcontex);
		final WheelView week = new WheelView(Mcontex);
//		final WheelView day = new WheelView(Mcontex);
		xingqi_num=1;jieci_num=1;
		lytdate.addView(week, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
		lytdate.addView(jieci, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f));

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		lytbutton.addView(btnset, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

		lytbutton.addView(btncancel, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
		lytextview.addView(textView_title, new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//		lytbutton.setPadding(2, 2, 5, 5);
		lytmain.addView(lytextview);
		lytmain.addView(lytdate);
		lytmain.addView(lytbutton);

		setContentView(lytmain);

		getWindow().setLayout(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (wheel==jieci){
					System.out.println("节次"+newValue);
					jieci_num=1+newValue;
				}
				if (wheel==week){
					xingqi_num=1+newValue;
					System.out.println("星期"+newValue);
				}


			}
		};

		// month
		int curjieci = 0;
		String jieci_content[] = new String[] { "第一、二节课", "第三、四节课", "第五、六节课",
				"第七、八节课", "第九、十节课" };
		jieci.setViewAdapter(new DateArrayAdapter(context, jieci_content, curjieci));
		jieci.setCurrentItem(curjieci);
		jieci.addChangingListener(listener);

		Calendar cal = Calendar.getInstance();
		// year
		String weeks_content[] = new String[] { "星期一", "星期二", "星期三",
				"星期四", "星期五", "星期六", "星期日"};

		int curweek = 0;

		week.setViewAdapter(new DateArrayAdapter(context, weeks_content, curweek));
		week.setCurrentItem(curweek);
		week.addChangingListener(listener);

		// day
//		updateDays(week, jieci);
//		day.setCurrentItem(1);

		btnset.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {
				dtp.OnDoneButton(DatePickerDailog.this,xingqi_num,jieci_num);

			}
		});


		btncancel.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {
				dtp.OnCancelButton(DatePickerDailog.this);

			}
		});

	}

//	Calendar updateDays(WheelView year, WheelView month) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.YEAR,
//				calendar.get(Calendar.YEAR) + (year.getCurrentItem()-NoOfYear));
//		calendar.set(Calendar.MONTH, month.getCurrentItem());
//
//
//		return calendar;
//
//	}

	private class DateNumericAdapter extends NumericWheelAdapter {
		int currentItem;
		int currentValue;

		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(20);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				//view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(null, Typeface.BOLD);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		int currentItem;
		int currentValue;

		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(20);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				//view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(null, Typeface.BOLD);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	public interface DatePickerListner {
//		 int return_xingqi=xingqi_num;
//		 int return_jieci=jieci_num;

		//return_jieci=1;

		public void OnDoneButton(Dialog datedialog,int return_xingqi,int return_jieci);

		public void OnCancelButton(Dialog datedialog);
	}
}
