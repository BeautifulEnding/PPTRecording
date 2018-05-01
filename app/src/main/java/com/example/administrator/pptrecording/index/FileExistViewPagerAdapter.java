package com.example.administrator.pptrecording.index;


import android.support.v4.app.*;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class FileExistViewPagerAdapter extends PagerAdapter{

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
//        ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);

    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(View container, int position) {
//        ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
//        return mImageViews[position % mImageViews.length];
        return null;
    }


}
