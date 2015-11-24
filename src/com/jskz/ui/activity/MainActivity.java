package com.jskz.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.jskz.ui.fragment.MainFragment;
import com.jskz.ui.fragment.MineFragment;
import com.jskz.ui.fragment.PoolFragment;
import com.jskz.ui.fragment.SettingFragment;

public class MainActivity extends FragmentActivity {

	private Button mMainBtn;
	private Button mSettingBtn;
	private Button mMineBtn;
	private Button mPoolBtn;
	
	private FragmentManager mManager;
	private Fragment mMainFragment;
	private Fragment mSettingFragment;
	private Fragment mMineFragment;
	private Fragment mPoolFragment;
	
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		initView();
	}

	private void initView() {
		getWindow().setFlags(0x08000000, 0x08000000);
		this.mMainBtn = (Button) findViewById(R.id.aci_main_btn);
		this.mSettingBtn = (Button) findViewById(R.id.aci_setting_btn);
		this.mMineBtn = (Button) findViewById(R.id.aci_mine_btn);
		this.mPoolBtn = (Button) findViewById(R.id.aci_pool_btn);
		
		mManager = getSupportFragmentManager();

		mMainFragment = new MainFragment();
		mSettingFragment = new SettingFragment();
		mMineFragment = new MineFragment();
		mPoolFragment = new PoolFragment();

		FragmentTransaction trans0 = mManager.beginTransaction();
		trans0.add(R.id.aci_container_fl, mMainFragment).commit();
	}

	// TODO Auto-generated method stub ：事务管理器！

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aci_main_btn:
			FragmentTransaction trans1 = mManager.beginTransaction();
			trans1.replace(R.id.aci_container_fl, mMainFragment);
			trans1.commit();
			mMainBtn.setSelected(true);
			mSettingBtn.setSelected(false);
			mMineBtn.setSelected(false);
			mPoolBtn.setSelected(false);
			break;
		case R.id.aci_setting_btn:
			FragmentTransaction trans2 = mManager.beginTransaction();
			trans2.replace(R.id.aci_container_fl, mSettingFragment);
			trans2.commit();
			mSettingBtn.setSelected(true);
			mMainBtn.setSelected(false);
			mMineBtn.setSelected(false);
			mPoolBtn.setSelected(false);
			break;
		case R.id.aci_mine_btn:
			FragmentTransaction trans3 = mManager.beginTransaction();
			trans3.replace(R.id.aci_container_fl, mMineFragment);
			trans3.commit();
			mMineBtn.setSelected(true);
			mSettingBtn.setSelected(false);
			mMainBtn.setSelected(false);
			mPoolBtn.setSelected(false);
			break;
		case R.id.aci_pool_btn:
			FragmentTransaction trans4 = mManager.beginTransaction();
			trans4.replace(R.id.aci_container_fl, mPoolFragment);
			trans4.commit();
			mMineBtn.setSelected(false);
			mSettingBtn.setSelected(false);
			mMainBtn.setSelected(false);
			mPoolBtn.setSelected(true);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.aci_about_it:
			Toast.makeText(MainActivity.this, "" + "关于", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.aci_settings_it:
			Toast.makeText(MainActivity.this, "" + "设置", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.aci_quit_it:
			Toast.makeText(MainActivity.this, "" + "退出", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}

		// Toast.makeText(MainActivity.this, ""+item.getItemId(),
		// Toast.LENGTH_SHORT).show();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				this.exit();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT)
					.show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}

}
