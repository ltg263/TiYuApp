package com.jxxx.tiyu_app.utils.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.VersionResponse;
import com.jxxx.tiyu_app.utils.GlideImgLoader;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.view.activity.HomeTwoXueShengActivity;
import com.jxxx.tiyu_app.view.adapter.HomeTwoType_SxAdapter;
import com.jxxx.tiyu_app.view.adapter.KeChengXiangQingAdapter;
import com.jxxx.tiyu_app.view.adapter.KeChengXiangQingAdapterSmall;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import constant.UiType;
import listener.Md5CheckResultListener;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

public class DialogUtils {

    public static void showDialogHint(Context context, String title, boolean isOne, final ErrorDialogInterface dialogConfirm) {

        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_hine, null);
        TextView bt_ok = (TextView) view.findViewById(R.id.bt_confirm);
        TextView suanle = (TextView) view.findViewById(R.id.bt_suanle);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(title);
        if(isOne){
            suanle.setVisibility(View.GONE);
        }
        suanle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm();
                }
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }
    public static void showDialogHintSelect(Context context, final ErrorDialogInterfaceA dialogConfirm) {
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_jieshu_lianjie, null);
        TextView btn_kaishishangke = view.findViewById(R.id.btn_kaishishangke);
        TextView btn_tiaoguo = view.findViewById(R.id.btn_tiaoguo);
        ImageView iv_quxiao = view.findViewById(R.id.iv_quxiao);
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
            }
        });
        btn_kaishishangke.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(1);
                }
                dialog5.dismiss();
            }
        });
        btn_tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(0);
                }
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }

    public interface SelectDictTypeDialogInterface {
        /**
         * 确定
         */
        public void btnConfirm(String ageRange,String contentType,String category,String theme,String processType,String trainType);
    }
    public static void showSelectDictType(Context context,String ageRange,String contentType,String category,String theme,String processType,String trainType,boolean isDaJieKe, Map<String,List<DictDataTypeBean>> mDictDataTypeBeans, final SelectDictTypeDialogInterface dialogConfirm) {

        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_dict_type, null);
        TextView btn =  view.findViewById(R.id.btn);
        RecyclerView rv_list_1 =  view.findViewById(R.id.rv_list_1);
        RecyclerView rv_list_2 =  view.findViewById(R.id.rv_list_2);
        RecyclerView rv_list_3 =  view.findViewById(R.id.rv_list_3);
        RecyclerView rv_list_4 =  view.findViewById(R.id.rv_list_4);
        TextView tv1 =  view.findViewById(R.id.tv_1);
        TextView tv2 =  view.findViewById(R.id.tv_2);
        TextView tv3 =  view.findViewById(R.id.tv_3);
        TextView tv4 =  view.findViewById(R.id.tv_4);
        tv1.setText("年级");
        tv2.setText("教学内容");
        tv3.setText("大类别");
        tv4.setText("主题内容");

        HomeTwoType_SxAdapter sys_grade = new HomeTwoType_SxAdapter(mDictDataTypeBeans.get("sys_grade"));
        sys_grade.setDictValue(ageRange);
        rv_list_1.setAdapter(sys_grade);
        HomeTwoType_SxAdapter sys_content_type = new HomeTwoType_SxAdapter(mDictDataTypeBeans.get("sys_content_type"));
        sys_content_type.setDictValue(contentType);
        rv_list_2.setAdapter(sys_content_type);
        HomeTwoType_SxAdapter sys_category = new HomeTwoType_SxAdapter(mDictDataTypeBeans.get("sys_category"));
        sys_category.setDictValue(category);
        rv_list_3.setAdapter(sys_category);
        HomeTwoType_SxAdapter sys_theme = new HomeTwoType_SxAdapter(mDictDataTypeBeans.get("sys_theme"));
        sys_theme.setDictValue(theme);
        rv_list_4.setAdapter(sys_theme);

        HomeTwoType_SxAdapter sys_process_type = new HomeTwoType_SxAdapter(mDictDataTypeBeans.get("sys_process_type"));
        sys_process_type.setDictValue(processType);

        HomeTwoType_SxAdapter sys_train_type = new HomeTwoType_SxAdapter(mDictDataTypeBeans.get("sys_train_type"));
        sys_train_type.setDictValue(trainType);
        if(isDaJieKe){
            tv3.setText("流程");
            tv4.setText("核心指标");
            rv_list_3.setAdapter(sys_process_type);
            rv_list_4.setAdapter(sys_train_type);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(sys_grade.getDictValue(),sys_content_type.getDictValue(),
                            sys_category.getDictValue(),sys_theme.getDictValue(),
                            sys_process_type.getDictValue(),sys_train_type.getDictValue());
                }
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(true);
        dialog5.setContentView(view);
        dialog5.show();
    }

    /**
     * 所有课程已完成！请断开连接，成绩将自动上传！
     */
    public static void showDialogWanChengSuoYou(Context context,String title,String bnt, final ErrorDialogInterfaceA dialogConfirm) {
    //
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_wanchengsuoyou, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        ImageView iv_select = view.findViewById(R.id.iv_select);
//        if (bnt.equals("断开连接")) {
            view.findViewById(R.id.ll_lianjie).setVisibility(View.VISIBLE);
            iv_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_select.setSelected(!iv_select.isSelected());
                }
            });
