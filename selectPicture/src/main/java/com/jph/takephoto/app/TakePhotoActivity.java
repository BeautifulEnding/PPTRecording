package com.jph.takephoto.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jph.takephoto.R;
import com.jph.takephoto.backWarning.PopWindowManager;
import com.jph.takephoto.constant.Constant;
import com.jph.takephoto.imagepro.ImageProTask;
import com.jph.takephoto.imagepro.ImageProThread;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.PermissionManager.TPermissionType;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

public class TakePhotoActivity extends IndexActivity implements TakePhoto.TakeResultListener,InvokeListener{
    private static final String TAG = TakePhotoActivity.class.getName();
    private Context context;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private TResult resultPhoto;
    private View relativeView;
    private Handler uiHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        context=TakePhotoActivity.this;
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }
    @Override
    public void takeSuccess(TResult result) {
//        Toast.makeText(TakePhotoActivity.this,"原路径 "+result.getImage().getOriginalPath(),Toast.LENGTH_SHORT).show();
//      压缩后路径为空
//  Toast.makeText(TakePhotoActivity.this,"压缩后的路径 "+result.getImage().getCompressPath(),Toast.LENGTH_SHORT).show();
//        Log.i(TAG,"takeSuccess：" + result.getImage().getCompressPath());
        resultPhoto=result;
        if (result!=null){
            //                imageProManager=new ImageProManagerImp();
////                让图像处理模块负责分割图片并进行相关显示
//                imageProManager.imagePro(resultImage);
            uiHandler=PopWindowManager.initPopWindow(context,relativeView);
            //                解除popWindow,转入后台进行图片处理，图片处理工作都在ImageProTask中异步执行
            ImageProThread task=new ImageProThread(uiHandler);
            task.start();
        }
    }
    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }
    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public TPermissionType invoke(InvokeParam invokeParam) {
        TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }
    public TResult getTResult(){
        return resultPhoto;
    }
    public void setRelativeView(View view){
        relativeView=view;
    }
}
