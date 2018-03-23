package com.jph.takephoto.imagepro;

import android.os.AsyncTask;

import com.jph.takephoto.model.TResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/23 0023.
 */
//public abstract class AsyncTask<Params, Progress, Result>
//在此例中，Params泛型是TResult类型，Progress泛型是Object类型，Result泛型是ArrayList<String>类型
public class ImageProTask extends AsyncTask<Object, Object, ArrayList<String>>{

//    正式开始耗时任务之前的工作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

//   在后台做的工作
    @Override
    protected ArrayList<String> doInBackground(Object... params) {

        return null;
    }

//更新进度
    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }
//耗时任务完成之后的处理
    @Override
    protected void onPostExecute(ArrayList<String> imagePath) {
        super.onPostExecute(imagePath);
    }
//取消耗时任务
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}

