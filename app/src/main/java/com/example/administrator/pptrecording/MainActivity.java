package com.example.administrator.pptrecording;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jph.takephoto.constant.Constant;
import com.example.administrator.pptrecording.index.DecideFragmentHelper;
import com.example.administrator.pptrecording.index.FileExistFragment;
import com.example.administrator.pptrecording.index.impl.DecideFragmentHelperImpl;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends TakePhotoActivity implements FileExistFragment.FragmentInterface{
    private String defaultFile;
    private String curFragmentTag;
    private TResult resultImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DecideFragmentHelper fragmentHelper=(DecideFragmentHelper)
                new DecideFragmentHelperImpl(getApplicationContext());
        defaultFile=fragmentHelper.decideFragment();
        if (defaultFile==null || defaultFile==""){
            fragmentHelper.switchFragment(
                    getSupportFragmentManager(),R.id.fragment_content, Constant.FRAGMENT_DEFAULT,curFragmentTag);
            curFragmentTag=Constant.FRAGMENT_DEFAULT;
        }else{
            fragmentHelper.switchFragment(
                    getSupportFragmentManager(),R.id.fragment_content, Constant.FRAGMENT_HAVEFILE,curFragmentTag);
            curFragmentTag=Constant.FRAGMENT_HAVEFILE;
        }
//新建ppt文件按钮
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                调用第三方选择照片module,最多选择100张图片
                getTakePhoto().onPickMultiple(100);
                setRelativeView(fab);
                resultImage=getTResult();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_scanFile) {
//            跳转到6，这可以单独做一个module
        }

        return super.onOptionsItemSelected(item);
    }

    public FragmentManager getFrManager(){
        return getSupportFragmentManager();
    }
}
