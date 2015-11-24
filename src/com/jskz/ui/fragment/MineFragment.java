package com.jskz.ui.fragment;

import com.jskz.ui.activity.R;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MineFragment extends Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
	
	private static final String TAG = "MineFragment";
	
	private Button mDownloadBtn;
    private ProgressBar mProgressBar;
    private TextView mShowTV;
    private TextView mFreshTV;
	private SwipeRefreshLayout mSettingSRL;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.mine_fragment, container, false);
		initView(v);
		initListener();
		return v;
	}
	
	private void initView(View v) {
		
		this.mProgressBar=(ProgressBar) v.findViewById(R.id.aci_bar_pb);
        this.mShowTV=(TextView) v.findViewById(R.id.aci_show_tv);
        this.mFreshTV = (TextView) v.findViewById(R.id.aci_fresh_tv3);
        this.mDownloadBtn = (Button) v.findViewById(R.id.aci_download_btn);
		this.mSettingSRL = (SwipeRefreshLayout) v.findViewById(R.id.aci_swipe_container3);
        
	}
	
	private void initListener() {
		mDownloadBtn.setOnClickListener(this);
		mFreshTV.setOnClickListener(this);
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
				mSettingSRL.setBackgroundColor(Color.BLUE);
			}
		}, 3000);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e(TAG, "onResume()");
	}
	
	
	class DownloadTask extends AsyncTask<Integer, Integer, String>{
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
        @Override
        protected String doInBackground(Integer... params) {
            for(int i=0; i<=100; i++){
                mProgressBar.setProgress(i);
                publishProgress(i);
                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            mShowTV.setText(progress[0] + "%");
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
        
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aci_download_btn:
			DownloadTask dTask = new DownloadTask();
            dTask.execute(100);
			break;
		}
	}
}