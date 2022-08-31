package com.jxxx.tiyu_app.view.activity;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.utils.view.StepArcView_n;
import com.jxxx.tiyu_app.view.adapter.HomeTwoTwoListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeBanJiXqActivity extends BaseActivity {
    @BindView(R.id.rv_two_list)
    RecyclerView mRvTwoList;
    @BindView(R.id.sv_n)
    StepArcView_n mSvN;
    @BindView(R.id.tv_bfb)
    TextView tv_bfb;

    @Override
    public int intiLayout() {
        return R.layout.activity_banji_xiangq;
    }

    @Override
    public void initView() {
        mSvN.setCurrentCount(100,70,tv_bfb);
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        mRvTwoList.setAdapter(new HomeTwoTwoListAdapter(null));
    }

    @Override
    public void initData() {

    }
}