//        }
        tv_title.setText(title);
        TextView btn_kaishishangke = view.findViewById(R.id.btn_kaishishangke);
        btn_kaishishangke.setText(bnt);
        ImageView iv_quxiao =view.findViewById(R.id.iv_quxiao);
        btn_kaishishangke.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(iv_select.isSelected()?0:1);
                }
                dialog5.dismiss();
            }
        });
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }

    /**
     * 开始上课
     */
    public static void showDialogKaiShiShangKe(Context context, SchoolCourseBean mSchoolCourseBean, final ErrorDialogInterfaceA dialogConfirm) {
    //
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_kaishishangke, null);
        TextView btn_kaishishangke = view.findViewById(R.id.btn_kaishishangke);
        ImageView iv_quxiao =view.findViewById(R.id.iv_quxiao);
        TextView tv_name =view.findViewById(R.id.tv_name);
        tv_name.setText(mSchoolCourseBean.getCourseName());
        TextView tv_type_1 =view.findViewById(R.id.tv_type_1);
        tv_type_1.setText("共"+mSchoolCourseBean.getCourseSectionVoList().size()+"个小节");
        TextView tv_type_2 =view.findViewById(R.id.tv_type_2);
        tv_type_2.setText(mSchoolCourseBean.getQueueInfo());
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("今日上午"+mSchoolCourseBean.getCourseName()+"课即将开始\n是否开始上课？");
        btn_kaishishangke.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(1);
                }
                dialog5.dismiss();
            }
        });
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }

    /**
     * 开始上课下节课
     */
    public static void showDialogKaiShiShangKeXiaYiJie(Context context, boolean isNoOk, SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoList, final ErrorDialogInterfaceA dialogConfirm) {

        //课程信息
        SchoolCourseBeanSmall mSchoolCourseBean = mCourseSectionVoList.getSmallCourseVo();
    //
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_kaishishangke_xiayijie, null);
        TextView btn_kaishishangke = view.findViewById(R.id.btn_kaishishangke);
        TextView btn_tiaoguo = view.findViewById(R.id.btn_tiaoguo);
        TextView tv_title = view.findViewById(R.id.tv_title);
        ImageView iv_icon = view.findViewById(R.id.iv_icon);
        if(isNoOk){
            btn_tiaoguo.setText("取消");
            tv_title.setText("确定结束此节课程进行下一节课吗？");
        }
        ImageView iv_quxiao =view.findViewById(R.id.iv_quxiao);
        GlideImgLoader.loadImageViewRadiusNoCenter(context,mSchoolCourseBean.getImgUrl(),iv_quxiao);
        TextView tv_name =view.findViewById(R.id.tv_name);
        tv_name.setText(mSchoolCourseBean.getCourseName());
        TextView tv_type_1 =view.findViewById(R.id.tv_type_1);
        tv_type_1.setText("共"+mSchoolCourseBean.getQueueNum()+"个队列  |  共" + mSchoolCourseBean.getStepNum() + "个步骤");
        GlideImgLoader.loadImageViewRadiusNoCenter(context,mSchoolCourseBean.getImgUrl(),iv_icon);
        TextView tv_type_2 =view.findViewById(R.id.tv_type_2);
        tv_type_2.setText(mSchoolCourseBean.getQueueInfo());
        btn_kaishishangke.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(1);
                }
                dialog5.dismiss();
            }
        });
        btn_tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null && !isNoOk){
                    dialogConfirm.btnConfirm(0);
                }
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }

    /**
     * 开始下一节课是否要寻球
     */
    public static void showDialogXiaYiJieIsXunQiu(Context context, SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoListBean, final ErrorDialogInterfaceA dialogConfirm) {
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_xiayijie_xunqiu, null);
        TextView btn_kaishishangke = view.findViewById(R.id.btn_kaishishangke);
        TextView btn_tiaoguo = view.findViewById(R.id.btn_tiaoguo);
        TextView tvsbnum = view.findViewById(R.id.tv_sbnum);
        if(mCourseSectionVoListBean != null){
            tvsbnum.setText("(下一节课使用"+mCourseSectionVoListBean.getBallNum()+"个光电球，"+mCourseSectionVoListBean.getPlateNum()+"块光电地板)");
        }
        btn_kaishishangke.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(1);
                }
                dialog5.dismiss();
            }
        });
        btn_tiaoguo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(0);
                }
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }

    /**
     * 课程详情
     */
    public static void showDialogKeChengXiangQing(Context context, SchoolCourseBean mSchoolCourseBean, SchoolCourseBeanSmall mSchoolCourseBeanSmall,final ErrorDialogInterfaceA dialogConfirm) {

        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_kechengxiangqing, null);
        Button btn_kaishishangke = view.findViewById(R.id.btn_kaishishangke);
        ImageView iv_quxiao =view.findViewById(R.id.iv_quxiao);
        TextView tv_title =  view.findViewById(R.id.tv_title);
        TextView tv_type =  view.findViewById(R.id.tv_type);
        RecyclerView rv_list = view.findViewById(R.id.rv_list);
        if(mSchoolCourseBean!=null){
            tv_title.setText("默认使用"+mSchoolCourseBean.getBallNum()+"个光电球，"+mSchoolCourseBean.getPlateNum()+"块光电地板");
            rv_list.setAdapter(new KeChengXiangQingAdapter(mSchoolCourseBean.getCourseSectionVoList()));
        }
        if(mSchoolCourseBeanSmall!=null){
            view.findViewById(R.id.iv_line).setVisibility(View.INVISIBLE);
            tv_type.setText("队列");
            tv_title.setText("默认使用"+mSchoolCourseBeanSmall.getBallNum()+"个光电球，"+mSchoolCourseBeanSmall.getPlateNum()+"块光电地板");
            List<SchoolCourseBeanSmall.StepGroupsBean> mStepGroups = mSchoolCourseBeanSmall.getStepGroups();
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
            rv_list.setAdapter(new KeChengXiangQingAdapterSmall(mCourseSteps));
        }

        btn_kaishishangke.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    dialogConfirm.btnConfirm(1);
                }
                dialog5.dismiss();
            }
        });
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }

    static int dingguang = 0;
    /**
     * 链接设备
     */
    public static void showDialogLianJieSheBei(Context context,boolean isShowQiXiao, int mBallNum,int mPlateNum, final ErrorDialogInterfaceLianJieSheBei dialogConfirm) {

        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_lianjieshebei, null);
        TextView tv_title = view.findViewById(R.id.tv_title_sb);
        Button btn_lianjie = view.findViewById(R.id.btn_lianjie);
        ImageView iv_quxiao = view.findViewById(R.id.iv_quxiao);
        EditText et_guangqiu_num = view.findViewById(R.id.et_guangqiu_num);
        EditText et_dianban_num = view.findViewById(R.id.et_dianban_num);
        et_guangqiu_num.setText(mBallNum+"");
        et_dianban_num.setText(mPlateNum+"");
