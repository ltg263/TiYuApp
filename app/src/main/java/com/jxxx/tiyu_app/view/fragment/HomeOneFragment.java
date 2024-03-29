package com.jxxx.tiyu_app.view.fragment;


import android.app.Activity;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.BaseFragment;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.SceduleCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.tcp_tester.SelectActivity;
import com.jxxx.tiyu_app.utils.CustomPopWindow;
import com.jxxx.tiyu_app.utils.RadioGroupSelectUtils;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.activity.CeShiShuJuAct;
import com.jxxx.tiyu_app.view.activity.HomeOneChuangJianSjActivity;
import com.jxxx.tiyu_app.view.activity.HomeTwoShangKeActivity;
import com.jxxx.tiyu_app.view.adapter.HomeOneAdapter;
import com.jxxx.tiyu_app.view.adapter.HomeOneAdapterBk;
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

    @BindView(R.id.rb_home_select1)
    RadioButton mRbHomeSelect1;
    @BindView(R.id.rb_home_select2)
    RadioButton mRbHomeSelect2;
    @BindView(R.id.rb_home_select3)
    RadioButton mRbHomeSelect3;
    @BindView(R.id.rb_home_select4)
    RadioButton mRbHomeSelect4;
    @BindView(R.id.mRadioGroup)
    RadioGroup mMRadioGroup;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.et_suosou)
    EditText mEtSuosou;
    @BindView(R.id.tv_beike)
    TextView mTvBeiKe;
    @BindView(R.id.iv_beike)
    ImageView mIvBeiKe;
    @BindView(R.id.tv_dajie)
    TextView mTvDajie;
    @BindView(R.id.iv_dajie)
    ImageView mIvDajie;
    @BindView(R.id.iv_shanxuan)
    ImageView iv_shanxuan;
    @BindView(R.id.tv_xiaojie)
    TextView mTvXiaojie;
    @BindView(R.id.iv_xiaojie)
    ImageView mIvXiaojie;
    @BindView(R.id.rv_one_list_bk)
    RecyclerView mRvOneListBk;
    @BindView(R.id.rv_one_list)
    RecyclerView mRvOneList;
    @BindView(R.id.ll_shaixuan)
    LinearLayout ll_shaixuan;
    @BindView(R.id.rv_one_list_small)
    RecyclerView mRvOneListSmall;
    HomeOneAdapterBk mHomeOneAdapterBk;
    HomeOneAdapter mHomeOneAdapter;
    HomeOneAdapterSmall mHomeOneAdapterSmall;
    int page = 1;
    String courseName = null;
    String ageRange = null, contentType = null, category = null,theme = null,processType=null,trainType=null;
    RadioGroupSelectUtils mRadioGroupSelectUtils;
    String strToken = "";
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_one;
    }

    @Override
    protected void initView() {
        strToken = SharedUtils.getToken();
        iv_shanxuan.setSelected(false);
        refreshLayout.setRefreshHeader(new MaterialHeader(mContext).setShowBezierWave(false));
//        refreshLayout.setEnableRefresh(false);
//        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                if(mIvBeiKe.getVisibility()==View.VISIBLE){
                    getSceduleCourseList();
                }else if(mIvDajie.getVisibility()==View.VISIBLE){
                    getSchoolCourseList();
                }else{
                    getSchoolCourseListSmall();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                if(mIvBeiKe.getVisibility()==View.VISIBLE){
                    getSceduleCourseList();
                }else if(mIvDajie.getVisibility()==View.VISIBLE){
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
                            page=1;
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

        mRadioGroupSelectUtils = new RadioGroupSelectUtils();
        mRadioGroupSelectUtils.setDaKeJie(mIvDajie.getVisibility()==View.VISIBLE);
        mRadioGroupSelectUtils.setOnChangeListener(getActivity(),
                mMRadioGroup, mRbHomeSelect1, mRbHomeSelect2, mRbHomeSelect3, mRbHomeSelect4, new RadioGroupSelectUtils.DialogInterface() {
                    @Override
                    public void btnConfirm(String sys,String value) {
                        switch (sys){
                            case "sys_grade":
                                ageRange = value;
                                break;
                            case "sys_content_type":
                                contentType = value;
                                break;
                            case "sys_category":
                                category = value;
                                break;
                            case "sys_theme":
                                theme = value;
                                break;
                            case "sys_process_type":
                                processType=value;
                                break;
                            case "sys_train_type":
                                trainType=value;
                                break;
                        }
                        page=1;
                        if(mIvDajie.getVisibility()==View.VISIBLE){
                            getSchoolCourseList();
                        }else{
                            getSchoolCourseListSmall();
                        }
                    }
                });


    }

    @Override
    protected void initData() {
        mHomeOneAdapterBk = new HomeOneAdapterBk(null);
        mRvOneListBk.setAdapter(mHomeOneAdapterBk);
        mHomeOneAdapter = new HomeOneAdapter(null);
        mRvOneList.setAdapter(mHomeOneAdapter);
        mHomeOneAdapterSmall = new HomeOneAdapterSmall(null);
        mRvOneListSmall.setAdapter(mHomeOneAdapterSmall);
        mHomeOneAdapterBk.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SceduleCourseBean mSceduleCourseBean = mHomeOneAdapterBk.getData().get(position);
                Log.w("mSceduleCourseBean","mSceduleCourseBean:"+mSceduleCourseBean.getId());
                if(mSceduleCourseBean.getCourse()==null){
                    ToastUtil.showShortToast(mContext,"无绑定大课程");
                    return;
                }
                if(mSceduleCourseBean.getCourse().getCourseSectionVoList().size()==0){
                    ToastUtil.showShortToast(mContext,"无绑定小课程");
                    return;
                }
                if(mSceduleCourseBean.getClassScheduleCard()==null){
                    ToastUtil.showShortToast(mContext,"无绑定班级");
                    return;
                }
                String id = mSceduleCourseBean.getId();
                String mClassId = mSceduleCourseBean.getClassScheduleCard().getClassId();
                String mClassName = mSceduleCourseBean.getClassScheduleCard().getClassName();
                String mClassSceduleCardId = mSceduleCourseBean.getId();
                if(view.getId()==R.id.tv_kcxq){
                    HomeTwoShangKeActivity.startActivityIntentBk(mContext,id,mClassId,mClassName,mClassSceduleCardId,false);
                }
                if(view.getId()==R.id.tv_kssk){
                    ((BaseActivity)mContext).showLoading();
                    getSchoolSceduleCourseDetail(mContext,id,mClassId,mClassName,mClassSceduleCardId);
                }
            }
        });
        mHomeOneAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SchoolCourseBean mSchoolCourseBean = mHomeOneAdapter.getData().get(position);
                if(mSchoolCourseBean.getCourseSectionVoList().size()==0){
                    ToastUtil.showShortToast(mContext,"无绑定小课程");
                    return;
                }
                if(view.getId()==R.id.tv_kcxq){
                    HomeTwoShangKeActivity.startActivityIntent(mContext,mSchoolCourseBean.getId(),false);
                }
                if(view.getId()==R.id.tv_kssk){
                    DialogUtils.showDialogKeChengXiangQing(mContext, mSchoolCourseBean,null, new DialogUtils.ErrorDialogInterfaceA() {
                        @Override
                        public void btnConfirm(int index) {
                            HomeTwoShangKeActivity.startActivityIntent(mContext,mSchoolCourseBean.getId(),false);
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
        getSceduleCourseList();
    }
    public static void getSchoolSceduleCourseDetail(Context mContext, String id, String mClassId, String mClassName, String mClassSceduleCardId) {
        RetrofitUtil.getInstance().apiService()
                .getSchoolSceduleCourseDetail(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SceduleCourseBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SceduleCourseBean> result) {
                        if(result.getCode()==200 && result.getData()!=null){
                            SchoolCourseBean mData = result.getData().getCourse();
                            List<SchoolCourseBean.CourseSectionVoListBean> mCourseSectionVoList =  mData.getCourseSectionVoList();
                            mCourseSectionVoList.clear();
                            mCourseSectionVoList.addAll(result.getData().getSectionList());
                            mData.setCourseSectionVoList(mCourseSectionVoList);
                            DialogUtils.showDialogKeChengXiangQing(mContext, mData,
                                    null, new DialogUtils.ErrorDialogInterfaceA() {
                                @Override
                                public void btnConfirm(int index) {
                                    HomeTwoShangKeActivity.startActivityIntentBk(mContext,id,mClassId,mClassName,mClassSceduleCardId,false);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity)mContext).hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        ((BaseActivity)mContext).hideLoading();
                    }
                });
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
    @OnClick({R.id.tv_chuangjian, R.id.iv_suosou,R.id.ll_beike,R.id.ll_dajie, R.id.ll_xiaojie, R.id.ll_shaixuan,R.id.ceshishuju})
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
                page=1;
                refreshLayout.setNoMoreData(false);
                if(mIvBeiKe.getVisibility()==View.VISIBLE){
                    getSceduleCourseList();
                }else if(mIvDajie.getVisibility()==View.VISIBLE){
                    getSchoolCourseList();
                }else{
                    getSchoolCourseListSmall();
                }
                break;
            case R.id.ll_beike:
                setBekKeDate();
                break;
            case R.id.ll_dajie:
                setDaKeJieDate();
                break;
            case R.id.ll_xiaojie:
                setXiaoKeJieDate();
                break;
            case R.id.ll_shaixuan:
                if(mMRadioGroup.getVisibility()==View.VISIBLE){
                    iv_shanxuan.setSelected(false);
                    mMRadioGroup.setVisibility(View.GONE);
                }else{
                    iv_shanxuan.setSelected(true);
                    mMRadioGroup.setVisibility(View.VISIBLE);
                }
//                DialogUtils.showSelectDictType(mContext,ageRange,contentType,category,theme,
//                        processType,trainType,mIvDajie.getVisibility()==View.VISIBLE,
//                        MainActivity.mDictDataTypeBeans, new DialogUtils.SelectDictTypeDialogInterface() {
//                    @Override
//                    public void btnConfirm(String ageRange,String contentType,String category,String theme,String processType,String trainType) {
//                        HomeOneFragment.this.ageRange = ageRange;
//                        HomeOneFragment.this.contentType = contentType;
//                        if(mIvDajie.getVisibility()==View.VISIBLE){
//                            HomeOneFragment.this.category = category;
//                            HomeOneFragment.this.theme = theme;
//                            getSchoolCourseList();
//                        }else{
//                            HomeOneFragment.this.processType = processType;
//                            HomeOneFragment.this.trainType = trainType;
//                            getSchoolCourseListSmall();
//                        }
//                    }
//                });
                break;
            case R.id.ceshishuju:
//                startActivity(new Intent(mContext,CeShiShuJuAct.class));
                startActivity(new Intent(mContext, SelectActivity.class));
                break;
        }
    }

    private void setBekKeDate() {
        page = 1;
        mRadioGroupSelectUtils.setDaKeJie(false);
        initDictDataType();
        ll_shaixuan.setVisibility(View.INVISIBLE);
        iv_shanxuan.setSelected(false);
        mMRadioGroup.setVisibility(View.GONE);
        mRbHomeSelect1.setText("年级");
        mRbHomeSelect2.setText("教学内容");
        mRbHomeSelect3.setText("大类别");
        mRbHomeSelect4.setVisibility(View.GONE);
        mTvBeiKe.setTextColor(getResources().getColor(R.color.white));
        mTvDajie.setTextColor(getResources().getColor(R.color.white_46));
        mTvXiaojie.setTextColor(getResources().getColor(R.color.white_46));
        mTvBeiKe.setTextSize(16);
        mTvDajie.setTextSize(14);
        mTvXiaojie.setTextSize(14);
        mIvBeiKe.setVisibility(View.VISIBLE);
        mIvDajie.setVisibility(View.INVISIBLE);
        mIvXiaojie.setVisibility(View.INVISIBLE);
        mRvOneListBk.setVisibility(View.VISIBLE);
        mRvOneListSmall.setVisibility(View.GONE);
        mRvOneList.setVisibility(View.GONE);
        courseName = null;
        refreshLayout.setNoMoreData(false);
        getSceduleCourseList();
    }

    private void setXiaoKeJieDate() {
        page = 1;
        mRadioGroupSelectUtils.setDaKeJie(false);
        initDictDataType();
        ll_shaixuan.setVisibility(View.VISIBLE);
        iv_shanxuan.setSelected(false);
        mMRadioGroup.setVisibility(View.GONE);
        mRbHomeSelect1.setText("年级");
        mRbHomeSelect2.setText("教学内容");
        mRbHomeSelect3.setText("流程");
        mRbHomeSelect4.setText("核心指标");
        mRbHomeSelect4.setVisibility(View.VISIBLE);
        mTvBeiKe.setTextColor(getResources().getColor(R.color.white_46));
        mTvDajie.setTextColor(getResources().getColor(R.color.white_46));
        mTvXiaojie.setTextColor(getResources().getColor(R.color.white));
        mTvBeiKe.setTextSize(14);
        mTvDajie.setTextSize(14);
        mTvXiaojie.setTextSize(16);
        mIvBeiKe.setVisibility(View.INVISIBLE);
        mIvDajie.setVisibility(View.INVISIBLE);
        mIvXiaojie.setVisibility(View.VISIBLE);
        mRvOneListSmall.setVisibility(View.VISIBLE);
        mRvOneListBk.setVisibility(View.GONE);
        mRvOneList.setVisibility(View.GONE);
        courseName = null;
        refreshLayout.setNoMoreData(false);
        getSchoolCourseListSmall();
    }

    private void setDaKeJieDate() {
        page = 1;
        mRadioGroupSelectUtils.setDaKeJie(true);
        initDictDataType();
        ll_shaixuan.setVisibility(View.VISIBLE);
        iv_shanxuan.setSelected(false);
        mMRadioGroup.setVisibility(View.GONE);
        mRbHomeSelect1.setText("年级");
        mRbHomeSelect2.setText("教学内容");
        mRbHomeSelect3.setText("大类别");
        mRbHomeSelect4.setVisibility(View.GONE);
        mTvBeiKe.setTextColor(getResources().getColor(R.color.white_46));
        mTvDajie.setTextColor(getResources().getColor(R.color.white));
        mTvXiaojie.setTextColor(getResources().getColor(R.color.white_46));
        mTvBeiKe.setTextSize(14);
        mTvDajie.setTextSize(16);
        mTvXiaojie.setTextSize(14);
        mIvBeiKe.setVisibility(View.INVISIBLE);
        mIvDajie.setVisibility(View.VISIBLE);
        mIvXiaojie.setVisibility(View.INVISIBLE);
        mRvOneListSmall.setVisibility(View.GONE);
        mRvOneListBk.setVisibility(View.GONE);
        mRvOneList.setVisibility(View.VISIBLE);
        courseName = null;
        refreshLayout.setNoMoreData(false);
        getSchoolCourseList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!strToken.equals(SharedUtils.getToken())){
            if(mIvDajie.getVisibility()==View.VISIBLE){
                setDaKeJieDate();
            }else{
                setXiaoKeJieDate();
            }
        }
    }

    private void initDictDataType() {
        ageRange = null;
        contentType = null;
        category = null;
        theme = null;
        processType=null;
        trainType=null;
    }

    private void getSceduleCourseList() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSceduleCourseList(SharedUtils.singleton().get(ConstValues.TEACHER_ID,""),
                        SharedUtils.singleton().get(ConstValues.SCHOOL_ID,""),
                        courseName,ageRange,contentType,category,theme,
                        page,ConstValues.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SceduleCourseBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SceduleCourseBean>> result) {
                        if(isResultOk(result)){
                            if(page==1){
                                mHomeOneAdapterBk.setNewData(result.getData());
                            }else{
                                mHomeOneAdapterBk.addData(result.getData());
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
    private void getSchoolCourseList() {
        showLoading();
        RetrofitUtil.getInstance().apiService()
                .getSchoolCourseList(SharedUtils.singleton().get(ConstValues.TEACHER_ID,""),
                        SharedUtils.singleton().get(ConstValues.SCHOOL_ID,""),
                        courseName,ageRange,contentType,category,theme,
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
                            if(page==1){
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
                .getSchoolCourseListSmall(SharedUtils.singleton().get(ConstValues.TEACHER_ID,""),
                        SharedUtils.singleton().get(ConstValues.SCHOOL_ID,""),
                        null,courseName,ageRange,contentType,processType,trainType,"1","0",
                        page,ConstValues.PAGE_SIZE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolCourseBeanSmall>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolCourseBeanSmall>> result) {
                        if(isResultOk(result)){
                            if(page==1){
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



