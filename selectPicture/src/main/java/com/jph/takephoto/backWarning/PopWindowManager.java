package com.jph.takephoto.backWarning;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.R;
import com.jph.takephoto.constant.Constant;
import com.jph.takephoto.util.ScreenUtil;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class PopWindowManager {
    public static int pop=0;
    private static Context mContext;
    private static View rootView;
    private static TextView processView;
    private static int process;
    private static PopupWindow popupWindow;
    public static PopupWindow initPopWindow(Context context, View view) {
        pop=1;
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(rootView, ScreenUtil.getScreenWidth(mContext),
                ScreenUtil.getScreenHeight(mContext));
        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg,null));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.update();
        if (view!=null){
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }else {
            Toast.makeText(context,"popWindow的父view为空",Toast.LENGTH_SHORT).show();
            popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }
        darkenBackground(0.2f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
        ProgressBar bar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        processView = (TextView) rootView.findViewById(R.id.process_text);
        process=NotificationHelper.getProcess();
        processView.setText("");
        if (process>0 && process<100){
            processView.setText(Constant.CURRENT_PROCESS_TIPS+process+"%");
        }else{
            processView.setText(Constant.CURRENT_PROCESS_TIPS+"0%");
        }
        Button backButton = (Button) rootView.findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//不管popWindow在不在，图像处理都已经开始了。
                NotificationHelper.showNotification(mContext);
                pop=0;
                popupWindow.dismiss();
            }
        });
      return popupWindow;
    }

    public static void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgcolor;
        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity)mContext).getWindow().setAttributes(lp);

    }

    public static TextView getProcessView(){
        return processView;
    }
}
