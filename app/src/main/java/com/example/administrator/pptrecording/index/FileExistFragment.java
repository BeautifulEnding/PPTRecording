package com.example.administrator.pptrecording.index;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.pptrecording.R;
import com.example.administrator.pptrecording.util.RecyclerViewUtils;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class FileExistFragment extends Fragment{
    private View rootView;
    private ViewPager mViewPager;
    private FileExistViewPagerAdapter pagerAdapter;
    private FragmentInterface fragmentInterface;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private HeaderAndFooterRecyclerViewAdapter headerAdapter;
    private RecycleViewAdapter recycleViewAdapter;
    public static Fragment newInstance(int position){
//        根据position来定位不同的图片，这里需要与recycleView结合
        FileExistFragment fragment=new FileExistFragment();
        Bundle args=new Bundle();
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.havefile_fragment, container, false);
        setFragmentManager();
        initView();
        initEvent();
        return rootView;
    }
    public void setFragmentManager(){
        this.fragmentManager=fragmentInterface.getFrManager();
    }
    public interface FragmentInterface{
         FragmentManager getFrManager();
    }
    private void initView(){
        pagerAdapter = new FileExistViewPagerAdapter(fragmentManager);
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(pagerAdapter);
        recyclerView=rootView.findViewById(R.id.recyclerView);
//        初始化recycleView
        initRecyclerView();

    }
    public void initRecyclerView() {
//        recycleViewAdapter=new SimpleAdapter(getActivity(),contents);
//        headerAdapter = new HeaderAndFooterRecyclerViewAdapter(recycleViewAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(headerAdapter);
//        RecyclerViewUtils.setHeaderView(recyclerView, null);
    }
    private void initEvent(){

    }

}
