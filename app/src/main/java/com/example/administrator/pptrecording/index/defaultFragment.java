package com.example.administrator.pptrecording.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.pptrecording.R;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class DefaultFragment extends Fragment{
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.default_fragment,container,false);
        return rootView;
    }
}
