package com.jph.takephoto.backWarning;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.jph.takephoto.R;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class NotificationManager {
    public static void showNotification(Context context, int process, View view){
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//        mBuilder.setContentTitle("图像处理");// 设置通知栏标题
//        mBuilder.setContentText("正在处理，已完成0%");
//        mBuilder.setSmallIcon(R.drawable.ic_arrow_back);// 设置通知小ICON
//        mBuilder.setTicker("后台图像处理进度"); // 通知首次出现在通知栏，带上升动画效果的
//        mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//        Notification notification = mBuilder.build();//API 16
//        android.app.NotificationManager mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
////        进度每更新一次就要重新notify一次
//
//        mBuilder.setProgress(100,0,false);
////        这个1是个编号，manager可以同时notify多个notification
//        mNotificationManager.notify(1, notification);
////        当单击的时候如果进度为100%，则跳转到ppt显示页面，否则，跳转到popWindow显示进度
//        if (process==100){
//            Intent intent=new Intent("image process sucess");
//            intent.putExtra("message","null");
//            PendingIntent hangPendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//            mBuilder.setContentIntent(hangPendingIntent);
//            mBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS);
//        }else {
////            mNotificationManager.cancel(1);
////            PopWindowManager.initPopWindow(context,view);
//        }



//        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                 builder.setSmallIcon(R.drawable.back_pro);
//                 builder.setContentTitle("下载");
//                 builder.setContentText("正在下载");
//        final android.app.NotificationManager manager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                 manager.notify(1, builder.build());
//                 builder.setProgress(100,0,false);
//                 //下载以及安装线程模拟
//                 new Thread(new Runnable() {
//                         @Override
//                         public void run() {
//                                 for(int i=0;i<100;i++){
//                                         builder.setProgress(100,i,false);
//                                         manager.notify(1,builder.build());
//                                         //下载进度提示
//                                         builder.setContentText("下载"+i+"%");
//                                         try {
//                                                 Thread.sleep(50);//演示休眠50毫秒
//                                             } catch (InterruptedException e) {
//                                                 e.printStackTrace();
//                                             }
//                                     }
//                                 //下载完成后更改标题以及提示信息
//                                 builder.setContentTitle("开始安装");
//                                 builder.setContentText("安装中...");
//                                 //设置进度为不确定，用于模拟安装
//                                 builder.setProgress(0,0,true);
//                                 manager.notify(1,builder.build());
//                 //                manager.cancel(NO_3);//设置关闭通知栏
//                             }
//                     }).start();




        android.app.NotificationManager notificationManager;

         Notification.Builder builder;
         Intent mIntent;
         PendingIntent pendingIntent;

        builder = new Notification.Builder(context);//创建builder对象
        //指定点击通知后的动作，此处跳到我的博客
        mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/u012552275"));
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent); //跳转
        builder.setSmallIcon(R.mipmap.ic_launcher);//小图标
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true); //顾名思义，左右滑动可删除通知
        notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        builder.setContentTitle("普通通知");
        builder.setSubText("这是一个普通通知");
        builder.setContentText("点击上我");
        builder.setProgress(100,0,false);
        notificationManager.notify(0, builder.build());



    }
}
