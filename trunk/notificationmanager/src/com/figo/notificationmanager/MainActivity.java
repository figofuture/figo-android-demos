package com.figo.notificationmanager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Intent intent = new Intent(MainActivity.this,
						ResultActivity.class);
				LNotification ln = new LNotification("一个新的系统消息", "请点击我查看",
						R.drawable.notification_icon, intent, System
								.currentTimeMillis(), 0);
				LNotificationManager lmn = LNotificationManager
						.getInstance(getApplicationContext());
				lmn.add("888", ln);
			}
		});

		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Intent intent = new Intent(MainActivity.this,
						ResultActivity2.class);
				LNotification ln = new LNotification("一个新的用户消息", "请点击我查看",
						R.drawable.notification_icon2, intent, System
								.currentTimeMillis(), 0);
				LNotificationManager lmn = LNotificationManager
						.getInstance(getApplicationContext());
				lmn.add("999", ln);
			}
		});

		Button btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Intent intent = new Intent(MainActivity.this,
						ResultActivity3.class);
				LNotification ln = new LNotification("一个新的外部消息", "请点击我查看",
						R.drawable.notification_icon3, intent, System
								.currentTimeMillis(), 0);
				LNotificationManager lmn = LNotificationManager
						.getInstance(getApplicationContext());
				lmn.add("111", ln);
			}
		});
		
		Button btn4 = (Button) findViewById(R.id.button4);
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				Intent intent = new Intent(MainActivity.this,
						ResultActivity.class);
				LNotification ln = new LNotification("一个新的系统消息", "请点击我查看",
						R.drawable.notification_icon, intent, System
								.currentTimeMillis(), 0);
				LNotificationManager lmn = LNotificationManager
						.getInstance(getApplicationContext());
				lmn.add("888", ln);
				
				 intent = new Intent(MainActivity.this,
							ResultActivity2.class);
					ln = new LNotification("一个新的用户消息", "请点击我查看",
							R.drawable.notification_icon2, intent, System
									.currentTimeMillis(), 0);
					lmn = LNotificationManager
							.getInstance(getApplicationContext());
					lmn.add("999", ln);
					
					intent = new Intent(MainActivity.this,
							ResultActivity3.class);
					ln = new LNotification("一个新的外部消息", "请点击我查看",
							R.drawable.notification_icon3, intent, System
									.currentTimeMillis(), 0);
					lmn = LNotificationManager
							.getInstance(getApplicationContext());
					lmn.add("111", ln);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
