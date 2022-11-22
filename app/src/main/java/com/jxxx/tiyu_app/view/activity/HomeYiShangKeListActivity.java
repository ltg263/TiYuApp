package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.view.adapter.HomeYiShangKeListAdapter;
import com.jxxx.tiyu_app.view.adapter.ShangKeBanJi_jlAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeYiShangKeListActivity extends BaseActivity {

    @BindView(R.id.rv_one_list)
    RecyclerView mRvOneList;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.tv_not_data)
    TextView tv_not_data;
    ShangKeBanJi_jlAdapter mShangKeBanJi_jlAdapter;
    HomeYiShangKeListAdapter mHomeYiShangKeListAdapter;
    String name = "";
    @Override
    public int intiLayout() {
        return R.layout.activity_home_yi_shangke_list;
    }

    @Override
    public void initView() {
        mShangKeBanJi_jlAdapter = new ShangKeBanJi_jlAdapter(null);
        mRvOneList.setAdapter(mShangKeBanJi_jlAdapter);
        mShangKeBanJi_jlAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mShangKeBanJi_jlAdapter.setId(mShangKeBanJi_jlAdapter.getData().get(position).getId());
                mShangKeBanJi_jlAdapter.notifyDataSetChanged();
                name = mShangKeBanJi_jlAdapter.getData().get(position).getClassName();
                getSchoolClassRecordList(mShangKeBanJi_jlAdapter.getData().get(position).getId());
            }
        });
        mHomeYiShangKeListAdapter = new HomeYiShangKeListAdapter(null);
        rv_list.setAdapter(mHomeYiShangKeListAdapter);
        mHomeYiShangKeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent(HomeYiShangKeListActivity.this,HomeBanJiXqActivity.class);
                mIntent.putExtra("id",mHomeYiShangKeListAdapter.getData().get(position).getId());
                mIntent.putExtra("name",name);
                startActivity(mIntent);
            }
        });

    }

    @Override
    public void initData() {
        getSchoolClassList();
    }

    private void getSchoolClassList() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolClassList(SharedUtils.singleton().get(ConstValues.TEACHER_ID,""),
                        SharedUtils.singleton().get(ConstValues.SCHOOL_ID,""),0, ConstValues.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolClassBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolClassBean>> result) {
                        if(isResultOk(result) && result.getData()!=null){
                            if(result.getData().size()>0){
                                name = result.getData().get(0).getClassName();
                                mShangKeBanJi_jlAdapter.setId(result.getData().get(0).getId());
                                getSchoolClassRecordList(result.getData().get(0).getId());
                            }
                            mShangKeBanJi_jlAdapter.setNewData(result.getData());
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

    private void getSchoolClassRecordList(String classId) {
        RetrofitUtil.getInstance().apiService()
                .getSchoolClassRecordList(SharedUtils.singleton().get(ConstValues.TEACHER_ID,""),classId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolClassRecordBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolClassRecordBean>> result) {
                        if (isResultOk(result) && result.getData() != null) {
                            tv_not_data.setVisibility(View.VISIBLE);
                            rv_list.setVisibility(View.GONE);
                            mHomeYiShangKeListAdapter.setNewData(result.getData());
                            if(mHomeYiShangKeListAdapter.getData().size()>0){
                                rv_list.setVisibility(View.VISIBLE);
                                tv_not_data.setVisibility(View.GONE);
                            }
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
