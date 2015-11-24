package com.jskz.ui.fragment;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jskz.model.DataBean;
import com.jskz.model.WordBean;
import com.jskz.ui.activity.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.jskz.adapter.SettingFragmentAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SettingFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

	private static final String TAG = SettingFragment.class.getSimpleName();
	protected boolean isCreated = false; 
	
	private Button mSearchBtn;
	private EditText mPointET;
	private ListView mListLV;
	private TextView mFreshTV;
	private SwipeRefreshLayout mSettingSRL;

	private String mBaiduurl = "http://apis.baidu.com/netpopo/idiom/chengyu";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        isCreated = true;
    }

    // TODO Auto-generated method stub
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.setting_fragment, container, false);
        initView(v);
		initListener();
		initData();
		return v;
    }

    private void initData() {
	}

	private void initView(View v) {

		this.mPointET = (EditText) v.findViewById(R.id.aci_point_et);
		this.mSearchBtn = (Button) v.findViewById(R.id.aci_search_btn);
		this.mListLV = (ListView) v.findViewById(R.id.aci_list_lv);
		this.mFreshTV = (TextView) v.findViewById(R.id.aci_fresh_tv2);
		this.mSettingSRL = (SwipeRefreshLayout) v.findViewById(R.id.aci_swipe_container2);
	}

	private void initListener() {

		mPointET.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);
		mFreshTV.setOnClickListener(this);
		mSettingSRL.setColorSchemeResources(
				android.R.color.background_dark,
				android.R.color.background_light,
				android.R.color.background_dark,
				android.R.color.background_light);
		mSettingSRL.setOnRefreshListener(this);

	}

	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	Log.i(TAG, "onActivityCreated");
    	super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.i(TAG, "onResume");
    	super.onResume();
    }

    @Override
    public void onPause() {
    	Log.i(TAG, "onPause");
    	super.onPause();
    }
    
    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
    	Log.i(TAG, "onDestroyView");
    	super.onDestroyView();
    }
    
    @Override
    public void onDestroy() {
    	Log.i(TAG, "onDestroy");
    	super.onDestroy();
    }
    
    @Override
    public void onDetach() {
    	Log.i(TAG, "onDetach");
    	super.onDetach();
    }

    @Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aci_search_btn:
			SearchWord();
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
				SearchWord();
			}
		}, 3000);
	}

	public void SearchWord() {
		String url = mBaiduurl + "?keyword=" + mPointET.getText().toString()
					 + "&appkey=1307ee261de8bbcf83830de89caae73f";
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addHeader("apikey", "6c36e1ebba98b1c157d34cfe81c5ef3e");
		http.send(HttpRequest.HttpMethod.GET, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String mData = responseInfo.result.toString();
						Log.e("Success", mData);

						WordBean wordBean = JSON.parseObject(mData, WordBean.class);
						List<DataBean> mList = wordBean.getData();
						for (int i = 0; i < (mList.size()); i++) {
							mList.get(i).setType(i % 2 == 0 ? 0 : 1);
							/*
							 * if(i % 2 == 0) { list.get(i).setType(0); } else {
							 * list.get(i).setType(1); }
							 */
						}
						final SettingFragmentAdapter mSettingAdapter = new SettingFragmentAdapter(getActivity(), mList);
						mListLV.setAdapter(mSettingAdapter);
						mListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								mSettingAdapter.setCurrentItem(position);
								mSettingAdapter.notifyDataSetChanged();
							}
						});
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Log.e("Failure", arg0.getMessage() + " " + arg1);
					}
				});

	}
}
