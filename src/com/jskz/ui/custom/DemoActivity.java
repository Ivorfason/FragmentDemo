package com.jskz.ui.custom;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jskz.ui.activity.R;

public class DemoActivity extends Activity {
	private Button mClickBtn;
	private Button mNoClickBtn;

	protected void onCreate(Bundle savedInstanceState) {
		// Java异常体系
		
		// private protected public package-private default
		
		// 方法的重写和重载
		// this,super

		// 重用10中widget的继承结构

		// 内部类
		
		// Java集合框架
		
		SparseArray<String> sa;
		
		StringBuilder sb1;
		StringBuffer sb2;

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_activity);

		initView();
		initListener();
		initData1();
		initData3();

		try {
			Log.e("Try-Catch", "" + initData2(12, 0));
		} catch (Exception e) {
			Log.e("Try-Catch", "0不能做被除数");
		}
		Cake c1 = new Cake(1);
		Cake c2 = new Cake(2);

		// TODO Auto-generated method stub		instanceof操作符的左右操作数必须有继承或实现关系
		boolean b0 = c1 instanceof Object;
		Log.e("Instanceof", "" + b0);
		boolean b1 = "Sting" instanceof Object;  
		Log.e("Instanceof", "" + b1);
		boolean b2 = new String() instanceof String;  
		Log.e("Instanceof", "" + b2);
		boolean b3 = new Object() instanceof String;  
		Log.e("Instanceof", "" + b3);
		boolean b4 = null instanceof String;  
		Log.e("Instanceof", "" + b4);
		boolean b5 = (String)null instanceof String;  
		Log.e("Instanceof", "" + b5);

		c2 = null;
		System.gc();
	}

	private void initData3() {
		try {
			throw new Exception("b = 0");
		} catch (Exception e) {
			// TODO: handle exception
			return;
		} finally {
			Log.e("123", "123");
		}
		
	}

	private void initView() {
		this.mNoClickBtn = (Button) findViewById(R.id.aci_noclick_btn);
	}

	private void initListener() {
		mNoClickBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mNoClickBtn.setText("不要啊");
			}
		});
		ListView lv = null;
		final int i = 1;
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.e("TAG", position+""+i);
			}
		});
	}

	private void initData1() {
		int a = 3;
		for (int i = 0; i < 5; i++) {
			Log.e("for循环中的break", "外循环");
			for (int ii = 0; ii < 10; ii++) {
				if (i < a) {
					i++;
					Log.e("for循环中的break", "内循环");
					break;
				}
			}
			Log.e("for循环中的break", "i的值为" + i);
			
		}

		for (int j = 0; j < 5; j++) {
			Log.e("for循环中的continue", "外循环");
			for (int jj = 0; jj < 10; jj++) {
				if (j < a) {
					j++;
					Log.e("for循环中的continue", "内循环");
					continue;
				}
			}
			Log.e("for循环中的continue", "j的值为" + j);
		}

		for (int k = 0; k < 3; k++) {
			switch (k) {
			case 2:
				Log.e("switch中的break", "k的值为" + k);
				break;
			}
			Log.e("switch中的break", "k的值为" + k);
		}

		for (int l = 0; l < 3; l++) {
			switch (l) {
			case 2:
				Log.e("switch中的continue", "l的值为" + l);
				continue;
			}
			Log.e("switch中的continue", "l的值为" + l);

		}
	}

	private int initData2(int a, int b) throws Exception {
		if (b == 0) {
			throw new Exception("b = 0");
		}
		return a / b;
	}

	public int MyClick(Object g) {
		
		return 0;
	}
	
	public String MyClick(String g) {
		
		return "1";
	}
	
	private void MyClick(TextView v) {
		
	}
	
	public void MyClick(View v) {
		mClickBtn = (Button) findViewById(R.id.aci_click_btn);
		mClickBtn.setText("不要啊");
	}

	/**
	 * continue，break try catch finally Experience throw throws finalize
	 * 
	 * instanceof synthronize transient volatile
	 */

}

class Cake extends Object {
	private int id;

	public Cake(int id) {
		this.id = id;
		Log.e("Cake Object", id + "is created");
	}

	protected void finalize() throws java.lang.Throwable {
		super.finalize();
		Log.e("Cake Object", id + "is disposed");
	}
}
