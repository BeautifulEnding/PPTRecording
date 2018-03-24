package com.example.administrator.pptrecording.index;


import android.support.v4.app.*;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class FileExistViewPagerAdapter extends FragmentPagerAdapter{

    public FileExistViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FileExistFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

        }
        return null;
    }
}
