package com.jskz.util;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

public class PoolRunnable implements Runnable
{
    
    private boolean cancleTask = false;
    private boolean cancleException = false;
    private Handler mHandler = null;
    
    public PoolRunnable(Handler handler)
    {
        mHandler = handler;
    }
    
    @Override
    public void run()
    {
        Log.i("KKK", "MyRunnable  run() is executed!!! ");
        runBefore();
        if (cancleTask == false)
        {
            running();
            Log.i("KKK", "调用PoolRunnable run()方法");
        }
        
        runAfter();
    }
    
    private void runAfter()
    {
        Log.i("KKK", "runAfter()");
    }
    
    private void running()
    {
        Log.i("KKK", "running()");
        try
        {
            // 做点有可能会出异常的事情！！！
            int prog = 0;
            if (cancleTask == false && cancleException == false)
            {
                while (prog < 101)
                {
                    if ((prog > 0 || prog == 0) && prog < 70)
                    {
                        SystemClock.sleep(100);
                    }
                    else
                    {
                        SystemClock.sleep(300);
                    }
                    if (cancleTask == false)
                    {
                        mHandler.sendEmptyMessage(prog++);
                        Log.i("KKK", "调用 prog++ = " + (prog));
                    }
                }
            }
        }
        catch (Exception e)
        {
            cancleException = true;
        }
    }
    
    private void runBefore()
    {
        // TODO Auto-generated method stub
        Log.i("KKK", "runBefore()");
    }
    
    public void setCancleTaskUnit(boolean cancleTask)
    {
        this.cancleTask = cancleTask;
        Log.i("KKK", "点击了取消任务按钮 ！！！");
        // mHandler.sendEmptyMessage(0);
    }
    
}
