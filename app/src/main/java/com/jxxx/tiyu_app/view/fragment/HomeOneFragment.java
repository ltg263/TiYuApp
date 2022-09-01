package com.jxxx.tiyu_app.view.fragment;


import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseFragment;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.utils.CustomPopWindow;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.activity.CeShiShuJuAct;
import com.jxxx.tiyu_app.view.activity.HomeOneChuangJianSjActivity;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;
import com.jxxx.tiyu_app.view.adapter.HomeOneAdapter;
import com.jxxx.tiyu_app.view.adapter.HomeOneAdapterSmall;
import com.jxxx.tiyu_app.view.adapter.HomeTwoType_SxAdapter;
import com.jxxx.tiyu_app.view.adapter.KeChengXiangQingAdapter;
import com.jxxx.tiyu_app.view.adapter.ShangKeBanJiAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeOneFragment extends BaseFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.et_suosou)
    EditText mEtSuosou;
    @BindView(R.id.tv_dajie)
    TextView mTvDajie;
    @BindView(R.id.iv_dajie)
    ImageView mIvDajie;
    @BindView(R.id.tv_xiaojie)
    TextView mTvXiaojie;
    @BindView(R.id.iv_xiaojie)
    ImageView mIvXiaojie;
    @BindView(R.id.rv_one_list)
    RecyclerView mRvOneList;
    @BindView(R.id.ll_shaixuan)
    LinearLayout ll_shaixuan;
    @BindView(R.id.rv_one_list_small)
    RecyclerView mRvOneListSmall;
    HomeOneAdapter mHomeOneAdapter;
    HomeOneAdapterSmall mHomeOneAdapterSmall;
    int page = 0;
    String courseName = null;
    String ageRange = null, trainType = null, trainPart = null;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView() {
        refreshLayout.setRefreshHeader(new MaterialHeader(mContext).setShowBezierWave(false));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                if(mIvDajie.getVisibility()==View.VISIBLE){
                    getSchoolCourseList();
                }else{
                    getSchoolCourseListSmall();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                if(mIvDajie.getVisibility()==View.VISIBLE){
                    getSchoolCourseList();
                }else{
                    getSchoolCourseListSmall();
                }
            }
        });
        mEtSuosou.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.w("onEditorAction","onEditorAction");
                //以下方法防止两次发送请求
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            courseName = mEtSuosou.getText().toString();
                            if(StringUtil.isBlank(courseName)){
                                ToastUtil.showLongStrToast(mContext,"所搜内容不能为空");
                                return true;
                            }
                            if(mIvDajie.getVisibility()==View.VISIBLE){
                                getSchoolCourseList();
                            }else{
                                getSchoolCourseListSmall();
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        mHomeOneAdapter = new HomeOneAdapter(null);
        mRvOneList.setAdapter(mHomeOneAdapter);
        mHomeOneAdapterSmall = new HomeOneAdapterSmall(null);
        mRvOneListSmall.setAdapter(mHomeOneAdapterSmall);
        mHomeOneAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.tv_kcxq){
                    HomeTwoShangKeActivity.startActivityIntent(mContext,mHomeOneAdapter.getData().get(position).getId(),false);
                }
                if(view.getId()==R.id.tv_kssk){

                    if(mHomeOneAdapter.getData().get(position).getCourseSectionVoList().size()==0){
                        return;
                    }
                    DialogUtils.showDialogKeChengXiangQing(mContext, mHomeOneAdapter.getData().get(position),null, new DialogUtils.ErrorDialogInterfaceA() {
                        @Override
                        public void btnConfirm(int index) {
                            HomeTwoShangKeActivity.startActivityIntent(mContext,mHomeOneAdapter.getData().get(position).getId(),false);
                        }
                    });
                }
            }
        });
        mHomeOneAdapterSmall.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.tv_kcxq){
                    HomeTwoShangKeActivity.startActivityIntent(mContext,mHomeOneAdapterSmall.getData().get(position).getId(),true);
                }
                if(view.getId()==R.id.tv_kssk){
                    getSchoolSmallCourseDetail(mHomeOneAdapterSmall.getData().get(position).getId());
                }
            }
        });
        getSchoolCourseList();
    }
    private void getSchoolSmallCourseDetail(String id) {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolSmallCourseDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolCourseBeanSmall>>() {



                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolCourseBeanSmall> result) {
                        if(isResultOk(result)){
                            DialogUtils.showDialogKeChengXiangQing(mContext, null,result.getData(), new DialogUtils.ErrorDialogInterfaceA() {
                                @Override
                                public void btnConfirm(int index) {
                                    HomeTwoShangKeActivity.startActivityIntent(mContext,id,true);
                                }
                            });
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
    @OnClick({R.id.tv_chuangjian, R.id.iv_suosou,R.id.ll_dajie, R.id.ll_xiaojie, R.id.ll_shaixuan,R.id.ceshishuju})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_chuangjian:
                startActivity(new Intent(mContext, HomeOneChuangJianSjActivity.class));
                break;
            case R.id.iv_suosou:
                courseName = mEtSuosou.getText().toString();
                if(StringUtil.isBlank(courseName)){
                    ToastUtil.showLongStrToast(mContext,"所搜内容不能为空");
                    return;
                }
                if(mIvDajie.getVisibility()==View.VISIBLE){
                    getSchoolCourseList();
                }else{
                    getSchoolCourseListSmall();
                }
                break;
            case R.id.ll_dajie:
//                page = 1;
                mTvDajie.setTextColor(getResources().getColor(R.color.white));
                mTvXiaojie.setTextColor(getResources().getColor(R.color.white_46));
                mTvDajie.setTextSize(16);
                mTvXiaojie.setTextSize(14);
                mIvDajie.setVisibility(View.VISIBLE);
                mIvXiaojie.setVisibility(View.INVISIBLE);
                mRvOneListSmall.setVisibility(View.GONE);
                mRvOneList.setVisibility(View.VISIBLE);
                courseName = null;
                getSchoolCourseList();
                break;
            case R.id.ll_xiaojie:
//                page = 1;
                mTvDajie.setTextColor(getResources().getColor(R.color.white_46));
                mTvXiaojie.setTextColor(getResources().getColor(R.color.white));
                mTvDajie.setTextSize(14);
                mTvXiaojie.setTextSize(16);
                mIvDajie.setVisibility(View.INVISIBLE);
                mIvXiaojie.setVisibility(View.VISIBLE);
                mRvOneListSmall.setVisibility(View.VISIBLE);
                mRvOneList.setVisibility(View.GONE);
                courseName = null;
                getSchoolCourseListSmall();
                break;
            case R.id.ll_shaixuan:
                DialogUtils.showSelectDictType(mContext,ageRange,trainType,trainType, MainActivity.mDictDataTypeBeans, new DialogUtils.SelectDictTypeDialogInterface() {
                    @Override
                    public void btnConfirm(String ageRange, String trainType, String trainPart) {
                        HomeOneFragment.this.ageRange = ageRange;
                        HomeOneFragment.this.trainType = trainType;
                        HomeOneFragment.this.trainType = trainPart;
                        if(mIvDajie.getVisibility()==View.VISIBLE){
                            getSchoolCourseList();
                        }else{
                            getSchoolCourseListSmall();
                        }
                    }
                });
                break;
            case R.id.ceshishuju:
                startActivity(new Intent(mContext,CeShiShuJuAct.class));
                break;
        }
    }

    private void getSchoolCourseList() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolCourseList(courseName,ageRange,trainType,trainPart,
                        page,ConstValues.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolCourseBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolCourseBean>> result) {
                        if(isResultOk(result)){
                            if(page==0){
                                mHomeOneAdapter.setNewData(result.getData());
                            }else{
                                mHomeOneAdapter.addData(result.getData());
                            }
                            int totalPage = StringUtil.getTotalPage(result.getTotal(), ConstValues.PAGE_SIZE);
                            if(totalPage <= page){
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        hideLoading();
                    }
                });
    }

    private void getSchoolCourseListSmall() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolCourseListSmall(courseName,ageRange,trainType,trainPart,page,ConstValues.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolCourseBeanSmall>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolCourseBeanSmall>> result) {
                        if(isResultOk(result)){
                            if(page==0){
                                mHomeOneAdapterSmall.setNewData(result.getData());
                            }else{
                                mHomeOneAdapterSmall.addData(result.getData());
                            }
                            int totalPage = StringUtil.getTotalPage(result.getTotal(), ConstValues.PAGE_SIZE);
                            if(totalPage <= page){
                                refreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                        hideLoading();
                    }
                });
    }

    private CustomPopWindow distancePopWindow;
    private void showDistancePopup() {

        View view = getLayoutInflater().inflate(R.layout.popup_window_sb, null, false);
        TextView tvTuiChu = view.findViewById(R.id.tv_tuichu);
        tvTuiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIvDajie.getVisibility()==View.VISIBLE){
                    getSchoolCourseList();
                }else{
                    getSchoolCourseListSmall();
                }
                distancePopWindow.dissmiss();
            }
        });
        //创建并显示popWindow
        distancePopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(view)
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        rbDistance.setChecked(false);
                    }
                })
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()//创建PopupWindow
                .showAsDropDown(ll_shaixuan, ll_shaixuan.getWidth(), 0);//显示PopupWindow
    }
}



