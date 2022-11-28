package com.jxxx.tiyu_app.view.activity;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.view.StepArcView_n;
import com.jxxx.tiyu_app.view.adapter.HomeBanJiXqAdapter;

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
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_type_1)
    TextView mTvType1;
    @BindView(R.id.tv_type_2)
    TextView mTvType2;
    @BindView(R.id.tv_absenteesNum)
    TextView mTvAbsenteesNum;
    @BindView(R.id.tv_traineesNum)
    TextView mTvTraineesNum;

    @Override
    public int intiLayout() {
        return R.layout.activity_banji_xiangq;
    }

    @Override
    public void initView() {
        iv_back.setOnClickListener(v -> finish());
        tv_title.setText(getIntent().getStringExtra("name"));
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
                            initDataV(result.getData());
                            mRvTwoList.setAdapter(new HomeBanJiXqAdapter(result.getData().getStudentResultsList()));
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

    private void initDataV(SchoolClassRecordBean data) {
        GlideImgLoader.loadImageViewRadiusNoCenter(this, data.getImgUrl(), mIvIcon);
        mTvAbsenteesNum.setText(data.getAbsenteesNum());
        mTvTraineesNum.setText(data.getTraineesNum());
        mTvName.setText(data.getCourseName());
        mTvUserName.setText(data.getCourseName());
        mTvType1.setText(data.getLabels());
        double zrs = Double.parseDouble(data.getTraineesNum());
        double wcrs = Double.parseDouble(data.getAbsenteesNum());
        int b = (int) (zrs/wcrs*100.0);
        mSvN.setCurrentCount(100, b, tv_bfb);
    }
}

