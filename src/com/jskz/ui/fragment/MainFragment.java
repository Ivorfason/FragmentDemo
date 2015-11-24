package com.jskz.ui.fragment;

import com.jskz.ui.activity.R;
import com.jskz.ui.custom.DemoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
	private TextView mFreshTV;
	private Button mJumpBtn;
	private SwipeRefreshLayout mSettingSRL;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main_fragment, container, false);
		initView(v);
		initListener();
		return v;
	}

	private void initView(View v) {
		this.mFreshTV = (TextView) v.findViewById(R.id.aci_fresh_tv1);
		this.mJumpBtn = (Button) v.findViewById(R.id.aci_jump_btn);
		this.mSettingSRL = (SwipeRefreshLayout) v.findViewById(R.id.aci_swipe_container1);		
	}
	
	private void initListener() {
		mFreshTV.setOnClickListener(this);
		mJumpBtn.setOnClickListener(this);
		mSettingSRL.setColorSchemeResources(
				android.R.color.background_dark,
				android.R.color.background_light,
				android.R.color.background_dark,
				android.R.color.background_light);
		mSettingSRL.setOnRefreshListener(this);
		
	}

	@Override
	public void onRefresh() {
		mFreshTV.setText("正在刷新");
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mFreshTV.setText("刷新完成");
				mSettingSRL.setRefreshing(false);
				mJumpBtn.setText("快点我啊！");
			}
		}, 3000);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aci_jump_btn:
			Intent intent = new Intent();
			intent.setClass(getActivity(),DemoActivity.class);
			getActivity().startActivity(intent);
		}
	}
	
}
