package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.view.adapter.HomeTwoOneListAdapter;
import com.jxxx.tiyu_app.view.adapter.HomeYiShangKeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeYiShangKeListActivity extends BaseActivity {

    @BindView(R.id.rv_one_list)
    RecyclerView mRvOneList;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    HomeTwoOneListAdapter mHomeTwoOneListAdapter;
    HomeYiShangKeListAdapter mHomeYiShangKeListAdapter;
    @Override
    public int intiLayout() {
        return R.layout.activity_home_yi_shangke_list;
    }

    @Override
    public void initView() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        mHomeTwoOneListAdapter = new HomeTwoOneListAdapter(null);
        mHomeTwoOneListAdapter.setId("0");
        mRvOneList.setAdapter(mHomeTwoOneListAdapter);
        mHomeTwoOneListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mHomeTwoOneListAdapter.setId(position+"");
                mHomeTwoOneListAdapter.notifyDataSetChanged();
            }
        });
        mHomeYiShangKeListAdapter = new HomeYiShangKeListAdapter(list);
        rv_list.setAdapter(mHomeYiShangKeListAdapter);
        mHomeYiShangKeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(HomeYiShangKeListActivity.this,HomeBanJiXqActivity.class));
            }
        });

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
