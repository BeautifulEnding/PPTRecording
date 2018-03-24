package com.example.administrator.pptrecording.index.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.*;

import com.jph.takephoto.constant.Constant;
import com.example.administrator.pptrecording.index.DecideFragmentHelper;
import com.example.administrator.pptrecording.index.DefaultFragment;
import com.example.administrator.pptrecording.index.FileExistFragment;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class DecideFragmentHelperImpl implements DecideFragmentHelper {
    private SharedPreferences hasFilePre;
    private Context mContext;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    //string变量应该直接包括ppt名字，
    // 也就是能通过该string变量直接定位到需要打开的ppt文件
    private String fileLocate;
    public DecideFragmentHelperImpl(Context context){
        mContext=context;
        hasFilePre=context.getSharedPreferences("defaultFile",Context.MODE_PRIVATE);
    }
    public String decideFragment(){
        fileLocate=hasFilePre.getString("fileLocate",null);
        if (fileLocate==null || fileLocate=="")
        return null;
        return fileLocate;
    }


    public void switchFragment(FragmentManager fragmentManager,int layout,String tag,String currFragTag){
        this.fragmentManager=fragmentManager;
        fragmentTransaction = fragmentManager.beginTransaction();
        //如果当前的Fragment就是Tag所指的Fragment。则不进行处理
        if(tag==currFragTag){
            return;
        }
        //如果当前Fragment不为空
        if(currFragTag != null && !currFragTag.equals("")){
            detachFragment(getFragment(currFragTag));
        }
        //增加Fragment到布局中
        attachFragment(layout, getFragment(tag), tag);
        commitTransactions();
    }
    private FragmentTransaction ensureTransaction(){
        if(fragmentTransaction == null){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        return fragmentTransaction;

    }

    private void attachFragment(int layout, Fragment f, String tag){
        if(f != null){
            if(f.isDetached()){
                ensureTransaction();
                fragmentTransaction.attach(f);

            }else if(!f.isAdded()){
                ensureTransaction();
                fragmentTransaction.add(layout, f, tag);
            }
        }
    }

    private Fragment getFragment(String tag){
        //得到tag所指向的Fragment
        Fragment f = fragmentManager.findFragmentByTag(tag);
        if (f==null && Constant.FRAGMENT_DEFAULT==tag){
            f=new DefaultFragment();
        }
        if (f==null && Constant.FRAGMENT_HAVEFILE==tag){
            f=new FileExistFragment();
        }
        return f;

    }
    private void detachFragment(Fragment f){

        if(f != null && !f.isDetached()){
            //删除该Fragment
            ensureTransaction();
            fragmentTransaction.detach(f);
        }
    }

    private void commitTransactions(){
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
            fragmentTransaction = null;
        }
    }
}
