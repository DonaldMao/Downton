package com.cndownton.app.downton.compatview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Mpf on 2017/9/14.
 */

public class CompatViewPager extends ViewPager {
    public CompatViewPager(Context context) {
        super(context);
    }
    
    public CompatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        }catch (Exception e){
            return true;
        }
    }
}
