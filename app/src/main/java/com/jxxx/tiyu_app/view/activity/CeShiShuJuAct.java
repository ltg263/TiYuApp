package com.jxxx.tiyu_app.view.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.tcp_tester.ClientTcpUtils;
import com.jxxx.tiyu_app.tcp_tester.ConstValuesHttps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CeShiShuJuAct extends BaseActivity {
    @BindView(R.id.tv_c0)
    TextView mTvC0;
    @BindView(R.id.tv_shou)
    TextView mTvShou;
    @BindView(R.id.tv_fa)
    TextView mTvFa;
    @BindView(R.id.send)
    EditText mSend;
    @BindView(R.id.fasong)
    TextView mFasong;
    boolean isShowC0 = true;
    boolean isShow16 = true;
    @Override
    public int intiLayout() {
        return R.layout.act_ceshishuju;
    }

    @Override
    public void initView() {
        List<String> list = new ArrayList<>();
        ClientTcpUtils.mClientTcpUtils = new ClientTcpUtils(this, new ClientTcpUtils.ErrorDialogInterface() {
            @Override
            public void btnConfirm(String type, byte[] v) {
                if(v!=null){
                    if(list.size()>20){
                        list.clear();
                    }
                    if(v[0]== ConstValuesHttps.MESSAGE_START){
                        System.out.println("接收到的总数据(10):" + Arrays.toString(v));
                        System.out.println("接收到的总数据(16):" + ClientTcpUtils.BinaryToHexString(v));
                        if(isShowC0){
                            mTvC0.setText("接受的数据(过滤C0)");
                            if(isShow16){
                                list.add("\n"+ClientTcpUtils.BinaryToHexString(v));
                            }else{
                                list.add("\n"+Arrays.toString(v));
                            }
                        }else{
                            mTvC0.setText("接受的数据(显示C0)");
                            if(v[1] != ConstValuesHttps.MESSAGE_GET_C0){
                                if(isShow16){
                                    list.add("\n"+ClientTcpUtils.BinaryToHexString(v));
                                }else{
                                    list.add("\n"+Arrays.toString(v));
                                }
                            }
                        }
                    }else{
                        list.add("接收到的总数据:错误数据");
                    }
                }
                mTvShou.setText(list.toString());
            }
        });
        mTvC0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowC0 = !isShowC0;
                if(isShowC0){
                    mTvC0.setText("接受的数据(过滤C0)");
                }else{
                    mTvC0.setText("接受的数据(显示C0)");
                }
            }
        });
        mFasong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sondData = mSend.getText().toString();
                String[] sends = sondData.split(",");
                byte[] data = new byte[sends.length];
                for(int i=0;i<sends.length;i++){
                    data[i] = Byte.parseByte(sends[i]);
                }
                byte[] mData = getByteData(data);
                System.out.println("发送的数据-->>"+Integer.toHexString(mData[1] & 0xFF)+":" + Arrays.toString(mData));
                mTvFa.setText(Integer.toHexString(mData[1] & 0xFF)+":" + Arrays.toString(mData));
//                ClientTcpUtils.mClientTcpUtils.sendData_cs(mData);
//                ClientTcpUtils.mClientTcpUtils.sendData_B0();
            }
        });
    }


    /**
     * 发送的数据
     * @param data
     * @return
     */
    private static byte[] getByteData( byte[] data) {
        List<Byte> lists = new ArrayList<>();
        lists.add((byte) 0xFE);
        for (int i = 0; i < data.length; i++) {
            lists.add(data[i]);
        }
        lists.add((byte) 0xFF);
        return ConstValuesHttps.listTobyte(lists);
    }
    @Override
    public void initData() {

    }
}
