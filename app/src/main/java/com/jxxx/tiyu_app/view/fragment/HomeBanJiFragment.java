package com.jxxx.tiyu_app.view.fragment;

import android.os.Bundle;


import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.base.BaseFragment;

/**
 *
 */
public class HomeBanJiFragment extends BaseFragment {


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_banji;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
//            data = (MapListSposrt.ListBean) bundle.getSerializable("data");
        }
    }

    @Override
    protected void initData() {

    }
}
