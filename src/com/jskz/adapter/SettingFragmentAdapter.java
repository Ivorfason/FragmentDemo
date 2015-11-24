package com.jskz.adapter;

import java.util.List;

import com.jskz.model.DataBean;
import com.jskz.ui.activity.R;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SettingFragmentAdapter extends BaseAdapter {

	private List<DataBean> data;
	private Context context;
	private LayoutInflater mInflater;
	private int mCurrentItem = 10000;
	public static final int TYPE_1 = 0;
	public static final int TYPE_2 = 1;
	private String mJoke = "<font color=red>大哥！</font><font color=blue>不要点我啊</font>";
	private String mBoy = "我是聪明的小帅哥！";

	public SettingFragmentAdapter(Context context, List<DataBean> data) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data==null) return 0;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setCurrentItem(int currentItem) {
		this.mCurrentItem = currentItem;
	}

	public static final class ViewHolderType1 {
		public TextView mSayingTV;
	}
	
	public static final class ViewHolderType2 {
		public TextView mSpeakingTV;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// int type = getItemViewType(position);
		ViewHolderType1 holderType1 = null;
		ViewHolderType2 holderType2 = null;
		Log.v("SettingFragmentAdapter", "getView " + position + " " + convertView);
		final DataBean item = data.get(position);
		int type = item.getType();
		if (convertView == null) {
			switch (type) {
			case TYPE_1:
				convertView = mInflater.inflate(R.layout.setting_item, null);
				holderType1 = new ViewHolderType1();
				holderType1.mSayingTV = (TextView) convertView
						.findViewById(R.id.aci_item_itemtext);
				convertView.setTag(holderType1);
				break;
			case TYPE_2:
				convertView = mInflater.inflate(R.layout.setting_item1, null);
				holderType2 = new ViewHolderType2();
				holderType2.mSpeakingTV = (TextView) convertView
						.findViewById(R.id.aci_item1_itemtext);
				convertView.setTag(holderType2);
				break;
			}
		}else {
			switch (type) {
			case TYPE_1:
				holderType1=(ViewHolderType1)convertView.getTag();
				break;
			case TYPE_2:
				holderType2=(ViewHolderType2)convertView.getTag();
				break;
			}
		}

		if (mCurrentItem == position) {
			switch (type) {
			case TYPE_1:
				holderType1.mSayingTV.setText(Html.fromHtml(mJoke));
				holderType1.mSayingTV.setGravity(Gravity.CENTER);
				break;
			case TYPE_2:
				holderType2.mSpeakingTV.setText(Html.fromHtml(mJoke));
				holderType2.mSpeakingTV.setGravity(Gravity.CENTER);
				break;
			}
		} else {
			switch (type) {
			case TYPE_1:
				holderType1.mSayingTV.setText(data.get(position).getName().toString()
										   + " (" + data.get(position).getPronounce().toString() + ")"
										   + "\n" + data.get(position).getContent().toString());
				holderType1.mSayingTV.setGravity(Gravity.LEFT);
				holderType1.mSayingTV.setLines(2);
				holderType1.mSayingTV.setEllipsize(TruncateAt.END);
				break;
			case TYPE_2:
				holderType2.mSpeakingTV.setText(mBoy);
				holderType2.mSpeakingTV.setGravity(Gravity.LEFT);
				break;
			}
		}
		
		return convertView;
	}
	
	@Override
	public int getItemViewType(int position) {

		DataBean dataBean = data.get(position);
		int type = dataBean.getType();
		return type;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

}
