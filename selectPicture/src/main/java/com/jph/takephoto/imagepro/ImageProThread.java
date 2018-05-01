package com.jph.takephoto.imagepro;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jph.takephoto.backWarning.NotificationHelper;
import com.jph.takephoto.constant.Constant;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.util.CacheUtil;
import com.jph.takephoto.util.SDCardUtil;
import com.jph.takephoto.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/3/23 0023.
 * 线程中不能出现Toast
 */

public class ImageProThread extends Thread{
    public interface getResultListener{
        void getSuccess(ArrayList<String> result);
    }

    private getResultListener listener;
//    从主UI线程传过来的Handler
    private Handler uiHandler;
//    记录当前图像处理进度
//    private int process;
//    记录处理后的图像保存路径
    private ArrayList<String> imagesPath;
    private TResult srcImages;
    private Context mContext;
    private ArrayList<TImage> images;
    private LoadDataExecutor executor;
    private int total;
    private int process;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            在这里处理popWindow中的进度和Notification中的进度
            switch (msg.what){
                case Constant.SINGLE_THRAED_FINISH:
//                    Log.e("ImageProThread Handler","收到子线程的消息");
                    process++;
                    if (process==total){
                        if (listener!=null && imagesPath!=null && imagesPath.size()>0){
//                            Log.e("ImageProThread Handler","imagesPath.size()>0");
                            listener.getSuccess(imagesPath);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public ImageProThread(Handler handler, TResult srcImages,Context context){
        uiHandler=handler;
        this.srcImages=srcImages;
        this.mContext=context;
        total=srcImages.getImages().size();
        process=0;
//        Log.e("ImageProThread","正在执行ImageProThread");
    }
    @Override
    public void run() {
        executor=new LoadDataExecutor();
        executor.init();
        imagesPath=new ArrayList<>();
        imagePro();
    }

    public void imagePro(){
        images=srcImages.getImages();
        for (int i=0;i<images.size();i++){
//            利用线程池管理单个图像处理线程
            executor.runThread(images.get(i),imagesPath,i,uiHandler,mContext,handler);
        }

    }

    public ArrayList<String> getImagesPath(){
        return imagesPath;
    }

    public void setListener(getResultListener listener){
        this.listener=listener;
    }

    public static class LoadDataExecutor {
        ExecutorService executor;
        //初始化参数(根据自己的需要修改参数)
        public void init()
        {
            executor = Executors.newFixedThreadPool(Constant.MAX_THREAD_NUM);
        }

        //运行线程(根据需要修改自己的参数)
        public void runThread(TImage image, ArrayList<String> imagePaths, int i,Handler handler,Context context,Handler threadHandler)
        {
            Runnable runner = new SingleImageproThread(image,imagePaths,i,handler,context,threadHandler);
            executor.execute(runner);
        }

        //关闭线程
        public void closeThread()
        {
            //Thread.sleep(waitTime);
            if(executor!=null)
            {
                try {
                    executor.shutdownNow();
                    executor.awaitTermination(0, TimeUnit.NANOSECONDS);
                } catch (InterruptedException ignored)
                {
                }

            }
        }
    }
}
