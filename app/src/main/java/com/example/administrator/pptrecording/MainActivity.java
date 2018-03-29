package com.example.administrator.pptrecording;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.pptrecording.util.DialogUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.backWarning.NotificationHelper;
import com.jph.takephoto.backWarning.PopWindowManager;
import com.jph.takephoto.constant.Constant;
import com.example.administrator.pptrecording.index.DecideFragmentHelper;
import com.example.administrator.pptrecording.index.FileExistFragment;
import com.example.administrator.pptrecording.index.impl.DecideFragmentHelperImpl;
import com.jph.takephoto.imagepro.ImageProThread;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.opencv.android.OpenCVLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TakePhoto.TakeResultListener,InvokeListener,ImageProThread.getResultListener{
    private static final String TAG = MainActivity.class.getName();
    private Context context;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private String defaultFile;
//    private String curFragmentTag;
    private TResult resultImage;
    private ArrayList<String> imagePaths;
    private DecideFragmentHelper fragmentHelper;
    private MyBroadcastReceiver br;
//    private ImageProThread thread;
    private FileExistFragment fragment;
    private FloatingActionButton fab;
    private TextView processView;
    private ImageProThread imageProThread;
    private PopupWindow popupWindow;
    private int process;
    private int total=0;
//    public static int pop=0;
//    保存传过来的顺序，因为多线程处理最后图片分割完成的顺序可能
//    和用户输入的顺序不一样
//    最后需要重新排序，现在还没有实现该功能
    private ArrayList<Integer> order=new ArrayList<>();
    static {
        if (OpenCVLoader.initDebug()){
            Log.e(TAG,"OPENCV initialize succeed");
        }else{
            Log.e(TAG,"OPENCV initialize failed");
        }
    }
    private Handler updateProcessHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            在这里处理popWindow中的进度和Notification中的进度
            switch (msg.what){
                case Constant.PROCESS:
//                    Log.e("Handler收到消息","Constant.PROCESS消息    "+msg.arg1);
//                    更新进度
                    order.add(total,msg.arg1);
                    total++;
                    process=(int)((float)total/(float) resultImage.getImages().size()*100);
                    if (PopWindowManager.pop==1){
                        Log.e("popupWindow","Constant.PROCESS消息    "+msg.arg1);
                            processView.setText("");
                            processView.setText(Constant.CURRENT_PROCESS_TIPS+process+"%");
                    }else{
                        Log.e("Notification","Notification消息    "+msg.arg1);
                        NotificationHelper.updateProcess(process);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context=MainActivity.this;
        getTakePhoto().onCreate(savedInstanceState);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentHelper=(DecideFragmentHelper)
                new DecideFragmentHelperImpl(getApplicationContext());
        defaultFile=fragmentHelper.getDefaultPPTFile();
//        if (defaultFile==null || defaultFile==""){
//            fragmentHelper.switchFragment(
//                    getSupportFragmentManager(),R.id.fragment_content, Constant.FRAGMENT_DEFAULT);
//
//        }else{
//            fragmentHelper.switchFragment(
////                    还没关注怎么打开默认文件，因为现在还没有生成.ppt文件
////                    所以现在只关注怎么更新Fragmnet，不关注怎么初始化
//                    getSupportFragmentManager(),R.id.fragment_content, Constant.FRAGMENT_HAVEFILE);
//        }
        fragmentHelper.switchFragment(
//                    还没关注怎么打开默认文件，因为现在还没有生成.ppt文件
//                    所以现在只关注怎么更新Fragmnet，不关注怎么初始化
                getSupportFragmentManager(),R.id.fragment_content, Constant.FRAGMENT_HAVEFILE);
//新建ppt文件按钮
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                调用第三方选择照片module,最多选择100张图片
//                重新调整进度
                process=0;
                imagePaths=null;
                getTakePhoto().onPickMultiple(100);
            }
        });
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.IMAGE_PRO_SUCCESS);
        br=new MyBroadcastReceiver();
        registerReceiver(br,myIntentFilter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_scanFile) {
//            跳转到6，这可以单独做一个module
        }

        return super.onOptionsItemSelected(item);
    }

    public FragmentManager getFrManager(){
        return getSupportFragmentManager();
    }

    /**
     * 主页面收到图像处理完成广播
     * 交换Fragment或更新Fragment的内容
     */
    private class MyBroadcastReceiver extends BroadcastReceiver {
        /***
         *
         * @param context
         * @param intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            //todo 跳转之前要处理的逻辑
            Toast.makeText(context,"主页面收到图像处理完成广播",Toast.LENGTH_SHORT).show();
            boolean pop1=intent.getBooleanExtra("pop",false);
            if (pop1){
                popupWindow=PopWindowManager.initPopWindow(context,fab);
                processView=PopWindowManager.getProcessView();
            }else{
                updatePPTFile();
            }
            updatePPTFile();
        }
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
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
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
        resultImage=result;
        if (result!=null){
            popupWindow=PopWindowManager.initPopWindow(context,fab);
            processView=PopWindowManager.getProcessView();
//            开启图像处理线程池
            imageProThread=new ImageProThread(updateProcessHandler,resultImage,context);
            imageProThread.setListener(this);
            imageProThread.start();
        }
    }
    @Override
    public void takeFail(TResult result,String msg) {
        Toast.makeText(this,"获取照片失败",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

    @Override
    protected void onDestroy() {
// TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(br);//LS:重点！
    }

    public void getSuccess(ArrayList<String> result){
//        得到处理之后的图片路径
//        Log.e("getSuccess ","回调函数getSuccess");
         imagePaths=result;
//        for (int i=0;i<imagePaths.size();i++){
//            Log.e("imagePaths的路径   ",imagePaths.get(i));
//        }
        showTips();

    }

    public void updatePPTFile(){
//        交换当前Fragment为
        fragmentHelper.switchFragment(getSupportFragmentManager(),R.id.fragment_content,Constant.FRAGMENT_HAVEFILE);
        fragment=(FileExistFragment) fragmentHelper.getCurrentFragment();
//        fragment.updateRecyclerView(imagePaths);
        fragment.updateListView(imagePaths);
    }

    private void showTips() {
        DialogUtil.showInputDialog(context);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("打开ppt文件").setMessage(Constant.SHOW_PPT_FILE)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        updatePPTFile();
                    }
                }).setNegativeButton("不了",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                保存ppt文件

                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
        popupWindow.dismiss();
        PopWindowManager.pop=0;
    }

}
