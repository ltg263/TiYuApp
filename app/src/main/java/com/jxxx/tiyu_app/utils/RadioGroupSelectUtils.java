package com.jxxx.tiyu_app.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.MainActivity;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.bean.CourseTypeListAllBean;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.view.adapter.PopupWindowAdapter_home;
import com.jxxx.tiyu_app.view.adapter.PopupWindowAdapter_home_type;

import java.util.List;


public class RadioGroupSelectUtils {

    public CustomPopWindow mCustomPopWindow1,mCustomPopWindow2,mCustomPopWindow3_d,mCustomPopWindow3_x,mCustomPopWindow4_d,mCustomPopWindow4_x;
    boolean isDaKeJie;

    public void setDaKeJie(boolean daKeJie) {
        isDaKeJie = daKeJie;
    }

    public void setOnChangeListener(Activity mContext , RadioGroup mMRadioGroup, RadioButton mRbHomeSelect1, RadioButton mRbHomeSelect2,
                                    RadioButton mRbHomeSelect3, RadioButton mRbHomeSelect4,DialogInterface mDialogInterface){
        mMRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home_select1:
                        mRbHomeSelect1.setSelected(true);
                        if (mCustomPopWindow1 == null)
                            mCustomPopWindow1 = showDistancePopup(mContext,mMRadioGroup,mRbHomeSelect1,
                                    "sys_grade",mDialogInterface);
                        else {
                            mCustomPopWindow1.showAsDropDown(mRbHomeSelect1);
                        }
                        break;
                    case R.id.rb_home_select2:
                        mRbHomeSelect2.setSelected(true);
                        if (mCustomPopWindow2 == null)
                            mCustomPopWindow2 = showDistancePopup(mContext,mMRadioGroup,mRbHomeSelect2,
                                    "sys_content_type",mDialogInterface);
                        else {
                            mCustomPopWindow2.showAsDropDown(mRbHomeSelect2);
                        }
                        break;
                    case R.id.rb_home_select3:
                        mRbHomeSelect3.setSelected(true);
                        if(isDaKeJie){
                            if(mCustomPopWindow3_d == null){
                                mCustomPopWindow3_d = showDistancePopup(mContext,mMRadioGroup,mRbHomeSelect3,
                                        "sys_category",mDialogInterface);
                            }else{
                                mCustomPopWindow3_d.showAsDropDown(mRbHomeSelect3);
                            }
                        }else{
                            if(mCustomPopWindow3_x == null){
                                mCustomPopWindow3_x = showDistancePopup(mContext,mMRadioGroup,mRbHomeSelect3,
                                        "sys_process_type",mDialogInterface);
                            }else{
                                mCustomPopWindow3_x.showAsDropDown(mRbHomeSelect3);
                            }
                        }
                        break;
                    case R.id.rb_home_select4:
                        mRbHomeSelect4.setSelected(true);
                        if(isDaKeJie){
                            if(mCustomPopWindow4_d == null){
                                mCustomPopWindow4_d = showDistancePopup(mContext,mMRadioGroup,mRbHomeSelect4,
                                        "sys_theme",mDialogInterface);
                            }else{
                                mCustomPopWindow4_d.showAsDropDown(mRbHomeSelect4);
                            }
                        }else{
                            if(mCustomPopWindow4_x == null){
                                mCustomPopWindow4_x = showDistancePopup(mContext,mMRadioGroup,mRbHomeSelect4,
                                        "sys_train_type",mDialogInterface);
                            }else{
                                mCustomPopWindow4_x.showAsDropDown(mRbHomeSelect4);
                            }
                        }
                        break;
                }
            }
        });
    }
    String mRbHomeSelectText = "";
    private CustomPopWindow showDistancePopup(Activity mContext,RadioGroup mMRadioGroup, RadioButton mRbHomeSelect,String sys,DialogInterface mDialogInterface) {

        View view = mContext.getLayoutInflater().inflate(R.layout.popup_window_ty, null, false);
        RecyclerView rv_popup_list = view.findViewById(R.id.rv_popup_list);
        View mView = view.findViewById(R.id.view);
        RecyclerView rv_popup_list_f = view.findViewById(R.id.rv_popup_list_f);
        List<DictDataTypeBean> mDictDataTypeBeans =  null;
        List<CourseTypeListAllBean> mCourseTypeListAllBeans =  null;
        if("sys_category".equals(sys)){
            mCourseTypeListAllBeans = MainActivity.mCourseTypeListAllBeans.get(sys);
        }else{
            mDictDataTypeBeans =  MainActivity.mDictDataTypeBeans.get(sys);
        }
        if(mCourseTypeListAllBeans!=null){
            rv_popup_list_f.setVisibility(View.VISIBLE);
            mView.setVisibility(View.VISIBLE);
            PopupWindowAdapter_home_type mPopupWindowAdapter_home_type = new PopupWindowAdapter_home_type(mCourseTypeListAllBeans);
            mPopupWindowAdapter_home_type.setYou(true);
            rv_popup_list.setAdapter(mPopupWindowAdapter_home_type);
            PopupWindowAdapter_home_type mPopupWindowAdapter_home_type_f = new PopupWindowAdapter_home_type(mCourseTypeListAllBeans.get(0).getChildren());
            rv_popup_list_f.setAdapter(mPopupWindowAdapter_home_type_f);
            CustomPopWindow distancePopWindow = new CustomPopWindow.PopupWindowBuilder(mContext).setView(view)
                    .setFocusable(true)//是否获取焦点，默认为ture
                    .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            mRbHomeSelect.setSelected(false);
                            mRbHomeSelect.setChecked(false);
                        }
                    })
                    .setBgDarkAlpha(0.5f)
