package com.jxxx.tiyu_app.view.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.ConstValues;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeTwoXueShengAdapter extends BaseQuickAdapter<SchoolClassBean.ClassGroupListBean, BaseViewHolder> {

    public HomeTwoXueShengAdapter(@Nullable List<SchoolClassBean.ClassGroupListBean> data) {
        super(R.layout.item_home_two_xuesheng, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SchoolClassBean.ClassGroupListBean item) {
        RecyclerView rv_list = helper.getView(R.id.rv_list);
        helper.setText(R.id.tv_name,"队列："+(helper.getLayoutPosition()+1));
        List<String> mStudentIds = Arrays.asList(item.getStudentIds().split(","));
        List<SchoolStudentBean> mSchoolStudentBeans = new ArrayList<>();
        for(int i = 0; i<ConstValues.mSchoolStudentInfoBean.size();i++){
            ConstValues.mSchoolStudentInfoBean.get(i).setAskForLeave(false);
            if(mStudentIds.contains(ConstValues.mSchoolStudentInfoBean.get(i).getId())){
                mSchoolStudentBeans.add(ConstValues.mSchoolStudentInfoBean.get(i));
            }
        }

//        List<String> list = new ArrayList<>();
//        list.add("闪烁灯");
//        list.add("呼吸灯");
//        list.add("启动");
//        list.add("关机");
        HomeTwoXueShengAdapter_z mHomeTwoXueShengAdapter_z = new HomeTwoXueShengAdapter_z(mSchoolStudentBeans);
        rv_list.setAdapter(mHomeTwoXueShengAdapter_z);
        mHomeTwoXueShengAdapter_z.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(item.isNotClassDl()){
                    return;
                }
                mHomeTwoXueShengAdapter_z.getData().get(position).setAskForLeave(!mHomeTwoXueShengAdapter_z.getData().get(position).isAskForLeave());
                mHomeTwoXueShengAdapter_z.notifyDataSetChanged();
            }
        });
    }

    class HomeTwoXueShengAdapter_z extends BaseQuickAdapter<SchoolStudentBean, BaseViewHolder> {

        public HomeTwoXueShengAdapter_z(@Nullable List<SchoolStudentBean> data) {
            super(R.layout.item_home_two_xuesheng_z, data);
        }
        @Override
        protected void convert(BaseViewHolder helper, SchoolStudentBean item) {
            helper.setBackgroundRes(R.id.srl,R.drawable.shape_radius_xuesheng_1)
                    .setText(R.id.tv_name,item.getStudentName())
                    .setText(R.id.tv_name_no,item.getStudentNo())
                    .setTextColor(R.id.tv_name_no,mContext.getResources().getColor(R.color.color_text_theme))
                    .setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.color_text_theme));
            if(item.isAskForLeave()){//请假
                helper.setBackgroundRes(R.id.srl,R.drawable.shape_radius_xuesheng_2)
                        .setTextColor(R.id.tv_name_no,mContext.getResources().getColor(R.color.color_999999))
                        .setTextColor(R.id.tv_name,mContext.getResources().getColor(R.color.color_999999));
            }

        }
    }
}

