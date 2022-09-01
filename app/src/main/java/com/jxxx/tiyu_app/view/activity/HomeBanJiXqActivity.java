package com.jxxx.tiyu_app.view.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.utils.view.StepArcView_n;
import com.jxxx.tiyu_app.view.adapter.HomeTwoTwoListAdapter;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeBanJiXqActivity extends BaseActivity {
    @BindView(R.id.rv_two_list)
    RecyclerView mRvTwoList;
    @BindView(R.id.sv_n)
    StepArcView_n mSvN;
    @BindView(R.id.tv_bfb)
    TextView tv_bfb;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    public int intiLayout() {
        return R.layout.activity_banji_xiangq;
    }

    @Override
    public void initView() {
        iv_back.setOnClickListener(v -> finish());
        tv_title.setText("203班成绩");
        mSvN.setCurrentCount(100,70,tv_bfb);
    }

    @Override
    public void initData() {
        getSchoolClassRecord();
    }
    private void getSchoolClassRecord() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolClassRecord(getIntent().getStringExtra("id"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolClassRecordBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolClassRecordBean> result) {
                        if (isResultOk(result) && result.getData() != null) {
                            mRvTwoList.setAdapter(new HomeTwoTwoListAdapter(null));
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
}
