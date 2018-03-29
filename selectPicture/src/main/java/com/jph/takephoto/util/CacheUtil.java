package com.jph.takephoto.util;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class CacheUtil {
    public static void saveBitmap(Bitmap bitmap,String filename) {
        SDCardUtil.put(bitmap,filename);
    }
    public static boolean cacheLoad(String topic) {
        return false;
    }

}
