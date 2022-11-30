package com.jxxx.tiyu_app.tcp_tester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConstValuesHttps {

    /******↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓基础配置信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*******/
    /**
     * ip
     */
    public static String IPAdr = "192.168.5.1";//ip
    /**
     * 端口
     */
    public static int PORT = 1001;
    /**
     * 线程的等待时长
     */
    public static int THREAD_SLEEP = 50;

    /**
     * 课程所用所有的球
     */
    public static List<Byte> MESSAGE_ALL_TOTAL = new ArrayList<>();

    /**
     * 主机连接所有的球
     */
    public static List<Byte> MESSAGE_ALL_TOTAL_ZJ = new ArrayList<>();

    public static Map<Byte,Byte> MESSAGE_ALL_TOTAL_MAP = new HashMap<>();

    /**
     * 开头
     */
    public static byte MESSAGE_START = (byte) 0xFE;

    /**
     * 结尾
     */
    public static byte MESSAGE_END = (byte) 0xFF;


    /******↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓向服务端发送的指令↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*******/

    /**
     * 闪烁灯
     */
    public static byte MESSAGE_SEND_A0 = (byte) 0xA0;

    /**
     *  呼吸灯
     */
    public static byte MESSAGE_SEND_A1 = (byte) 0xA1;

    /**
     * 一键启动
     */
    public static byte MESSAGE_SEND_B0 = (byte) 0xB0;

    /**
     * 一键关机
     */
    public static byte MESSAGE_SEND_B1 = (byte) 0xB1;

    /**
     * 设置亮度
     */
    public static byte MESSAGE_SEND_B2 = (byte) 0xB2;

    /**
     * 设置数码管显示光电球的球号指令功能码
     */
    public static byte MESSAGE_SEND_B3 = (byte) 0xB3;


    /******↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓接受向服务端的指令↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*******/

    /**
     * 网关数据上报
     */
    public static byte MESSAGE_GET_C0 = (byte) 0xC0;

    /**
     * 触发后的状态反馈指令均为
     */
    public static byte MESSAGE_GET_C5 = (byte) 0xC5;

    /**
     * 倒计时结束的状态反馈指令均为
     */
    public static byte MESSAGE_GET_C6 = (byte) 0xC6;

    /**
     * 发送的数据
     * @param message
     * @param data
     * @return
     */
    public static byte[] getByteData(byte message, byte[] data) {
        List<Byte> lists = new ArrayList<>();
        lists.add(MESSAGE_START);
        lists.add((byte) 0x0A);
        lists.add((byte) 0x91);
        lists.add((byte) 0x90);
        lists.add(data[0]);
        lists.add((byte) 0x00);
        lists.add(message);
        for (int i = 0; i < data.length; i++) {
            if(i!=0){
                lists.add(data[i]);
            }
        }
        lists.add(MESSAGE_END);
        return listTobyte(lists);
    }

    /**
     * 转换成的byte数组
     */
    public static byte[] listTobyte(List<Byte> list) {
        if (list == null || list.size() == 0)
            return null;

        byte[] bytes = new byte[list.size()];
        int i = 0;
        Iterator<Byte> iterator = list.iterator();
        while (iterator.hasNext()) {
            bytes[i] = iterator.next();
            i++;
        }
        return bytes;
    }
}
