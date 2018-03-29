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
    private String currentTag="";
    private FileExistFragment fragment;
    //string变量应该直接包括ppt名字，
    // 也就是能通过该string变量直接定位到需要打开的ppt文件
    private String fileLocate;
    public DecideFragmentHelperImpl(Context context){
        mContext=context;
        hasFilePre=context.getSharedPreferences("ppt",Context.MODE_PRIVATE);
    }
    public String getDefaultPPTFile(){
        fileLocate=hasFilePre.getString("pptName",null);
        if (fileLocate==null || fileLocate=="")
        return null;
        return fileLocate;
    }


    public void switchFragment(FragmentManager fragmentManager,int layout,String tag){
        this.fragmentManager=fragmentManager;
        fragmentTransaction = fragmentManager.beginTransaction();
        //如果当前的Fragment就是Tag所指的Fragment。则不进行处理
        if(tag==currentTag){
            return;
        }
        //如果当前Fragment不为空
        if(currentTag != null && !currentTag.equals("")){
            if (tag==Constant.FRAGMENT_HAVEFILE)
            detachFragment(getFragment(currentTag));
        }
        //增加Fragment到布局中
        attachFragment(layout, getFragment(tag), tag);
        commitTransactions();
        currentTag=tag;
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
            fragment=new FileExistFragment();
            f=fragment;
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

    public void setCurrentFragmentTag(String tag){
        currentTag=tag;
    }

    public String getCurrentFragmentTag(){
        return currentTag;
    }

    public Fragment getCurrentFragment(){

        return fragment;
    }


//    public boolean dealFragment(int defaultLayout,int fileLayout){
//        if (currentTag==Constant.FRAGMENT_DEFAULT){
////            说明当前Fragment为默认Fragment
//            switchFragment(fragmentManager,fileLayout,Constant.FRAGMENT_HAVEFILE);
//            currentTag=Constant.FRAGMENT_HAVEFILE;
//            return true;
//        }
//
//        return false;
//    }

}
