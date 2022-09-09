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
import com.jxxx.tiyu_app.app.ConstValues;
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
    @BindView(R.id.ll_sele_ssx)
    LinearLayout ll_sele_ssx;
    @BindView(R.id.ll_zhixingmingling)
    LinearLayout ll_zhixingmingling;

    WifiInfo mWifiInfo;
    WifiUtil mWifiUtil;
    //'sys_course_mode',
    // 'sys_ball_color', //光电球颜色
    // 'sys_plate_color', //光电地板颜色
    // 'sys_light_mode', //灯光模式
    // 'sys_flickering_rate', //闪烁频率
    // 'sys_trigger_mode', //触发模式
    // 'sys_trigger_after', //执行指令
    List<String> lists = new ArrayList<>();
    /**
     * 课程模式
     */
    List<DictDataTypeBean> sys_course_mode = new ArrayList<>();
    /**
     * 光电球颜色
     */
    List<DictDataTypeBean> sys_ball_color = new ArrayList<>();
    /**
     * 光电地板颜色
     */
    List<DictDataTypeBean> sys_plate_color = new ArrayList<>();
    /**
     * 灯光模式
     */
    List<DictDataTypeBean> sys_light_mode = new ArrayList<>();
    /**
     * 闪烁频率
     */
    List<DictDataTypeBean> sys_flickering_rate = new ArrayList<>();
    /**
     * 触发模式
     */
    List<DictDataTypeBean> sys_trigger_mode = new ArrayList<>();
    /**
     * 执行指令
     */
    List<DictDataTypeBean> sys_trigger_after = new ArrayList<>();
    //指令\设备地址\颜色设置\频闪设置\时长设置\触发方式\触发后的执行动作
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
                lists.add("光电球");
                lists.add("电地板");
                initPopupWindow(mTvSelectSb,null,lists,-1);
                break;
            case R.id.tv_chufa_fangshi:
                if(StringUtil.isBlank(mTvSelectSb.getText().toString())){
                    ToastUtil.showShortToast(this,"请先选择链接设备");
                    return;
                }
                initPopupWindow(mTvChufaFangshi,sys_trigger_mode,null,5);
                break;
            case R.id.tv_yanse:
                if(StringUtil.isBlank(mTvSelectSb.getText().toString())){
                    ToastUtil.showShortToast(this,"请先选择触发模式");
                    return;
                }
                initPopupWindow(mTvYanse,sys_plate_color,null,2);
                break;
            case R.id.tv_dengguangmoshi:
                if(StringUtil.isBlank(mTvSelectSb.getText().toString())){
                    ToastUtil.showShortToast(this,"请先选择触发模式");
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
                        lists.add(i+"次");
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
                Log.w("sendData","sendData:"+ Arrays.toString(sendData));
                if(StringUtil.isBlank(sheBeiNum)){
                    ToastUtil.showShortToast(this,"请输入每个队列的球数");
                    return;
                }
                if(StringUtil.isBlank(duilieNum)){
                    ToastUtil.showShortToast(this,"请输入队列数量");
                    return;
                }
                Log.w("sendData","每个队列的球数:"+ sheBeiNum);
                Log.w("sendData","队列数量:"+ duilieNum);

                mWifiUtil = new WifiUtil(this);
                mWifiInfo = mWifiUtil.getWifiManager().getConnectionInfo();

                if(!mWifiUtil.getWifiManager().isWifiEnabled() || !mWifiInfo.getSSID().contains("ESP8266")){
                    DialogUtils.showDialogHint(this, "请将WIFI连接到ESP8266", false, new DialogUtils.ErrorDialogInterface() {
                        @Override
                        public void btnConfirm() {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    return;
                }

                lianjie(Integer.parseInt(sheBeiNum),Integer.parseInt(duilieNum));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(HomeTwoShangKeActivity.mWifiMessageReceiver!=null){
            unregisterReceiver(HomeTwoShangKeActivity.mWifiMessageReceiver);
            HomeTwoShangKeActivity.mWifiMessageReceiver = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(HomeTwoShangKeActivity.mWifiMessageReceiver!=null){
            unregisterReceiver(HomeTwoShangKeActivity.mWifiMessageReceiver);
            HomeTwoShangKeActivity.mWifiMessageReceiver = null;
        }
    }

    private void lianjie(int sheBeiNum, int duilieNum) {
        ConstValuesHttps.MESSAGE_ALL_TOTAL.clear();
        ConstValuesHttps.MESSAGE_ALL_TOTAL_MAP.clear();
        /**
         * 广播动态注册
         */
        Log.w("sendData","球的总数:"+ (sheBeiNum*duilieNum));
        HomeTwoShangKeActivity.mWifiMessageReceiver = new WifiMessageReceiver(sheBeiNum*duilieNum, 1);//集成广播的类
        HomeTwoShangKeActivity.mWifiMessageReceiver.setSuiJi(true);
        HomeTwoShangKeActivity.mWifiMessageReceiver.setWifiMessageReceiverInter(new WifiMessageReceiver.WifiMessageReceiverInter() {
            @Override
            public void messageReceiverInter() {
                Log.w("sendData","messageReceiverInter");
                Intent mIntent = new Intent(HomeOneChuangJianSjActivity.this,HomeOneChuangJianSj_YdActivity.class);
                mIntent.putExtra("sendData",sendData);
                mIntent.putExtra("sheBeiNum",sheBeiNum);
                mIntent.putExtra("duilieNum",duilieNum);
                startActivity(mIntent);
            }
        });

        IntentFilter mIntentFilter = new IntentFilter(WifiMessageReceiver.START_BROADCAST_ACTION_START);// 创建IntentFilter对象
        registerReceiver(HomeTwoShangKeActivity.mWifiMessageReceiver, mIntentFilter);// 注册Broadcast Receive
        ClientTcpUtils.mClientTcpUtils = new ClientTcpUtils(HomeOneChuangJianSjActivity.this);
    }
    private void initPopupWindow(TextView tvView, List<DictDataTypeBean> dictTypes,List<String> list,int pos) {
        List<String> lists = new ArrayList<>();
        if(dictTypes!=null){
            for(int i=0;i<dictTypes.size();i++){
                if(pos!=-2 || !dictTypes.get(i).getDictValue().equals("4")
                        || sendData[5] == 0){
                    lists.add(dictTypes.get(i).getDictLabel());
                }
            }
        }else{
            lists.addAll(list);
        }
        View view = getLayoutInflater().inflate(R.layout.popup_window_ty, null, false);
        RecyclerView rv_popup_list = view.findViewById(R.id.rv_popup_list);
        PopupWindowAdapter mPopupWindowAdapter = new PopupWindowAdapter(lists);
        rv_popup_list.setAdapter(mPopupWindowAdapter);
        //创建PopupWindow
        CustomPopWindow distancePopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(view)
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
//                        rbDistance.setChecked(false);
                    }
                })
                .setBgDarkAlpha(0.5f)
                .enableBackgroundDark(true)
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .size(tvView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()//创建PopupWindow
                .showAsDropDown(tvView);//显示PopupWindow
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
                    switch (Byte.parseByte(dictTypes.get(position).getDictValue())){
                        case 1://灭灯
                            tv_sele_ssx.setVisibility(View.GONE);
                            ll_sele_ssx.setVisibility(View.GONE);
                            sendData[0] = ConstValuesHttps.MESSAGE_SEND_A0;
                            break;
                        case 2://亮灯
                            tv_sele_ssx.setText("请选择持续时间");
                            tv_sele_ssx.setVisibility(View.VISIBLE);
                            ll_sele_ssx.setVisibility(View.VISIBLE);
                            mTvChiXunsj.setVisibility(View.VISIBLE);
                            mTvShanshuo.setVisibility(View.GONE);
                            tv_xunhuancishu.setVisibility(View.GONE);
                            sendData[0] = ConstValuesHttps.MESSAGE_SEND_A0;
                            break;
                        case 3://闪烁
                            tv_sele_ssx.setText("请选择持续时间和闪烁频率");
                            tv_sele_ssx.setVisibility(View.VISIBLE);
                            ll_sele_ssx.setVisibility(View.VISIBLE);
                            mTvChiXunsj.setVisibility(View.VISIBLE);
                            mTvShanshuo.setVisibility(View.VISIBLE);
                            tv_xunhuancishu.setVisibility(View.GONE);
                            sendData[0] = ConstValuesHttps.MESSAGE_SEND_A0;
                            break;
                        case 4://呼吸
                            tv_sele_ssx.setText("请选择持续时间、闪烁频率和循环次数");
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
                    sendData[5] = 0;
                    mTvChiXunsj.setText("");
                    mTvShanshuo.setText("");
                    tv_xunhuancishu.setText("");
                    tvView.setText(mPopupWindowAdapter.getData().get(position));
                    return;
                }
                tvView.setText(mPopupWindowAdapter.getData().get(position));
                if(list!=null){
//                    if(pos==4){
                        sendData[pos] = (byte) (position+1);
//                    }
                    return;
                }
                sendData[pos] = Byte.parseByte(dictTypes.get(position).getDictValue());
                if(pos==5){//触发模式
                    mTvDengGuangMoShi.setText("");
                    switch (sendData[pos]){
                        case 0://不触发
                            ll_sele_ssx.setVisibility(View.GONE);
                            tv_sele_ssx.setVisibility(View.GONE);
                            ll_zhixingmingling.setVisibility(View.GONE);
                            break;
                        case 1://按压
                        case 2://振动
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
        getDictDataType("sys_course_mode");
        getDictDataType("sys_ball_color");
        getDictDataType("sys_plate_color");
        getDictDataType("sys_light_mode");
        getDictDataType("sys_flickering_rate");
        getDictDataType("sys_trigger_mode");
        getDictDataType("sys_trigger_after");
    }
    /**
     * 获取筛选的条件
     */
    private void getDictDataType(String dictType) {
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
                        if(isResultOk(result)){
                            switch (dictType){
                                case "sys_course_mode":
                                    sys_course_mode.addAll(result.getData());
                                    break;
                                case "sys_ball_color":
                                    sys_ball_color.addAll(result.getData());
                                    break;
                                case "sys_plate_color":
                                    sys_plate_color.addAll(result.getData());
                                    break;
                                case "sys_light_mode":
                                    sys_light_mode.addAll(result.getData());
                                    break;
                                case "sys_flickering_rate":
                                    sys_flickering_rate.addAll(result.getData());
                                    break;
                                case "sys_trigger_mode":
                                    sys_trigger_mode.addAll(result.getData());
                                    break;
                                case "sys_trigger_after":
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
