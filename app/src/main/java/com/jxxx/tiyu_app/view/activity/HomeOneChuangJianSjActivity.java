package com.jxxx.tiyu_app.view.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.api.RetrofitUtil;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;
import com.jxxx.tiyu_app.utils.CustomPopWindow;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.WifiMessageReceiver;
import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.view.adapter.PopupWindowAdapter;
import com.jxxx.tiyu_app.wifi.WifiUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeOneChuangJianSjActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_select_sb)
    TextView mTvSelectSb;
    @BindView(R.id.tv_dengguangmoshi)
    TextView mTvDengGuangMoShi;
    @BindView(R.id.tv_shanshuo)
    TextView mTvShanshuo;
    @BindView(R.id.tv_yanse)
    TextView mTvYanse;
    @BindView(R.id.tv_chufa_fangshi)
    TextView mTvChufaFangshi;
    @BindView(R.id.tv_chixunsj)
    TextView mTvChiXunsj;
    @BindView(R.id.tv_sele_ssx)
    TextView tv_sele_ssx;
    @BindView(R.id.tv_xunhuancishu)
    TextView tv_xunhuancishu;
    @BindView(R.id.et_shebei_num)
    EditText et_shebei_num;
    @BindView(R.id.et_duilie_num)
    EditText et_duilie_num;
    @BindView(R.id.tv_zhixingmingling)
    TextView tv_zhixingmingling;
    @BindView(R.id.tv_zhixingshijian)
    EditText tv_zhixingshijian;
    @BindView(R.id.ll_sele_ssx)
    LinearLayout ll_sele_ssx;
    @BindView(R.id.ll_zhixingmingling)
    LinearLayout ll_zhixingmingling;
    WifiMessageReceiver mWifiMessageReceiver;
    //'sys_course_mode',
    // 'sys_ball_color', //???????????????
    // 'sys_plate_color', //??????????????????
    // 'sys_light_mode', //????????????
    // 'sys_flickering_rate', //????????????
    // 'sys_trigger_mode', //????????????
    // 'sys_trigger_after', //????????????
    List<String> lists = new ArrayList<>();
    /**
     * ????????????
     */
    static List<DictDataTypeBean> sys_course_mode = new ArrayList<>();
    /**
     * ???????????????
     */
    static List<DictDataTypeBean> sys_ball_color = new ArrayList<>();
    /**
     * ??????????????????
     */
    static List<DictDataTypeBean> sys_plate_color = new ArrayList<>();
    /**
     * ????????????
     */
    static List<DictDataTypeBean> sys_light_mode = new ArrayList<>();
    /**
     * ????????????
     */
    static List<DictDataTypeBean> sys_flickering_rate = new ArrayList<>();
    /**
     * ????????????
     */
    static List<DictDataTypeBean> sys_trigger_mode = new ArrayList<>();
    /**
     * ????????????
     */
    static List<DictDataTypeBean> sys_trigger_after = new ArrayList<>();
    //??????\????????????\????????????\????????????\????????????\????????????\????????????????????????
    byte[] sendData = new byte[]{ConstValuesHttps.MESSAGE_SEND_A0,0,0,0,0,0,0};
    @Override
    public int intiLayout() {
        return R.layout.activity_home_one_chuangjian;
    }

    @Override
    public void initView() {
        tv_sele_ssx.setVisibility(View.GONE);
        ll_sele_ssx.setVisibility(View.GONE);
        ll_zhixingmingling.setVisibility(View.GONE);
    }
    @OnClick({R.id.iv_back, R.id.tv_select_sb, R.id.tv_dengguangmoshi,R.id.tv_xunhuancishu,
            R.id.tv_shanshuo, R.id.tv_yanse,R.id.tv_zhixingmingling, R.id.tv_chufa_fangshi, R.id.tv_chixunsj, R.id.btn_kaishiyundong})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_select_sb:
                lists.clear();
                lists.add("?????????");
                lists.add("?????????");
                initPopupWindow(mTvSelectSb,null,lists,-1);
                break;
            case R.id.tv_chufa_fangshi:
                if(StringUtil.isBlank(mTvSelectSb.getText().toString())){
                    ToastUtil.showShortToast(this,"????????????????????????");
                    return;
                }
                initPopupWindow(mTvChufaFangshi,sys_trigger_mode,null,5);
                break;
            case R.id.tv_yanse:
                if(StringUtil.isBlank(mTvSelectSb.getText().toString())){
                    ToastUtil.showShortToast(this,"????????????????????????");
                    return;
                }
                initPopupWindow(mTvYanse,sys_ball_color,null,2);
                break;
            case R.id.tv_dengguangmoshi:
                if(StringUtil.isBlank(mTvSelectSb.getText().toString())){
                    ToastUtil.showShortToast(this,"????????????????????????");
                    return;
                }
                initPopupWindow(mTvDengGuangMoShi,sys_light_mode,null,-2);
                break;
            case R.id.tv_shanshuo:
                if(sendData[0]==ConstValuesHttps.MESSAGE_SEND_A0){
                    initPopupWindow(mTvShanshuo,sys_flickering_rate,null,3);
                }else{
                    lists.clear();
                    for(int i =1;i<6;i++){
                        lists.add(i+"s");
                    }
                    initPopupWindow(mTvShanshuo,null,lists,4);
                }
                break;
            case R.id.tv_chixunsj:
                lists.clear();
                if(sendData[0]==ConstValuesHttps.MESSAGE_SEND_A0){
                    lists.add("??????");
                    for(int i =1;i<61;i++){
                        lists.add(i+"s");
                    }
                    initPopupWindow(mTvChiXunsj,null,lists,4);
                }else{
                    for(int i =1;i<4;i++){
                        lists.add(i+"s");
                    }
                    initPopupWindow(mTvChiXunsj,null,lists,3);
                }
                break;
            case R.id.tv_xunhuancishu:
                lists.clear();
                if(sendData[0]==ConstValuesHttps.MESSAGE_SEND_A0){

                }else{
                    for(int i =1;i<256;i++){
                        lists.add(i+"???");
                    }
                }
                initPopupWindow(tv_xunhuancishu,null,lists,5);
                break;
            case R.id.tv_zhixingmingling:
                initPopupWindow(tv_zhixingmingling,sys_trigger_after,null,6);
                break;
            case R.id.btn_kaishiyundong:
                String sheBeiNum = et_shebei_num.getText().toString();
                String duilieNum = et_duilie_num.getText().toString();
                String zhixinshijian = tv_zhixingshijian.getText().toString();
                Log.w("sendData","sendData:"+ Arrays.toString(sendData));
                if(StringUtil.isBlank(sheBeiNum)){
                    ToastUtil.showShortToast(this,"??????????????????????????????");
                    return;
                }
                if(StringUtil.isBlank(duilieNum)){
                    ToastUtil.showShortToast(this,"?????????????????????");
                    return;
                }
                Log.w("sendData","?????????????????????:"+ sheBeiNum);
                Log.w("sendData","????????????:"+ duilieNum);
                int time = 0;
                if(StringUtil.isNotBlank(zhixinshijian)){
                    time = Integer.parseInt(zhixinshijian);
                }
                lianjie(Integer.parseInt(sheBeiNum),Integer.parseInt(duilieNum),time);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //????????????????????????
        try {
            if(mWifiMessageReceiver!=null){
                unregisterReceiver(mWifiMessageReceiver);
                mIntentFilter = null;
                mWifiMessageReceiver= null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            if(mWifiMessageReceiver!=null){
                unregisterReceiver(mWifiMessageReceiver);
                mIntentFilter = null;
                mWifiMessageReceiver= null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    IntentFilter mIntentFilter;
    private void lianjie(int sheBeiNum, int duilieNum,int time) {
        ConstValuesHttps.MESSAGE_ALL_TOTAL.clear();
        ConstValuesHttps.MESSAGE_ALL_TOTAL_ZJ.clear();
        ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.clear();
        /**
         * ??????????????????
         */
        Log.w("sendData","????????????:"+ (sheBeiNum*duilieNum));
        if(mWifiMessageReceiver == null){
            mWifiMessageReceiver = new WifiMessageReceiver(sheBeiNum*duilieNum, 1);//??????????????????
        }else {
            mWifiMessageReceiver.setSbNum(sheBeiNum * duilieNum);
            mWifiMessageReceiver.setDengGuang(1);
        }
        mWifiMessageReceiver.setSuiJi(true);
        mWifiMessageReceiver.setWifiMessageReceiverInter(new WifiMessageReceiver.WifiMessageReceiverInter() {
            @Override
            public void messageReceiverInter() {
                Log.w("sendData","messageReceiverInter");
                Intent mIntent = new Intent(HomeOneChuangJianSjActivity.this,HomeOneChuangJianSj_YdActivity.class);
                mIntent.putExtra("sendData",sendData);
                mIntent.putExtra("sheBeiNum",sheBeiNum);
                mIntent.putExtra("duilieNum",duilieNum);
                mIntent.putExtra("time",time);
                startActivity(mIntent);
            }
        });
        if(mIntentFilter == null){
            mIntentFilter = new IntentFilter(WifiMessageReceiver.START_BROADCAST_ACTION_START);// ??????IntentFilter??????
            registerReceiver(mWifiMessageReceiver, mIntentFilter);// ??????Broadcast Receive
        }
        ClientTcpUtils.mClientTcpUtils = new ClientTcpUtils(HomeOneChuangJianSjActivity.this);
    }
    private void initPopupWindow(TextView tvView, List<DictDataTypeBean> dictTypes,List<String> list,int pos) {
        List<String> lists = new ArrayList<>();
        if(dictTypes!=null){
            for(int i=0;i<dictTypes.size();i++){
                if(dictTypes.get(i)==null){
                    lists.add("??????");
                }else{
                    if(pos!=-2 || !dictTypes.get(i).getDictValue().equals("4")
                            || sendData[5] == 0){
                        lists.add(dictTypes.get(i).getDictLabel());
                    }
                }
            }
        }else{
            lists.addAll(list);
        }
        View view = getLayoutInflater().inflate(R.layout.popup_window_ty, null, false);
        RecyclerView rv_popup_list = view.findViewById(R.id.rv_popup_list);
        PopupWindowAdapter mPopupWindowAdapter = new PopupWindowAdapter(lists);
        rv_popup_list.setAdapter(mPopupWindowAdapter);
        //??????PopupWindow
        CustomPopWindow distancePopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(view)
                .setFocusable(true)//??????????????????????????????ture
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        rbDistance.setChecked(false);
                    }
                })
                .setBgDarkAlpha(0.5f)
                .enableBackgroundDark(true)
                .setOutsideTouchable(true)//??????PopupWindow ????????????dissmiss
                .size(tvView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)//????????????
                .create()//??????PopupWindow
                .showAsDropDown(tvView);//??????PopupWindow
        mPopupWindowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                distancePopWindow.dissmiss();
                if(pos==-1){
                    if(!tvView.getText().toString().equals(mPopupWindowAdapter.getData().get(position))){
                        tv_sele_ssx.setVisibility(View.GONE);
                        ll_sele_ssx.setVisibility(View.GONE);
                        ll_zhixingmingling.setVisibility(View.GONE);
                        mTvChufaFangshi.setText("");
                        mTvDengGuangMoShi.setText("");
                        mTvYanse.setText("");
                        sendData = new byte[]{ConstValuesHttps.MESSAGE_SEND_A0,0,0,0,0,0,0};
                    }
                    tvView.setText(mPopupWindowAdapter.getData().get(position));
                    return;
                }
                if(pos==-2){
                    mTvYanse.setVisibility(View.VISIBLE);
                    ll_zhixingmingling.setVisibility(View.VISIBLE);
                    ll_sele_ssx.setVisibility(View.VISIBLE);
                    mTvChiXunsj.setVisibility(View.VISIBLE);
                    mTvShanshuo.setVisibility(View.VISIBLE);
                    tv_xunhuancishu.setVisibility(View.VISIBLE);
                    switch (Byte.parseByte(dictTypes.get(position).getDictValue())){
                        case 1://??????
                            tv_sele_ssx.setText("?????????????????????");
                            tv_sele_ssx.setVisibility(View.VISIBLE);
                            ll_sele_ssx.setVisibility(View.VISIBLE);
                            mTvChiXunsj.setVisibility(View.VISIBLE);
                            mTvShanshuo.setVisibility(View.GONE);
                            tv_xunhuancishu.setVisibility(View.GONE);
                            mTvYanse.setVisibility(View.GONE);
                            ll_zhixingmingling.setVisibility(View.GONE);
                            sendData[0] = ConstValuesHttps.MESSAGE_SEND_A0;
                            break;
                        case 2://??????
                            tv_sele_ssx.setText("?????????????????????");
                            tv_sele_ssx.setVisibility(View.VISIBLE);
                            ll_sele_ssx.setVisibility(View.VISIBLE);
                            mTvChiXunsj.setVisibility(View.VISIBLE);
                            mTvShanshuo.setVisibility(View.GONE);
                            tv_xunhuancishu.setVisibility(View.GONE);
                            sendData[0] = ConstValuesHttps.MESSAGE_SEND_A0;
                            break;
                        case 3://??????
                            tv_sele_ssx.setText("????????????????????????????????????");
                            tv_sele_ssx.setVisibility(View.VISIBLE);
                            ll_sele_ssx.setVisibility(View.VISIBLE);
                            mTvChiXunsj.setVisibility(View.VISIBLE);
                            mTvShanshuo.setVisibility(View.VISIBLE);
                            tv_xunhuancishu.setVisibility(View.GONE);
                            sendData[0] = ConstValuesHttps.MESSAGE_SEND_A0;
                            break;
                        case 4://??????
                            tv_sele_ssx.setText("???????????????????????????????????????????????????");
                            tv_sele_ssx.setVisibility(View.VISIBLE);
                            ll_sele_ssx.setVisibility(View.VISIBLE);
                            mTvChiXunsj.setVisibility(View.VISIBLE);
                            mTvShanshuo.setVisibility(View.VISIBLE);
                            tv_xunhuancishu.setVisibility(View.VISIBLE);
                            sendData[0] = ConstValuesHttps.MESSAGE_SEND_A1;
                            break;
                    }
                    sendData[3] = 0;
                    sendData[4] = 0;
//                    sendData[5] = 0;
                    mTvChiXunsj.setText("");
                    mTvShanshuo.setText("");
                    tv_xunhuancishu.setText("");
                    tvView.setText(mPopupWindowAdapter.getData().get(position));
                    return;
                }
                tvView.setText(mPopupWindowAdapter.getData().get(position));
                if(list!=null){
                    if(pos==4){
                        String str = mPopupWindowAdapter.getData().get(position).replace("s","");
                        if(str.equals("??????")){
                            sendData[pos] = 61;
                        }else{
                            sendData[pos] = (byte) Integer.parseInt(str);
                        }
                    }else{
                        sendData[pos] = (byte) (position+1);
                    }
                    return;
                }
                if(dictTypes.get(position)==null){
                    sendData[pos] = -1;
                }else{
                    sendData[pos] = Byte.parseByte(dictTypes.get(position).getDictValue());
                }
                if(pos==5){//????????????
                    mTvDengGuangMoShi.setText("");
                    switch (sendData[pos]){
                        case 0://?????????
                            ll_sele_ssx.setVisibility(View.GONE);
                            tv_sele_ssx.setVisibility(View.GONE);
                            ll_zhixingmingling.setVisibility(View.GONE);
                            break;
                        case 1://??????
                        case 2://??????
                            ll_sele_ssx.setVisibility(View.GONE);
                            tv_sele_ssx.setVisibility(View.GONE);
                            ll_zhixingmingling.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
    }
    public static void getDictDataType(){
        getDictDataType("sys_course_mode");
        getDictDataType("sys_ball_color");
        getDictDataType("sys_plate_color");
        getDictDataType("sys_light_mode");
        getDictDataType("sys_flickering_rate");
        getDictDataType("sys_trigger_mode");
        getDictDataType("sys_trigger_after");
    }
    /**
     * ?????????????????????
     */
    private static void getDictDataType(String dictType) {
        RetrofitUtil.getInstance().apiService()
                .getDictDataType(dictType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Result<List<DictDataTypeBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result<List<DictDataTypeBean>> result) {
                        if(result.getCode()==200){
                            switch (dictType){
                                case "sys_course_mode":
                                    sys_course_mode.clear();
                                    sys_course_mode.addAll(result.getData());
                                    break;
                                case "sys_ball_color":
                                    sys_ball_color.clear();
                                    sys_ball_color.add(null);
                                    sys_ball_color.addAll(result.getData());
                                    break;
                                case "sys_plate_color":
                                    sys_plate_color.clear();
                                    sys_plate_color.add(null);
                                    sys_plate_color.addAll(result.getData());
                                    break;
                                case "sys_light_mode":
                                    sys_light_mode.clear();
                                    sys_light_mode.addAll(result.getData());
                                    sys_light_mode.remove(0);
                                    break;
                                case "sys_flickering_rate":
                                    sys_flickering_rate.clear();
                                    sys_flickering_rate.addAll(result.getData());
                                    break;
                                case "sys_trigger_mode":
                                    sys_trigger_mode.clear();
                                    sys_trigger_mode.addAll(result.getData());
                                    break;
                                case "sys_trigger_after":
                                    sys_trigger_after.clear();
                                    sys_trigger_after.addAll(result.getData());
                                    break;
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
