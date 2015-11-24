package com.jskz.ui.fragment;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.jskz.ui.activity.R;
import com.jskz.util.PoolRunnable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PoolFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

	private ConcurrentLinkedQueue<PoolRunnable> mTaskQueue = null;
	private ConcurrentMap<Future, PoolRunnable> mTaskMap = null;
	private ExecutorService mES = null;
	private Object mLock = new Object();
	private boolean isNotify = true;
	private boolean isRuning = true;
	
	private ProgressBar mProgressBar = null;
	private Handler mHandler = null;
	private TextView mFreshTV;
	private Button mBtn1;
	private Button mBtn2;
	private Button mBtn3;
	private Button mBtn4;
	private Button mBtn5;	
	private SwipeRefreshLayout mSettingSRL;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pool_fragment, container, false);
		initview(v);
		initListener(v);
		initData();
		return v;

	}

	private void initview(View v) {
		this.mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
		this.mBtn1 = (Button) v.findViewById(R.id.aci_testpost_btn);
		this.mFreshTV = (TextView) v.findViewById(R.id.aci_fresh_tv4);
		this.mSettingSRL = (SwipeRefreshLayout) v.findViewById(R.id.aci_swipe_container4);
	}

	private void initListener(View v) {
		mFreshTV.setOnClickListener(this);
		mBtn1.setOnClickListener(this);
		v.findViewById(R.id.aci_button2_btn).setOnClickListener(this);
		v.findViewById(R.id.aci_button3_btn).setOnClickListener(this);
		v.findViewById(R.id.aci_button4_btn).setOnClickListener(this);
		v.findViewById(R.id.aci_button5_btn).setOnClickListener(this);
		mSettingSRL.setColorSchemeResources(
				android.R.color.background_dark,
				android.R.color.background_light,
				android.R.color.background_dark,
				android.R.color.background_light);
		mSettingSRL.setOnRefreshListener(this);
	}

	private void initData() {
		mTaskQueue = new ConcurrentLinkedQueue<PoolRunnable>();
		mTaskMap = new ConcurrentHashMap<Future, PoolRunnable>();
		if (mES == null) {
			mES = Executors.newCachedThreadPool();
		}

		mHandler = new Handler() {
			/**
			 * Overriding methods
			 * 
			 * @param msg
			 */
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				mProgressBar.setProgress(msg.what);
			}

		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aci_testpost_btn:
			start();
			break;
		case R.id.aci_button2_btn:
			stop();
			break;
		case R.id.aci_button3_btn:
			reload(new PoolRunnable(mHandler));
			break;
		case R.id.aci_button4_btn:
			release();
			break;
		case R.id.aci_button5_btn:
			addTask(new PoolRunnable(mHandler));
			break;

		default:
			break;
		}
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
				release();
			}
		}, 3000);
	}
	
	private void addTask(final PoolRunnable mR) {

		mHandler.sendEmptyMessage(0);

		if (mES == null) {
			mES = Executors.newCachedThreadPool();
			notifyWork();
		}

		if (mTaskQueue == null) {
			mTaskQueue = new ConcurrentLinkedQueue<PoolRunnable>();
		}

		if (mTaskMap == null) {
			mTaskMap = new ConcurrentHashMap<Future, PoolRunnable>();
		}

		mES.execute(new Runnable() {

			@Override
			public void run() {
				mTaskQueue.offer(mR);
				// taskQueue.add(mR);
				notifyWork();
			}
		});

		Toast.makeText(getActivity(), "已添加一个新任务到线程池中 ！", 0).show();
	}

	private void release() {
		Toast.makeText(getActivity(), "释放所有占用的资源！", 0).show();

		mHandler.sendEmptyMessage(0);
		isRuning = false;

		Iterator iter = mTaskMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Future, PoolRunnable> entry = (Map.Entry<Future, PoolRunnable>) iter.next();
			Future result = entry.getKey();
			if (result == null) {
				continue;
			}
			result.cancel(true);
			mTaskMap.remove(result);
		}
		if (null != mES) {
			mES.shutdown();
		}

		mES = null;
		mTaskMap = null;
		mTaskQueue = null;

	}

	private void reload(final PoolRunnable mR) {
		mHandler.sendEmptyMessage(0);
		if (mES == null) {
			mES = Executors.newCachedThreadPool();
			notifyWork();
		}

		if (mTaskQueue == null) {
			mTaskQueue = new ConcurrentLinkedQueue<PoolRunnable>();
		}

		if (mTaskMap == null) {
			mTaskMap = new ConcurrentHashMap<Future, PoolRunnable>();
		}

		mES.execute(new Runnable() {

			@Override
			public void run() {
				mTaskQueue.offer(mR);
				// taskQueue.add(mR);
				notifyWork();
			}
		});

		mES.execute(new Runnable() {
			@Override
			public void run() {
				if (isRuning) {
					PoolRunnable mPoolRunnable = null;
					synchronized (mLock) {
						mPoolRunnable = mTaskQueue.poll(); 
						if (mPoolRunnable == null) {
							isNotify = true;
						}
					}

					if (mPoolRunnable != null) {
						mTaskMap.put(mES.submit(mPoolRunnable), mPoolRunnable);
					}
				}
			}
		});
	}

	private void stop() {

		Toast.makeText(getActivity(), "任务已被取消！", 0).show();

		for (PoolRunnable mPoolRunnable : mTaskMap.values()) {
			mPoolRunnable.setCancleTaskUnit(true);
		}
	}

	private void start() {

		if (mES == null || mTaskQueue == null || mTaskMap == null) {
			Log.i("KKK", "某资源是不是已经被释放了？");
			return;
		}
		mES.execute(new Runnable() {
			@Override
			public void run() {
				if (isRuning) {
					PoolRunnable mPoolRunnable = null;
					synchronized (mLock) {
						mPoolRunnable = mTaskQueue.poll(); 
						if (mPoolRunnable == null) {
							isNotify = true;
							// try
							// {
							// myRunnable.wait(500);
							// }
							// catch (InterruptedException e)
							// {
							// e.printStackTrace();
							// }
						}
					}

					if (mPoolRunnable != null) {
						mTaskMap.put(mES.submit(mPoolRunnable), mPoolRunnable);
					}
				}

			}
		});
	}

	private void notifyWork() {
		synchronized (mLock) {
			if (isNotify) {
				mLock.notifyAll();
				isNotify = !isNotify;
			}
		}
	}
}
