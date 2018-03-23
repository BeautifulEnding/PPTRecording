package com.jph.takephoto.constant;

import android.os.Environment;

public class Constant {
    //Fragment的标识
    public static final String FRAGMENT_DEFAULT= "default";
    public static final String FRAGMENT_HAVEFILE = "haveFlie";

    public static String DEFAULT_ROOT_PATH= Environment.getExternalStorageDirectory()
            .getAbsolutePath()+"/pptRecording/";

    public static final int TEST_MESSAGE=11111;
}
