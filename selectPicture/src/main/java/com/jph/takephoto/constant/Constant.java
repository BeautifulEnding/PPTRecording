package com.jph.takephoto.constant;

import android.os.Environment;

public class Constant {
    //Fragment的标识
    public static final String FRAGMENT_DEFAULT= "default";
    public static final String FRAGMENT_HAVEFILE = "haveFlie";

    public static final String DEFAULT_ROOT_PATH= Environment.getExternalStorageDirectory()
            .getAbsolutePath()+"/pptRecording/";
    public static final String DEFAULT_IMAGE_PATH=DEFAULT_ROOT_PATH+"images/";
    public static final String DEFAULT_PPT_PATH=DEFAULT_ROOT_PATH+"ppts/";
    public static final String IMAGE_PRO_SUCCESS="image.pro.success";
    public static final String CURRENT_PROCESS_TIPS="该过程可能需要" +
            "过长时间，可转入后台进行处理，当前进度为";
    public static final String SHOW_PPT_FILE="图片转换完成，是否现在显示？";
    public static final String SHOW_POPWINDOW="打开popWindow";
    public static final String CLICK_NOTIFICATION="click.notification";
    public static final int SINGLE_THRAED_FINISH=10;
    public static final int IMAGE_PATH=4;
    public static final int TEST_MESSAGE=11111;
    public static final int IMAGE_NOTI_ID=1;
    public static final int PROCESS=2;
    public static final int MAX_THREAD_NUM=5;
}
