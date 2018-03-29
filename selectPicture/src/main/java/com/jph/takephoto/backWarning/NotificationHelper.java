package com.jph.takephoto.backWarning;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.jph.takephoto.R;
import com.jph.takephoto.constant.Constant;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class NotificationHelper {
    private static int process=0;
    private static Context mContext;
    public static void showNotification(Context context){
        mContext=context;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ppt);// 设置通知小ICON
//        mBuilder.setTicker(message.getSender_id()); // 通知首次出现在通知栏，带上升动画效果的
        mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        if (process==100){
            mBuilder.setContentTitle("完成");
            mBuilder.setContentText("已完成，点击打开文件");// 通知首次出现在通知栏，带上升动画效果的
            mBuilder.setProgress(100,100,false);
        }else{
            mBuilder.setContentTitle("正在处理");
            mBuilder.setContentText("当前进度");
            mBuilder.setProgress(100,process,false);
        }
        mBuilder.setOngoing(true);
        Intent intent=new Intent(Constant.CLICK_NOTIFICATION);
        PendingIntent hangPendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(hangPendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS);
        Notification notification = mBuilder.build();//API 16
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Constant.IMAGE_NOTI_ID, notification);
    }

    public static void updateProcess(int pro){
        process=pro;
        Log.e("当前进度","    "+process+"%");
        showNotification(mContext);
    }

    public static int getProcess(){
        return process;
    }
}
