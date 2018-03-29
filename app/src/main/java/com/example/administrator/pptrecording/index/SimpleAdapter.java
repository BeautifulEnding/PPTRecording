package com.example.administrator.pptrecording.index;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.pptrecording.R;
import com.jph.takephoto.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/12/20 0020.
 */
//extends RecyclerView.Adapter<RecyclerView.ViewHolder>
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.OriginViewHolder> implements RecyclerView.ChildDrawingOrderCallback{
    //    recycleView的适配器，要有ViewHolder
    private Context mContext;
    //    记录图像的路径
    private ArrayList<String> imagePaths;
    //    传给ViewHolder的整个布局
    private View mView;
    private ImageView leftImageView;
    private ImageView rightImageView;
    private ImageView bigImageView;
    private ImageSize imageSize;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();
    private int currentShow = 0;

    public SimpleAdapter(Context context, ArrayList<String> datas) {
//        Log.e("SimAdapter","正在执行SimpleAdapter---------------------------");
        mContext = context;
//        这里注意一定不要更改了数据源，不然会刷新失败
        imagePaths = datas;
        currentShow = 0;
        imageSize = new ImageSize(ScreenUtil.getScreenWidth(mContext) / 4, (int) (ScreenUtil.getScreenHeight(mContext) * 0.2));
    }

    /**
     * 创建ViewHolder，viewType不一样，创建的ViewHolder也不一样
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public SimpleAdapter.OriginViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_holder, parent, false);
        OriginViewHolder originViewHolder = new OriginViewHolder(mView);
        return originViewHolder;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final SimpleAdapter.OriginViewHolder holder, final int position) {
        ImageLoader.getInstance().loadImage(ImageDownloader.Scheme.FILE.wrap(imagePaths.get(position)), imageSize, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.e("SimpleAdapter ", "正在执行onLoadingStarted方法");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.image.setImageBitmap(loadedImage);

            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    出现图片选中边框
                showRect(v);
                if (position != 0) {
                    leftImageView.setVisibility(View.VISIBLE);
                } else {
                    leftImageView.setVisibility(View.GONE);
                }
                if (position != imagePaths.size() - 1) {
                    rightImageView.setVisibility(View.VISIBLE);
                } else {
                    rightImageView.setVisibility(View.GONE);
                }
                ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(imagePaths.get(position)), bigImageView);
                currentShow = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (imagePaths != null) {
            return imagePaths.size();
        } else {
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setData(ArrayList<String> objects) {
        imagePaths = objects;
        if (objects.size() > 0) {
            ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(objects.get(0)), bigImageView);
            currentShow = 0;
        }

        for (int i=0;i<imagePaths.size();i++){
            Log.e("imagePaths 路径  ",imagePaths.get(i));
            this.notifyItemInserted(i);
        }
    }

    /**
     *
     */
    public static class OriginViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public OriginViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.small_image);
        }
    }

    public void setImageViews(ImageView left, ImageView bigImage, ImageView right) {
        leftImageView = left;
        bigImageView = bigImage;
        rightImageView = right;
        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRect(v);
                if (rightImageView.getVisibility() == View.GONE) {
                    rightImageView.setVisibility(View.VISIBLE);
                }
                currentShow--;
                if (currentShow == 0) {
                    leftImageView.setVisibility(View.GONE);
                }
                if (imagePaths.size()>currentShow){
                    ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(imagePaths.get(currentShow)), bigImageView);
                }
            }
        });
        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRect(v);
                if (leftImageView.getVisibility() == View.GONE) {
                    leftImageView.setVisibility(View.VISIBLE);
                }
                currentShow++;
                if (currentShow == imagePaths.size() - 1) {
                    rightImageView.setVisibility(View.GONE);
                }
                if (imagePaths.size()>currentShow){
                    ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(imagePaths.get(currentShow)), bigImageView);
                }
            }
        });
    }

    @Override
    public int onGetChildDrawingOrder(int childCount, int i) {
        return currentShow;

    }

    private void showRect(View v) {
        if (Build.VERSION.SDK_INT >= 21) {
            ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).translationZ(1).start();

        }else {
            ViewCompat.animate(v).scaleX(1.17f).scaleY(1.17f).start();
            ViewGroup parent = (ViewGroup) v.getParent();
            parent.requestLayout();
            parent.invalidate();

        }
    }
}
