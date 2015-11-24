package com.jskz.test;

import android.util.Log;

import com.jskz.test.test2.Demo3;

public class Demo2 {
	
	private static final String TAG = "Demo2";
	
	private static Demo1 mDemo1;
	private static Demo3 mDemo3;
	
	public static void main(String[] args) {
		mDemo1 = new Demo1();
		
		Log.e("TAG", "Demo1.name1() = " + mDemo1.name1());
		Log.e("TAG", "Demo1.name2() = " + mDemo1.name2());
		Log.e("TAG", "Demo1.name3() = " + mDemo1.name3());
		
		// Log.e("TAG", "Demo3.name1() = " + mDemo3.name1());
		Log.e("TAG", "Demo3.name2() = " + mDemo3.name2());
		// Log.e("TAG", "Demo3.name3() = " + mDemo3.name3());
		
	}

}
