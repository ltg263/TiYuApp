package com.jxxx.tiyu_app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.PostStudentResults;
import com.jxxx.tiyu_app.bean.SceduleCourseBean;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;
import com.jxxx.tiyu_app.utils.CustomPopWindow;
import com.jxxx.tiyu_app.utils.SharedUtils;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.adapter.KeChengXiangQingAdapter;
import com.jxxx.tiyu_app.view.adapter.KeChengXiangQingAdapterSmall;
import com.jxxx.tiyu_app.view.adapter.ShangKeBanJiAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeTwoShangKeActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.rv_list_small)
    RecyclerView rv_list_small;
    @BindView(R.id.rv_two_list)
    RecyclerView rv_two_list;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_banji)
    TextView mTvBanji;
    @BindView(R.id.tv_lianjie)
    TextView mTvLianjie;
    @BindView(R.id.tv_shu)
    TextView tv_shu;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_xunhuancishu)
    TextView tv_xunhuancishu;
    @BindView(R.id.btn_kaishishangke)
    TextView btn_kaishishangke;
    ShangKeBanJiAdapter mShangKeBanJiAdapter;
    KeChengXiangQingAdapter mKeChengXiangQingAdapter;
    KeChengXiangQingAdapterSmall mKeChengXiangQingAdapterSmall;
    boolean isSmallCourse;
    String mClassId,mClassName;
    private WifiMessageReceiver mWifiMessageReceiver;
    List<String> list = new ArrayList<>();
    int queueNum = 0;
    Intent mIntent;
    @Override
    public int intiLayout() {
        return R.layout.activity_home_two_shangke;
    }

    @Override
    public void initView() {
        ConstValues.mSchoolCourseInfoBean = null;
        ConstValues.mSchoolCourseInfoBeanSmall = null;
        ConstValues.mSchoolClassInfoBean = null;
        ConstValues.mSchoolStudentInfoBean = null;
        ConstValues.classSceduleCardId = null;
        mIntent = getIntent();
        isSmallCourse = mIntent.getBooleanExtra("isSmallCourse",false);
        if(!isSmallCourse){
            ConstValues.classSceduleCardId = mIntent.getStringExtra("classSceduleCardId");
            mClassName = mIntent.getStringExtra("mClassName");
            mClassId = mIntent.getStringExtra("classId");
        }
        mKeChengXiangQingAdapter = new KeChengXiangQingAdapter(null);
        rv_list.setAdapter(mKeChengXiangQingAdapter);
        mKeChengXiangQingAdapterSmall = new KeChengXiangQingAdapterSmall(null);
        rv_list_small.setAdapter(mKeChengXiangQingAdapterSmall);
        mShangKeBanJiAdapter = new ShangKeBanJiAdapter(null);
        rv_two_list.setAdapter(mShangKeBanJiAdapter);
        mShangKeBanJiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SchoolClassBean mItem = mShangKeBanJiAdapter.getData().get(position);
                ConstValues.mSchoolClassInfoBean = mItem;
                mShangKeBanJiAdapter.setId(mItem.getId());
                mShangKeBanJiAdapter.notifyDataSetChanged();
                queueNum = mItem.getQueueNum();
                tv_grade.setText(mItem.getClassName()+"班级队列数"+mItem.getQueueNum()+"，队列最大人数"+mItem.getQueuePersonNum());
                mKeChengXiangQingAdapter.notifyDataSetChanged();
                showLoading();
                getSchoolStudentList(mItem.getId());
            }
        });
        mKeChengXiangQingAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoListBean = mKeChengXiangQingAdapter.getData().get(position);
                switch (view.getId()){
                    case R.id.tv_2:
                        list.clear();
                        for(int i=0;i<50;i++){
                            list.add(String.valueOf(i+1));
                        }
                        CustomPopWindow.initPopupWindow(HomeTwoShangKeActivity.this, view, list,
                                new CustomPopWindow.PopWindowInterface() {
                                    @Override
                                    public void getPosition(int position) {
                                        ((TextView)view).setText(list.get(position));
                                        mCourseSectionVoListBean.setLoopNum(Integer.parseInt(list.get(position)));
                                    }
                                });
                        break;
                    case R.id.tv_1:
//                        if(true){
//                            postSmallCourseCopyQueue((TextView) view,"", mCourseSectionVoListBean.getSmallCourseId(),"2");
//                            return;
//                        }
                        list.clear();
                        for(int i=0;i<queueNum;i++){
                            list.add((i+1)+"");
                        }
                        if(list.size()==0){
                            return;
                        }
                        CustomPopWindow.initPopupWindow(HomeTwoShangKeActivity.this,view, list,
                                new CustomPopWindow.PopWindowInterface() {
                                    @Override
                                    public void getPosition(int pos) {
//                                        int pos = Integer.parseInt(((TextView) view).getText().toString());
//                                        if(pos < position+2){
                                        ((TextView) view).setText(list.get(pos));
                                        postSmallCourseCopyQueue(mCourseSectionVoListBean.getSmallCourseId(),list.get(pos),position);
//                                        }
//                                        String mActionInfo = mCourseSectionVoListBean.getSmallCourseVo().getActionInfo();
//                                        JSONArray mJSONArray =  JSONArray.parseArray(mActionInfo);
//                                        Log.w("mActionInfo","mActionInfo"+mActionInfo);
//                                        if(mJSONArray.size()-1<position){
//                                            ToastUtil.showShortToast(HomeTwoShangKeActivity.this,"错误队列");
//                                            return;
//                                        }
//                                        Log.w("mActionInfo","mJSONArray.get(position):"+mJSONArray.get(position));
//                                        JSONObject mJSONObject = JSONObject.parseObject(mJSONArray.getJSONObject(position).toJSONString());
//                                        mJSONObject.put("groupNo",mJSONArray.size());
//                                        mJSONArray.add(mJSONObject);
//                                        Log.w("mActionInfo","mJSONArray"+mJSONArray.toJSONString());
                                    }
                                });
                        break;
                }
            }
        });
    }
    private void postSmallCourseCopyQueue(String mSmallCourseId,String num,int pos) {
        showLoading();
        SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoListBean = new SchoolCourseBean.CourseSectionVoListBean();
        mCourseSectionVoListBean.setId(mSmallCourseId);
        mCourseSectionVoListBean.setNum(num);
        RetrofitUtil.getInstance().apiService()
                .postSmallCourseCopyQueue(mSmallCourseId,num)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolCourseBeanSmall>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolCourseBeanSmall> result) {
                        Log.w("postSmallCourseCopyQueue","onNext");
                        if(isResultOk(result) && result.getData()!=null){
//                            ConstValues.mSchoolCourseInfoBean = result.getData();
                            mKeChengXiangQingAdapter.getData().get(pos).setQueueingNum(Integer.parseInt(num));
                            mKeChengXiangQingAdapter.getData().get(pos).setSmallCourseVo(result.getData());
                            mKeChengXiangQingAdapter.getData().get(pos).setBallNum(result.getData().getBallNum());
                            mKeChengXiangQingAdapter.getData().get(pos).setPlateNum(result.getData().getPlateNum());
                            mKeChengXiangQingAdapter.notifyDataSetChanged();
                            if(pos==0){
                                tv_shu.setText("本节课使用"+mKeChengXiangQingAdapter.getData().get(0).getBallNum()+"个光电球，"
                                        +mKeChengXiangQingAdapter.getData().get(0).getPlateNum()+"块光电地板");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("postSmallCourseCopyQueue","onError");
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }
    private void getSchoolStudentList(String classId) {
        RetrofitUtil.getInstance().apiService()
                .getSchoolStudentList(classId,null,null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<SchoolStudentBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<SchoolStudentBean>> result) {
                        if(isResultOk(result) && result.getData()!=null){
                            ConstValues.mSchoolStudentInfoBean = result.getData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("onError","e:"+e.toString());
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }

    @Override
    public void initData() {
        showLoading();
        //备课进入
        if(StringUtil.isNotBlank(ConstValues.classSceduleCardId)){
            tv_1.setText("");
            mTvBanji.setText("共"+1+"个班");
            getSchoolClassId();
            return;
        }
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
                            result.getData();
                            mTvBanji.setText("共"+result.getTotal()+"个班");
                            if(isSmallCourse){
                                tv_type.setText("队列");
                                mIv.setVisibility(View.GONE);
                                rv_list.setVisibility(View.GONE);
                                rv_list_small.setVisibility(View.VISIBLE);
                                tv_xunhuancishu.setVisibility(View.VISIBLE);
                                getSchoolSmallCourseDetail();
                            }else{
                                getSchoolCourseDetail();
                            }
                            mShangKeBanJiAdapter.setNewData(result.getData());
                            if(result.getData().size()>0) {
                                SchoolClassBean mSchoolClassBean = result.getData().get(0);
                                ConstValues.mSchoolClassInfoBean = mSchoolClassBean;
                                mShangKeBanJiAdapter.setId(mSchoolClassBean.getId());
                                getSchoolStudentList(mSchoolClassBean.getId());
                                queueNum = mSchoolClassBean.getQueueNum();
                                tv_grade.setText(mSchoolClassBean.getClassName()+"班级队列数"+mSchoolClassBean.getQueueNum()
                                        +"，队列最大人数"+mSchoolClassBean.getQueuePersonNum());
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
    private void getSchoolClassId() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolClassId(mClassId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolClassBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolClassBean> result) {
                        if (isResultOk(result) && result.getData() != null) {
                            SchoolClassBean mSchoolClassBean = result.getData();
                            ConstValues.mSchoolClassInfoBean = mSchoolClassBean;
                            mShangKeBanJiAdapter.addData(mSchoolClassBean);
                            mShangKeBanJiAdapter.setId(mSchoolClassBean.getId());
                            getSchoolStudentList(mSchoolClassBean.getId());
                            queueNum = mSchoolClassBean.getQueueNum();
                            tv_grade.setText(mSchoolClassBean.getClassName()+"班级队列数"+mSchoolClassBean.getQueueNum()
                                    +"，队列最大人数"+mSchoolClassBean.getQueuePersonNum());
                            getSchoolStudentList(mClassId);
                            getSchoolSceduleCourseDetail();
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

    private void getSchoolSceduleCourseDetail() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolSceduleCourseDetail(mIntent.getStringExtra("id"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SceduleCourseBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SceduleCourseBean> result) {
                        if(isResultOk(result) && result.getData()!=null){
                            SchoolCourseBean mData = result.getData().getCourse();
                            List<SchoolCourseBean.CourseSectionVoListBean> mCourseSectionVoList =  mData.getCourseSectionVoList();
                            mCourseSectionVoList.clear();
                            mCourseSectionVoList.addAll(result.getData().getSectionList());
                            mData.setCourseSectionVoList(mCourseSectionVoList);
                            ConstValues.mSchoolCourseInfoBean = mData;
                            mKeChengXiangQingAdapter.setNewData(mData.getCourseSectionVoList());
                            if(mData.getCourseSectionVoList().size()==0){
                                btn_kaishishangke.setText("无法上课");
                            }
                            tv_shu.setText("本节课使用"+mData.getBallNum()+"个光电球，"+mData+"块光电地板");
                            if(mData.getCourseSectionVoList()!=null && mData.getCourseSectionVoList().size()>0){
                                tv_shu.setText("本节课使用"+mData.getCourseSectionVoList().get(0).getBallNum()+"个光电球，"
                                        +mData.getCourseSectionVoList().get(0).getPlateNum()+"块光电地板");
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

    private void getSchoolCourseDetail() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolCourseDetail(mIntent.getStringExtra("id"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolCourseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolCourseBean> result) {
                        if(isResultOk(result) && result.getData()!=null){
                            ConstValues.mSchoolCourseInfoBean = result.getData();
                            mKeChengXiangQingAdapter.setNewData(result.getData().getCourseSectionVoList());
                            if(result.getData().getCourseSectionVoList().size()==0){
                                btn_kaishishangke.setText("无法上课");
                            }
                            tv_shu.setText("本节课使用"+result.getData().getBallNum()+"个光电球，"+result.getData().getPlateNum()+"块光电地板");
                            if(result.getData().getCourseSectionVoList()!=null && result.getData().getCourseSectionVoList().size()>0){
                                tv_shu.setText("本节课使用"+result.getData().getCourseSectionVoList().get(0).getBallNum()+"个光电球，"
                                        +result.getData().getCourseSectionVoList().get(0).getPlateNum()+"块光电地板");
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

    private void getSchoolSmallCourseDetail() {
        RetrofitUtil.getInstance().apiService()
                .getSchoolSmallCourseDetail(mIntent.getStringExtra("id"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<SchoolCourseBeanSmall>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<SchoolCourseBeanSmall> result) {
                        if(isResultOk(result)){
                            ConstValues.mSchoolCourseInfoBeanSmall = result.getData();
                            ConstValues.mSchoolCourseInfoBeanSmall.setLoopNum(1);
                            if(ConstValues.mSchoolCourseInfoBeanSmall!=null){
                                List<SchoolCourseBeanSmall.StepGroupsBean> mStepGroups = ConstValues.mSchoolCourseInfoBeanSmall.getStepGroups();
                                List<SchoolCourseBeanSmall.StepGroupsBean.CourseStepListBean> mCourseSteps = new ArrayList<>();
                                for(int i=0;i<mStepGroups.size();i++){
                                    List<SchoolCourseBeanSmall.StepGroupsBean.CourseStepListBean> mCourseStepList = mStepGroups.get(i).getCourseStepList();
                                    for(int j=0;j<mCourseStepList.size();j++){
                                        SchoolCourseBeanSmall.StepGroupsBean.CourseStepListBean mCourseStep = mCourseStepList.get(j);
                                        mCourseStep.setBuzhou_pos("步骤"+(j+1));
                                        if(j==0){
                                            mCourseStep.setDuilie_pos("队列" + (i + 1));
                                        }else{
                                            mCourseStep.setDuilie_pos("");
                                        }
                                        mCourseSteps.add(mCourseStep);
                                    }
                                }
                                mKeChengXiangQingAdapterSmall.setNewData(mCourseSteps);
                                if(mStepGroups.size()==0){
                                    btn_kaishishangke.setText("无法上课");
                                }
                                tv_shu.setText("本节课使用"+ConstValues.mSchoolCourseInfoBeanSmall.getBallNum()+"个光电球，"
                                        +ConstValues.mSchoolCourseInfoBeanSmall.getPlateNum()+"块光电地板");
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

    @OnClick({R.id.iv_back, R.id.tv_lianjie,R.id.tv_xunhuancishu,R.id.btn_kaishishangke})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_xunhuancishu:
                list.clear();
                for(int i=0;i<50;i++){
                    list.add(String.valueOf(i+1));
                }
                CustomPopWindow.initPopupWindow(HomeTwoShangKeActivity.this, view, list,
                        new CustomPopWindow.PopWindowInterface() {
                            @Override
                            public void getPosition(int position) {
                                tv_xunhuancishu.setText(list.get(position));
                                ConstValues.mSchoolCourseInfoBeanSmall.setLoopNum(Integer.parseInt(list.get(position)));
                            }
                        });
                break;
            case R.id.btn_kaishishangke:
//            case R.id.tv_lianjie:
                if(!btn_kaishishangke.getText().toString().equals("开始上课")){
                    return;
                }
                if(ConstValues.mSchoolCourseInfoBean==null && !isSmallCourse){
                    ToastUtil.showLongStrToast(this,"大课程信息获取失败");
                    return;
                }
                if(ConstValues.mSchoolCourseInfoBeanSmall==null && isSmallCourse){
                    ToastUtil.showLongStrToast(this,"小课程信息获取失败");
                    return;
                }
                if(ConstValues.mSchoolClassInfoBean==null){
                    ToastUtil.showLongStrToast(this,"班级信息获取失败");
                    return;
                }
                if(ConstValues.mSchoolClassInfoBean.getClassGroupList()==null || ConstValues.mSchoolClassInfoBean.getClassGroupList().size()==0){
                    ToastUtil.showLongStrToast(this,"该班级无队列");
                    return;
                }
                for(int i = ConstValues.mSchoolClassInfoBean.getClassGroupList().size()-1;i>=0;i--){
                    if(StringUtil.isBlank(ConstValues.mSchoolClassInfoBean.getClassGroupList().get(i).getStudentIds())){
                        ConstValues.mSchoolClassInfoBean.getClassGroupList().remove(i);
                    }
                }

                if(ConstValues.mSchoolStudentInfoBean == null || ConstValues.mSchoolStudentInfoBean.size()==0){
                    ToastUtil.showLongStrToast(this,"班级学生的信息获取失败");
                    return;
                }
                lianjie();
                break;
        }
    }

    boolean isAddClassRecord = false;//是否已经添加过上课记录
    private void postSchoolClassRecord() {
        String courseId = null,smallCourseId = null;
        if(isSmallCourse){
            smallCourseId = ConstValues.mSchoolCourseInfoBeanSmall.getId();
        }else{
            courseId = ConstValues.mSchoolCourseInfoBean.getId();
        }
        PostStudentResults mPostStudentResults = new PostStudentResults(
                StringUtil.getTimeToYMD(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"),
                ConstValues.mSchoolClassInfoBean.getId(),"1",
                SharedUtils.singleton().get(ConstValues.TEACHER_ID,""),courseId,smallCourseId);

        RetrofitUtil.getInstance().apiService()
                .postSchoolClassRecord(mPostStudentResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result>() {



                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        if(isResultOk(result)) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("BroadcastReceiver：onDestroy"+mWifiMessageReceiver);
        if(mWifiMessageReceiver!=null){
            unregisterReceiver(mWifiMessageReceiver);
            mWifiMessageReceiver = null;
        }
    }

    private void lianjie() {
        ConstValuesHttps.MESSAGE_ALL_TOTAL.clear();
        ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.clear();
        ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.clear();
        int mBallNum;
        int mPlateNum;
        int totalDuration;
        if(isSmallCourse){
            mBallNum = ConstValues.mSchoolCourseInfoBeanSmall.getBallNum();
            mPlateNum = ConstValues.mSchoolCourseInfoBeanSmall.getPlateNum();
            totalDuration = 0;
        }else{
            mBallNum =  ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(0).getBallNum();
            mPlateNum =  ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(0).getPlateNum();
            totalDuration =  ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(0).getTotalDuration();
        }
        DialogUtils.showDialogLianJieSheBei(this,true, mBallNum, mPlateNum,totalDuration,
                new DialogUtils.ErrorDialogInterfaceLianJieSheBei() {
                    @Override
                    public void lianJieNum(int guangQiu, int guangBan, int dengGuang,int totalDuration) {
                        if(guangQiu==-1 && guangBan==-1 && dengGuang==-1){
//                            if(mWifiMessageReceiver!=null){
//                                unregisterReceiver(mWifiMessageReceiver);
//                                mWifiMessageReceiver = null;
//                            }
                            return;
                        }
                        int sbNum = guangQiu+guangBan;
                        HomeTwoXueShengActivity.current_course_section = 0;
                        if(isSmallCourse){
                            ConstValues.mSchoolCourseInfoBeanSmall.setTotalDuration(totalDuration);
                        }else{
                            ConstValues.mSchoolCourseInfoBean.getCourseSectionVoList().get(0).setTotalDuration(totalDuration);
                        }
//                        int sbNum = 3;
                        /**
                         * 广播动态注册
                         */
                        if(mWifiMessageReceiver==null){
                            mWifiMessageReceiver = new WifiMessageReceiver(sbNum,dengGuang);//集成广播的类
                            IntentFilter mIntentFilter = new IntentFilter(WifiMessageReceiver.START_BROADCAST_ACTION_START);// 创建IntentFilter对象
                            registerReceiver(mWifiMessageReceiver, mIntentFilter);// 注册Broadcast Receive
                        }
                        ClientTcpUtils.mClientTcpUtils = new ClientTcpUtils(HomeTwoShangKeActivity.this);
                    }
                });
    }

    public static void startActivityIntentBk(Context mContext,String id,String classId,String mClassName,String classSceduleCardId,boolean isSmallCourse){
        Intent mIntent = new Intent(mContext, HomeTwoShangKeActivity.class);
        mIntent.putExtra("id",id);
        mIntent.putExtra("classSceduleCardId",classSceduleCardId);
        mIntent.putExtra("classId",classId);
        mIntent.putExtra("mClassName",mClassName);
        mIntent.putExtra("isSmallCourse",isSmallCourse);
        mContext.startActivity(mIntent);
    }
    public static void startActivityIntent(Context mContext,String id,boolean isSmallCourse){
        Intent mIntent = new Intent(mContext, HomeTwoShangKeActivity.class);
        mIntent.putExtra("id",id);
        mIntent.putExtra("isSmallCourse",isSmallCourse);
        mContext.startActivity(mIntent);
    }
    public static void startActivityIntentFragment(Context mContext){
        String id;
        Intent mIntent = new Intent(mContext, HomeTwoShangKeActivity.class);
        if(ConstValues.mSchoolCourseInfoBean!=null) {//大课程信息
            id = ConstValues.mSchoolCourseInfoBean.getId();
            mIntent.putExtra("isSmallCourse",false);
        }else{
            id = ConstValues.mSchoolCourseInfoBeanSmall.getId();
            mIntent.putExtra("isSmallCourse",true);
        }
        mIntent.putExtra("id",id);
        mContext.startActivity(mIntent);
    }
}
