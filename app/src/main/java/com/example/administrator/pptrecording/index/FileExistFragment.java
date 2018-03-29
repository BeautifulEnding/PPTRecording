package com.example.administrator.pptrecording.index;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.pptrecording.R;
import com.example.administrator.pptrecording.TestActivity;
import com.example.administrator.pptrecording.util.RecyclerViewUtils;
import com.jph.takephoto.imagepro.ImageProThread;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/13 0013.
 */
//imagePaths有两个来源，一个是图像处理后的路径，还有一个就是系统本身存在的文件路径
public class FileExistFragment extends Fragment implements HomeFragmentView{
    private View rootView;
//    private ViewPager mViewPager;
//    private FileExistViewPagerAdapter pagerAdapter;
//    private FragmentManager fragmentManager;
    private ImageView bigImage;
    private ImageView left;
    private ImageView right;
    private RecyclerView recyclerView;
    private HeaderAndFooterRecyclerViewAdapter headerAdapter;
    private SimpleAdapter recycleViewAdapter;
//    记录图像的路径
    private ArrayList<String> imagePaths=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.havefile_fragment, container, false);
//        setFragmentManager();
        initView();
        return rootView;
    }
//    public void setFragmentManager(){
//        this.fragmentManager=fragmentInterface.getFrManager();
//    }
    private void initView(){
        left=rootView.findViewById(R.id.left);
        bigImage=rootView.findViewById(R.id.container);
        right=rootView.findViewById(R.id.right);
        recyclerView=rootView.findViewById(R.id.recyclerView);
//        初始化recycleView
        initRecyclerView();
        if (imagePaths != null && imagePaths.size()>0){
            ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(imagePaths.get(0)),bigImage);
        }
    }
    public void initRecyclerView() {
        recycleViewAdapter=new SimpleAdapter(getActivity(),imagePaths);
        recycleViewAdapter.setImageViews(left,bigImage,right);
//        headerAdapter = new HeaderAndFooterRecyclerViewAdapter(recycleViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setChildDrawingOrderCallback(recycleViewAdapter);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
    }


    private void initEvent(){

    }

    @Override
    public void updateListView(ArrayList<String> statuselist) {
        imagePaths= statuselist;
        recycleViewAdapter.setData(statuselist);
        recycleViewAdapter.notifyDataSetChanged();
    }

}
