package com.jph.takephoto.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.Serializable;

public class TImage implements Serializable{
    private String originalPath;
    private String compressPath;
    private FromType fromType;
    private boolean cropped;
    private boolean compressed;
//    private Bitmap objBitmap;
    public static TImage of(String path, FromType fromType){
        return new TImage(path, fromType);
    }
    public static TImage of(Uri uri, FromType fromType){
        return new TImage(uri, fromType);
    }
    private TImage(String path, FromType fromType) {
        this.originalPath = path;
        this.fromType = fromType;
    }
    private TImage(Uri uri, FromType fromType) {
        this.originalPath = uri.getPath();
        this.fromType = fromType;
    }
    private TImage(String originalPath){
        this.originalPath = originalPath;
    }
//    public Bitmap getObjBitmap(){
//        return objBitmap;
//    }
//    public void setObjBitmap(Bitmap bitmap){
//        this.objBitmap=bitmap;
//    }
    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public FromType getFromType() {
        return fromType;
    }

    public void setFromType(FromType fromType) {
        this.fromType = fromType;
    }

    public boolean isCropped() {
        return cropped;
    }

    public void setCropped(boolean cropped) {
        this.cropped = cropped;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }
    //图片来自相机还是其他的路径
    public enum FromType {
        CAMERA, OTHER
    }

    public static TImage parse(String jsonString) {
//        将json字符串转化成ContentList对象
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        TImage image = new Gson().fromJson(jsonString,TImage.class);
        return image;
    }
}
