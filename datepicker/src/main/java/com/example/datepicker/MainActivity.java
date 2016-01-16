package com.example.datepicker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity {
	Calendar dateandtime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dateandtime = Calendar.getInstance(Locale.US);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(final View arg0) {
			
				DatePickerDailog dp = new DatePickerDailog(MainActivity.this,
						 new DatePickerDailog.DatePickerListner() {

							@Override
							public void OnDoneButton(Dialog datedialog,int a,int b ) {

								datedialog.dismiss();

							}

							@Override
							public void OnCancelButton(Dialog datedialog) {
								// TODO Auto-generated method stub
								datedialog.dismiss();
							}
						});
				dp.show();
				
				
				
				
			}
		});
	}

	
}