//        et_guangqiu_num.setText("此堂课使用"+mBallNum+"个");
//        et_dianban_num.setText("此堂课使用"+mPlateNum+"个");
        et_guangqiu_num.setEnabled(false);
        et_dianban_num.setEnabled(false);
        TextView btn_1 = view.findViewById(R.id.btn_1);
        TextView btn_2 = view.findViewById(R.id.btn_2);
        TextView btn_3 = view.findViewById(R.id.btn_3);
        tv_title.setText("您还未连接光电球或光电地板");
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingguang = 0;
                btn_1.setTextColor(context.getResources().getColor(R.color.white));
                btn_1.setBackgroundResource(R.drawable.shape_radius_line_828df1_select);
                btn_2.setTextColor(context.getResources().getColor(R.color.color_999999));
                btn_2.setBackgroundResource(R.drawable.shape_radius_line_828df1);
                btn_3.setTextColor(context.getResources().getColor(R.color.color_999999));
                btn_3.setBackgroundResource(R.drawable.shape_radius_line_828df1);
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingguang = 1;
                btn_2.setTextColor(context.getResources().getColor(R.color.white));
                btn_2.setBackgroundResource(R.drawable.shape_radius_line_828df1_select);
                btn_1.setTextColor(context.getResources().getColor(R.color.color_999999));
                btn_1.setBackgroundResource(R.drawable.shape_radius_line_828df1);
                btn_3.setTextColor(context.getResources().getColor(R.color.color_999999));
                btn_3.setBackgroundResource(R.drawable.shape_radius_line_828df1);
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingguang = -1;
                btn_3.setTextColor(context.getResources().getColor(R.color.white));
                btn_3.setBackgroundResource(R.drawable.shape_radius_line_828df1_select);
                btn_2.setTextColor(context.getResources().getColor(R.color.color_999999));
                btn_2.setBackgroundResource(R.drawable.shape_radius_line_828df1);
                btn_1.setTextColor(context.getResources().getColor(R.color.color_999999));
                btn_1.setBackgroundResource(R.drawable.shape_radius_line_828df1);
            }
        });
        int finalMBallNum = mBallNum;
        int finalMPlateNum = mPlateNum;
        btn_lianjie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(dialogConfirm!=null){
                    String guangqiuNum = et_guangqiu_num.getText().toString();
                    if(StringUtil.isBlank(guangqiuNum)){
                        guangqiuNum = finalMBallNum +"";
                    }
                    String dianbanNum = et_dianban_num.getText().toString();
                    if(StringUtil.isBlank(dianbanNum)){
                        dianbanNum = finalMPlateNum +"";
                    }
                    if(Integer.parseInt(guangqiuNum)<finalMBallNum){
                        ToastUtil.showLongStrToast(context,"光电球不能少于"+finalMBallNum+"个");
                        return;
                    }
                    if(Integer.parseInt(guangqiuNum) > ConstValues.BALL_NUM_MAX){
                        ToastUtil.showLongStrToast(context,"光电球最多"+ConstValues.BALL_NUM_MAX+"个");
                        return;
                    }
                    if(Integer.parseInt(dianbanNum)<finalMPlateNum){
                        ToastUtil.showLongStrToast(context,"光电地板不能少于"+finalMPlateNum+"个");
                        return;
                    }
                    if(Integer.parseInt(dianbanNum) > ConstValues.PLATE_NUM_MAX){
                        ToastUtil.showLongStrToast(context,"光电地板最多"+ConstValues.PLATE_NUM_MAX+"个");
                        return;
                    }
                    dialog5.dismiss();
                    dialogConfirm.lianJieNum(StringUtil.isNotBlank(guangqiuNum)?Integer.parseInt(guangqiuNum):finalMBallNum
                            ,StringUtil.isNotBlank(dianbanNum)?Integer.parseInt(dianbanNum):finalMPlateNum, dingguang);
                }
            }
        });
        if(!isShowQiXiao){
            iv_quxiao.setVisibility(View.GONE);
        }
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.lianJieNum(-1,-1,-1);
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }
    static int currentCounts = 0;
    /**
     * 开始寻球
     */
    public static void showDialogXunQiu(Context context, String title,final ErrorDialogInterfaceA dialogConfirm) {
        currentCounts = 0;
        final Dialog dialog5 = new Dialog(context, R.style.selectorDialog);
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_kaishixunqiu, null);
        Button btn_xunqiu = view.findViewById(R.id.btn_xunqiu);
        ImageView iv_quxiao =view.findViewById(R.id.iv_quxiao);
        TextView tv_title =  view.findViewById(R.id.tv_title);
        TextView tv_bfb =  view.findViewById(R.id.tv_bfb);
        StepArcView_n mSvN = view.findViewById(R.id.sv_n);
        mSvN.setCurrentCount(10,currentCounts,tv_bfb);
        btn_xunqiu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(currentCounts==10){
                    if(dialogConfirm!=null){
                        dialogConfirm.btnConfirm(1);
                    }
                    dialog5.dismiss();
                    return;
                }
                currentCounts++;
                mSvN.setCurrentCount(10,currentCounts,tv_bfb);
            }
        });
        iv_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5.dismiss();
            }
        });
        dialog5.setCancelable(false);
        dialog5.setContentView(view);
        dialog5.show();
    }
    public interface ErrorDialogInterfaceLianJieSheBei {
        /**
         * 确定
         */
        public void lianJieNum(int guangQiu,int guangBan,int dingguang);
    }
    public interface ErrorDialogInterfaceA {
        /**
         * 确定
         */
        public void btnConfirm(int index);
    }

    public interface ErrorDialogInterface {
        /**
         * 确定
         */
        public void btnConfirm();
    }


    public static void goUpdating(Context mContext, VersionResponse data) {
        if(TextUtils.isEmpty(data.getDownloadPath())){
            return;
        }
        UpdateAppUtils.init(mContext);
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setCheckWifi(true);
        updateConfig.setNeedCheckMd5(false);
        updateConfig.setNotifyImgRes(R.mipmap.ic_logo);
        UiConfig uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.PLENTIFUL);
        uiConfig.setUpdateLogoImgRes(R.mipmap.ic_logo);
        uiConfig.setUpdateBtnBgRes(R.drawable.btn_shape_theme);

        UpdateAppUtils
                .getInstance()
                .apkUrl(data.getDownloadPath())
                .updateTitle("发现新版本:V"+data.getVersionNo())
                .updateContent("更新内容:"+Html.fromHtml(data.getUpdateContent()))
                .uiConfig(uiConfig)
                .updateConfig(updateConfig)
                .setMd5CheckResultListener(new Md5CheckResultListener() {
                    @Override
                    public void onResult(boolean result) {

                    }
                })
                .setUpdateDownloadListener(new UpdateDownloadListener() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onDownload(int progress) {

                    }

                    @Override
                    public void onFinish() {

                    }

                })
                .update();
    }
    public static void downLoad(String path,Context context)throws Exception {
        URL url = new URL(path);
        InputStream is = url.openStream();
        String end = path.substring(path.lastIndexOf("."));
        //打开手机对应的输出流,输出到文件中
        OutputStream os = context.openFileOutput("Cache_" + System.currentTimeMillis() + end, Context.MODE_PRIVATE);
        byte[] buffer = new byte[1024];
        int len = 0;
        //从输入六中读取数据,读到缓冲区中
        while ((len = is.read(buffer)) > 0) {
            os.write(buffer, 0, len);
        }
        //关闭输入输出流
        is.close();
        os.close();
    }
}
