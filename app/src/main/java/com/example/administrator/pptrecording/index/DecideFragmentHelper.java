package com.example.administrator.pptrecording.index;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public interface DecideFragmentHelper {

//    设置当前Fragment的TAG
    public void setCurrentFragmentTag(String tag);

//    得到当前Fragment的TAG
    public String getCurrentFragmentTag();

    //得到当前Fragment
    public Fragment getCurrentFragment();
    //确定启动APP显示哪个Fragment,返回默认应该打开的ppt文件的位置
    public String getDefaultPPTFile();
    /***
     *
     * @param fragmentManager 从调用处传入 fragmentManager
     * @param layout 传入fragment所依赖的布局
     * @param tag  需要转换的fragment的tag
     */
    public void switchFragment(FragmentManager fragmentManager, int layout, String tag);

//    当图像处理完成后主页面Fragment进行的相关操作
//    交换Fragment或更新Fragment的内容

//    /***
//     * @param defaultLayout
//     * @param fileLayout
//     * @return 返回是否更换了layout，如果更换了说明之前为默认，否则直接更换Fragment的内容
//     */
//    public boolean dealFragment(int defaultLayout,int fileLayout);
}
