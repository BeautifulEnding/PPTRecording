package com.example.administrator.pptrecording.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.pptrecording.MainActivity;
import com.example.administrator.pptrecording.R;
import com.jph.takephoto.constant.Constant;

/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class DialogUtil {
    private static boolean change=true;
    // 定义一个显示消息的对话框
    public static boolean showDialog(Context context
            , String msg , boolean goHome)
    {
        // 创建一个AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(msg).setCancelable(false);
        if(goHome) {
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.setNegativeButton("不了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    change=false;
                }
            });
        }
        else
        {
            builder.setPositiveButton("确定", null);
        }
        builder.create().show();

        return true;
    }

    // 定义一个显示消息的对话框
    public static void showInputDialog(final Context context)
    {
        /* @setView 装入自定义View ==> R.layout.dialog_customize
     * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
     * dialog_customize.xml可自定义更复杂的View
     */
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(context);
        customizeDialog.setTitle("请输入保存该文件的名称");
        final View dialogView = LayoutInflater.from(context)
            .inflate(R.layout.dialog,null);
        customizeDialog.setView(dialogView);
        customizeDialog.setIcon(R.drawable.ppt);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText edit_text =
                                (EditText) dialogView.findViewById(R.id.edit_text);
                        SharedPreferences preferences=context.getSharedPreferences("ppt",context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("pptName",edit_text.getText().toString());
                        editor.commit();
                        Toast.makeText(context,
                                "文件已保存在"+ Constant.DEFAULT_PPT_PATH+"目录下",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        customizeDialog.show();
    }

//    public static boolean getChangeValues(){
//        return change;
//    }
}
