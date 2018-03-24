package com.example.administrator.pptrecording.index;


import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public interface DecideFragmentHelper {
    //确定启动APP显示哪个Fragment,返回默认应该打开的ppt文件的位置
    public String decideFragment();
    /***
     *
     * @param fragmentManager 从调用处传入 fragmentManager
     * @param layout 传入fragment所依赖的布局
     * @param tag  需要转换的fragment的tag
     * @param currFragTag 当前fragment的tag
     */
    public void switchFragment(FragmentManager fragmentManager, int layout, String tag, String currFragTag);
}
