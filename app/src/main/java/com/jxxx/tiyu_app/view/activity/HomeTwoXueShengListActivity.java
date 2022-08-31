package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.view.adapter.HomeTwoXueShengListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeTwoXueShengListActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    HomeTwoXueShengListAdapter mHomeTwoXueShengListAdapter;
    @Override
    public int intiLayout() {
        return R.layout.activity_home_two_xuesheng_list;
    }

    @Override
    public void initView() {
        mHomeTwoXueShengListAdapter = new HomeTwoXueShengListAdapter(null);
        rv_list.setAdapter(mHomeTwoXueShengListAdapter);
        mHomeTwoXueShengListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent(HomeTwoXueShengListActivity.this,HomeXueShengXqActivity.class);
                mIntent.putExtra("id",mHomeTwoXueShengListAdapter.getData().get(position).getId());
                startActivity(mIntent);
            }
        });

    }

    @Override
    public void initData() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolStudentList(getIntent().getStringExtra("classId"),0, ConstValues.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolStudentBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolStudentBean>> result) {
                        if(isResultOk(result) && result.getData()!=null){
                            mHomeTwoXueShengListAdapter.setNewData(result.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
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
