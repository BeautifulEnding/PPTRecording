package com.example.administrator.pptrecording;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.pptrecording.index.HeaderAndFooterRecyclerViewAdapter;
import com.example.administrator.pptrecording.index.HomeFragmentView;
import com.example.administrator.pptrecording.index.SimpleAdapter;
import com.jph.takephoto.backWarning.NotificationHelper;
import com.jph.takephoto.backWarning.PopWindowManager;
import com.jph.takephoto.constant.Constant;
import com.jph.takephoto.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class TestActivity extends Activity implements HomeFragmentView {
    private ImageView bigImage;
    private ImageView left;
    private ImageView right;
    private RecyclerView recyclerView;
    private HeaderAndFooterRecyclerViewAdapter headerAdapter;
    private SimpleAdapter recycleViewAdapter;
    //    记录图像的路径
    private ArrayList<String> imagePaths=new ArrayList<>();
    private MyBroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        setContentView(R.layout.havefile_fragment);
        left=findViewById(R.id.left);
        bigImage=findViewById(R.id.container);
        right=findViewById(R.id.right);
        recyclerView=findViewById(R.id.recyclerView);
//        初始化recycleView
        initRecyclerView();
        if (imagePaths != null && imagePaths.size()>0){
            ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(imagePaths.get(0)),bigImage);
        }
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("right","right按钮被点击");
                ArrayList<String> image=new ArrayList<String>();
                image.add(Constant.DEFAULT_IMAGE_PATH+"1522115975386.jpg");
                updateListView(image);
            }
        });
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.IMAGE_PRO_SUCCESS);
        br=new MyBroadcastReceiver();
        registerReceiver(br,myIntentFilter);
        NotificationHelper.showNotification(TestActivity.this);
    }

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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initRecyclerView() {
//        imagePaths.add(Constant.DEFAULT_IMAGE_PATH+"1522115975386.jpg");
        recycleViewAdapter=new SimpleAdapter(TestActivity.this,imagePaths);
        recycleViewAdapter.setImageViews(left,bigImage,right);
//        headerAdapter = new HeaderAndFooterRecyclerViewAdapter(recycleViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(TestActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleViewAdapter);
//        RecyclerViewUtils.setHeaderView(recyclerView, headerAdapter);
    }

    @Override
    public void updateListView(ArrayList<String> statuselist) {
        imagePaths= statuselist;
        recycleViewAdapter.setData(statuselist);
        recycleViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
// TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(br);//LS:重点！
    }

}