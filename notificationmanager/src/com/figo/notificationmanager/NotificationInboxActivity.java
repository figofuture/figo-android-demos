package com.figo.notificationmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationInboxActivity extends ListActivity {

	private NotificationInboxAdapter mAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notification_inbox);
		
		mAdapter = new NotificationInboxAdapter(this);
		this.setListAdapter(mAdapter);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (null != mAdapter) {
			LNotification ln = mAdapter.getItem(position);
			if (null != ln) {
				switch (ln.getType()) {
				case LNotification.ACTIONS_TYPE_ACTIVITY:
					if (null != ln.getIntent())
						startActivity(ln.getIntent());
					break;
				case LNotification.ACTIONS_TYPE_SERVICE:
					if (null != ln.getIntent())
						startService(ln.getIntent());
					break;
				case LNotification.ACTIONS_TYPE_BROADCAST:
					if (null != ln.getIntent())
						sendBroadcast(ln.getIntent());
					break;
				}
			}
		}
	}

	public class NotificationInboxAdapter extends BaseAdapter {

		private Context mContext = null;
		private ViewHolder holder;
		private LayoutInflater mInflater;
		private LNotificationManager lnm = null;
		private ArrayList<LNotification> items = null;

		public NotificationInboxAdapter(Context ctx) {
			super();
			mContext = ctx;
			mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			items = new ArrayList<LNotification>();
			items.clear();
			lnm = LNotificationManager.getInstance(mContext);
			Iterator<?> iterator = lnm.getNotifications().keySet().iterator();
			while (iterator.hasNext()) {
				items.add(lnm.getNotifications().get(iterator.next()));
			}
			
			Collections.sort(items, new Comparator<LNotification>(){

				@Override
				public int compare(LNotification paramT1, LNotification paramT2) {
					return Long.valueOf(paramT2.getTimestamp()).compareTo(Long.valueOf(paramT1.getTimestamp()));
				}});
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public LNotification getItem(int arg0) {
			return items.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if ( null == convertView ) {
				convertView = mInflater.inflate(R.layout.notification_item,
						null);
				holder = new ViewHolder();
				holder.tvName = (TextView) convertView
						.findViewById(R.id.oh_item_name);
				holder.tvContent = (TextView) convertView
						.findViewById(R.id.oh_item_content);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.oh_item_date);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.oh_item_time);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			LNotification ln = items.get(position);
			if (null != ln) {
				holder.tvName.setText(ln.getTitleId());
				holder.tvContent.setText(ln.getMsgId());
				holder.tvDate.setText(DateFormat
						.getDateFormat(getApplicationContext())
						.format(new Date(ln.getTimestamp())).toString());
				holder.tvTime.setText(DateFormat
						.getTimeFormat(getApplicationContext())
						.format(new Date(ln.getTimestamp())).toString());
			}

			return convertView;
		}

	}

	static class ViewHolder {
		TextView tvName;
		TextView tvContent;
		TextView tvDate;
		TextView tvTime;
	}
}