//                .enableBackgroundDark(true)
                    .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                    .size(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                    .create()//创建PopupWindow
                    .showAsDropDown(mRbHomeSelect);//显示PopupWindow

            List<CourseTypeListAllBean> finalMCourseTypeListAllBeans = mCourseTypeListAllBeans;

            mPopupWindowAdapter_home_type.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    for(int i=0;i<mPopupWindowAdapter_home_type.getData().size();i++){
                        mPopupWindowAdapter_home_type.getData().get(i).setSelect(false);

                    }
                    mPopupWindowAdapter_home_type.getData().get(position).setSelect(true);
                    mRbHomeSelect.setText(mPopupWindowAdapter_home_type.getData().get(position).getTypeName());
                    mRbHomeSelectText = mPopupWindowAdapter_home_type.getData().get(position).getTypeName();
                    mPopupWindowAdapter_home_type.notifyDataSetChanged();
                    mDialogInterface.btnConfirm(sys,mPopupWindowAdapter_home_type.getData().get(position).getId());

                    for(int i=0;i<finalMCourseTypeListAllBeans.get(position).getChildren().size();i++){
                        finalMCourseTypeListAllBeans.get(position).getChildren().get(i).setSelect(false);
                    }
                    mPopupWindowAdapter_home_type_f.setNewData(finalMCourseTypeListAllBeans.get(position).getChildren());

                }
            });
            mPopupWindowAdapter_home_type_f.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    distancePopWindow.dissmiss();
                    for(int i=0;i<mPopupWindowAdapter_home_type_f.getData().size();i++){
                        mPopupWindowAdapter_home_type_f.getData().get(i).setSelect(false);
                    }
                    mPopupWindowAdapter_home_type_f.getData().get(position).setSelect(true);
                    mRbHomeSelect.setText(mRbHomeSelectText+"/"+mPopupWindowAdapter_home_type_f.getData().get(position).getTypeName());
                    mPopupWindowAdapter_home_type_f.notifyDataSetChanged();
                    mDialogInterface.btnConfirm(sys,mPopupWindowAdapter_home_type_f.getData().get(position).getId());
                }
            });
            return distancePopWindow;
        }

        if(mDictDataTypeBeans==null){
            return null;
        }
        PopupWindowAdapter_home mPopupWindowAdapter_home = new PopupWindowAdapter_home(mDictDataTypeBeans);
        rv_popup_list.setAdapter(mPopupWindowAdapter_home);
        //创建PopupWindow
        //是否获取焦点，默认为ture
        //是否PopupWindow 以外触摸dissmiss
        //显示大小
        //创建PopupWindow
        int size_w =  mRbHomeSelect.getWidth();
        int size_h =  ViewGroup.LayoutParams.WRAP_CONTENT;
        for(int i=0;i<mDictDataTypeBeans.size();i++){
            if(mDictDataTypeBeans.get(i).getDictLabel().length()>4){
                size_w = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
        }
        if(mDictDataTypeBeans.size()>5){
            size_h = 600;
        }

        CustomPopWindow distancePopWindow = new CustomPopWindow.PopupWindowBuilder(mContext).setView(view)
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mRbHomeSelect.setSelected(false);
                        mRbHomeSelect.setChecked(false);
                    }
                })
                .setBgDarkAlpha(0.5f)
//                .enableBackgroundDark(true)
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .size(size_w,size_h)//显示大小
                .create()//创建PopupWindow
                .showAsDropDown(mRbHomeSelect);//显示PopupWindow
        mPopupWindowAdapter_home.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                distancePopWindow.dissmiss();
                for(int i=0;i<mPopupWindowAdapter_home.getData().size();i++){
                    mPopupWindowAdapter_home.getData().get(i).setSelect(false);
                }
                mPopupWindowAdapter_home.getData().get(position).setSelect(true);
                mRbHomeSelect.setText(mPopupWindowAdapter_home.getData().get(position).getDictLabel());
                mPopupWindowAdapter_home.notifyDataSetChanged();
                mDialogInterface.btnConfirm(sys,mPopupWindowAdapter_home.getData().get(position).getDictValue());
            }
        });
        return distancePopWindow;
    }

    public interface DialogInterface {
        /**
         * 确定
         */
        public void btnConfirm(String sys,String value);
    }

}
