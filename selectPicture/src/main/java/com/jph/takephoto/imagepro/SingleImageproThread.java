package com.jph.takephoto.imagepro;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jph.takephoto.constant.Constant;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.util.CacheUtil;
import com.jph.takephoto.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/25 0025.
 */

public class SingleImageproThread implements Runnable{
    private Context mContext;
    private ImageSize imageSize;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    private Mat srcMat;
    private Mat objMat;
    private Bitmap srcBitmap;
    private Bitmap objBitmap;
    private TImage image;
    private ArrayList<String> imagePaths;
    private int index;
    private Handler uiHandler;
    private Handler threadHandler;
    public SingleImageproThread(TImage image,ArrayList<String> imagePaths,int i,Handler handler,Context context,Handler threadHandler){
        this.image=image;
        this.imagePaths=imagePaths;
        this.index=i;
        this.uiHandler=handler;
        this.mContext=context;
        this.threadHandler=threadHandler;
    }
    @Override
    public void run() {
        imageSize = new ImageSize(ScreenUtil.getScreenWidth(mContext),(int) (ScreenUtil.getScreenHeight(mContext)*0.6));
        singleImagePro();

    }

    private void singleImagePro(){
        String path= ImageDownloader.Scheme.FILE.wrap(image.getOriginalPath());
        ImageLoader.getInstance().loadImage(path, imageSize,options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
//                  Log.e("SingleImageproThread","正在执行onLoadingStarted方法");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Log.e("SingleImageproThread","正在执行onLoadingComplete方法");
                srcMat = new Mat();
                objMat = new Mat();
                srcBitmap = loadedImage;
                Utils.bitmapToMat(srcBitmap, srcMat);//convert original bitmap to Mat, R G B.
                objMat=ImageOpencv.findPPTRect(srcMat);
                //            第i张图片处理完成后
                Message message=new Message();
                message.what= Constant.PROCESS;
                message.arg1=index+1;
                uiHandler.sendMessage(message);
//                Imgproc.cvtColor(srcMat, objMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
                objBitmap = Bitmap.createBitmap(objMat.width(), objMat.height(), Bitmap.Config.RGB_565);
                Utils.matToBitmap(objMat, objBitmap); //convert mat to bitmap
//                Log.e("SingleImageproThread","正在执行onLoadingComplete方法");
//                保存bitmap到本地
                String fileName=System.currentTimeMillis()+".jpg";
                imagePaths.add(imagePaths.size(), Constant.DEFAULT_IMAGE_PATH+fileName);
//                Message message1=new Message();
//                message1.what=Constant.SINGLE_THRAED_FINISH;
                threadHandler.sendEmptyMessage(Constant.SINGLE_THRAED_FINISH);
                CacheUtil.saveBitmap(objBitmap,fileName);
            }
        });

    }
}
