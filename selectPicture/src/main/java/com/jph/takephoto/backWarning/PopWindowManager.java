package com.jph.takephoto.backWarning;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jph.takephoto.R;
import com.jph.takephoto.constant.Constant;
import com.jph.takephoto.imagepro.ImageProTask;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.util.ScreenUtil;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class PopWindowManager {
    private static Context mContext;
    private static View rootView;
    private static TextView processView;
    private static Handler updateProcessHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            在这里处理popWindow中的进度和Notification中的进度
            switch (msg.what){
                case Constant.TEST_MESSAGE:
//                    测试消息
                    if (processView!=null){
                        processView.append("成功");
                    }
                    break;
                default:
                    break;
            }
        }
    };
    public static Handler initPopWindow(final Context context, View view) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(rootView, ScreenUtil.getScreenWidth(mContext),
                ScreenUtil.getScreenHeight(mContext));
        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg,null));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.update();
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        darkenBackground(0.2f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
        ProgressBar bar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        processView = (TextView) rootView.findViewById(R.id.process_text);
        Button backButton = (Button) rootView.findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//不管popWindow在不在，图像处理都已经开始了。
                NotificationManager.showNotification(context,10,view);
//                popupWindow.dismiss();
            }
        });
        return updateProcessHandler;
    }

    public static void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgcolor;
        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)mContext).getWindow().setAttributes(lp);

    }
}
