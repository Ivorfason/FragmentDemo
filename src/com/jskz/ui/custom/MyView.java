package com.jskz.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.TextView;

public class MyView extends TextView {
	
    public MyView(Context context) {
       super(context);
       // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
       // TODO Auto-generated method stub
       super.onDraw(canvas);
       canvas.drawColor(Color.BLUE);
    }
    
}