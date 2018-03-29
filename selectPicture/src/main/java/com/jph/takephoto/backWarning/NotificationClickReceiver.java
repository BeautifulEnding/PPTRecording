package com.jph.takephoto.backWarning;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.jph.takephoto.constant.Constant;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

/***
 * 处理notification的点击事件，当被点击时，触发广播，跳转到这里进行处理，
 * 注意这里也不能处理耗时任务
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    /***
     * 如果通知当前进度未完成，则取消当前通知，返回到（重新显示）popWindow
     * 如果当前进度为完成，   则取消当前通知，不显示popWindow，主页面交换Fragment或
     * 更新Fragment的内容
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
//        Toast.makeText(context,"收到单击广播",Toast.LENGTH_SHORT).show();
        NotificationManager manager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        int process=NotificationHelper.getProcess();
        Intent intent1=new Intent();
        intent1.setAction(Constant.IMAGE_PRO_SUCCESS);
        if (process<100 && process>=0){
            intent1.putExtra("pop",true);
        }
        if (process==100){
            //        通知主页面更新Fragment
            intent.putExtra("update",true);
        }

        context.sendBroadcast(intent1);
        manager.cancel(Constant.IMAGE_NOTI_ID);
    }
}
