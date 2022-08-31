package com.jxxx.tiyu_app.view.activity;

import android.view.View;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.base.BaseActivity;

import butterknife.OnClick;

public class HomeOneChuangJianSjActivity extends BaseActivity {

    @Override
    public int intiLayout() {
        return R.layout.activity_home_one_chuangjian;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
