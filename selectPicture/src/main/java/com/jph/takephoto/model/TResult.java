package com.jph.takephoto.model;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
public class TResult implements Serializable{
    private  ArrayList<TImage> images;
    private TImage image;
//    private String savePaths;
    public static TResult of(TImage image){
        ArrayList<TImage> images=new ArrayList<>(1);
        images.add(image);
        return new TResult(images);
    }
    public static TResult of(ArrayList<TImage> images){
        return new TResult(images);
    }
    private TResult(ArrayList<TImage> images) {
        this.images = images;
        if(images!=null&&!images.isEmpty())this.image=images.get(0);
    }
//    public String getSavePaths(){
//        return savePaths;
//    }
//    public void setSavePaths(String paths){
//        this.savePaths=paths;
//    }
    public ArrayList<TImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<TImage> images) {
        this.images = images;
    }

    public TImage getImage() {
        return image;
    }

    public void setImage(TImage image) {
        this.image = image;
    }

    public static TResult parse(String jsonString) {
//        将json字符串转化成ContentList对象
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        TResult result = new Gson().fromJson(jsonString,TResult.class);
        return result;
    }
}
