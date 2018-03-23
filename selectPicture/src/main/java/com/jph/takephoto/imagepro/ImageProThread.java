package com.jph.takephoto.imagepro;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jph.takephoto.constant.Constant;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class ImageProThread extends Thread{
//    从主UI线程传过来的Handler
    private Handler uiHandler;
    public ImageProThread(Handler handler){
        uiHandler=handler;
    }
    @Override
    public void run() {
        try{
            System.out.println("开始下载文件");
            //此处让线程DownloadThread休眠5秒中，模拟文件的耗时过程
            Thread.sleep(5000);
            Message message=new Message();
            message.what= Constant.TEST_MESSAGE;
//            Message中还可以带入消息
            Bundle bundle=new Bundle();
            bundle.putString("test","testMessage");
            message.setData(bundle);
            uiHandler.sendMessage(message);
            System.out.println("文件下载完成");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
