package com.figo.testsecretcode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SecretCodeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent i) {
		  Intent intent=new Intent(context,SecretActivity.class);
		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		  context.startActivity(intent);
	}

}
