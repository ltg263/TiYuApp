package com.jxxx.tiyu_app.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * create by hj on 2020/4/25
 **/
public class ChartHelper {

    public static float getRandom(Float seed) {
        DecimalFormat mDecimalFormat = new DecimalFormat("#.00");
        Random mRandom = new Random();
        return Float.valueOf(mDecimalFormat.format(mRandom.nextFloat() * seed));
    }

    public static void addEntry(List<Entry> mData, LineChart lineChart, float yValues) {
        if (lineChart != null
                && lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            int size = mData.size();
            if (size == 0) {
                Entry entry = new Entry(maxCount, yValues);
                mData.add(entry);
            } else {
                boolean needRemove = false;
                for (Entry e : mData) {
                    float x = e.getX() - 1;
                    if (x < 0) {
                        needRemove = true;
                    }
                    e.setX(x);
                }
                if (needRemove) {
                    mData.remove(0);
                }
                Entry entry = new Entry(maxCount, yValues);
                mData.add(entry);
            }
            LineData lineData = new LineData(getSet(mData));
            lineData.setDrawValues(false);
            lineChart.setData(lineData);
            lineChart.invalidate();
        }
    }

    public static int maxCount = 10; //集合最大存储数量

    public static void addEntryYs(List<Entry> mData1, List<Entry> mData2, List<Entry> mData3, String[] resultSrt,
                                  LineChart lineChart,boolean is1,boolean is2, boolean is3) {
        if (lineChart != null&& lineChart.getData() != null) {
            if(resultSrt!=null){
                if (mData1.size() == 0) {
                    if(parseFloat(resultSrt[0])!=-1){
                        Entry entry1 = new Entry(maxCount, parseFloat(resultSrt[0]));
                        mData1.add(entry1);
                    }
                } else {
                    boolean needRemove = false;
                    for (Entry e : mData1) {
                        float x = e.getX() - 1;
                        if (x < 0) {
                            needRemove = true;
                        }
                        e.setX(x);
                    }
                    if (needRemove) {
                        mData1.remove(0);
                    }
                    if(parseFloat(resultSrt[0])!=-1){
                        Entry entry1 = new Entry(maxCount, parseFloat(resultSrt[0]));
                        mData1.add(entry1);
                    }
                }

                if (mData2.size() == 0) {
                    if(parseFloat(resultSrt[1])!=-1){
                        Entry entry2 = new Entry(maxCount, parseFloat(resultSrt[1]));
                        mData2.add(entry2);
                    }
                } else {
                    boolean needRemove = false;
                    for (Entry e : mData2) {
                        float x = e.getX() - 1;
                        if (x < 0) {
                            needRemove = true;
                        }
                        e.setX(x);
                    }
                    if (needRemove) {
                        mData2.remove(0);
                    }
                    if(parseFloat(resultSrt[1])!=-1){
                        Entry entry2 = new Entry(maxCount, parseFloat(resultSrt[1]));
                        mData2.add(entry2);
                    }
                }

                if (mData3.size() == 0) {
                    if(parseFloat(resultSrt[2])!=-1){
                        Entry entry3 = new Entry(maxCount, parseFloat(resultSrt[2]));
                        mData3.add(entry3);
                    }
                } else {
                    boolean needRemove = false;
                    for (Entry e : mData3) {
                        float x = e.getX() - 1;
                        if (x < 0) {
                            needRemove = true;
                        }
                        e.setX(x);
                    }
                    if (needRemove) {
                        mData3.remove(0);
                    }
                    if(parseFloat(resultSrt[2])!=-1){
                        Entry entry3 = new Entry(maxCount, parseFloat(resultSrt[2]));
                        mData3.add(entry3);
                    }
                }
            }
            LineData lineData = new LineData();
            if(is1){
                lineData.addDataSet(getSet(mData1,"#E24AC9"));
            }
            if(is2){
                lineData.addDataSet(getSet(mData2,"#4A90E2"));
            }
            if(is3){
                lineData.addDataSet(getSet(mData3,"#E2AF4A"));
            }
            lineData.setDrawValues(false);
            lineChart.setData(lineData);
            lineChart.invalidate();
        }
    }
    private static float parseFloat(String a){
        float b = 0;
        try {
            b =Float.parseFloat(a);
        } catch (Exception e) {
            e.printStackTrace();
            b = -1;
        }
        return b;
    }

    public static void initChart(List<Entry> mData, LineChart lineChart, long maxYValue) {
        initChart(mData,lineChart,maxYValue,null,null);
    }

    public static void initChart(List<Entry> mData, LineChart lineChart, long maxYValue,String color1,String color2) {

        int lineColor = Color.parseColor("#ebebeb");
        int textColor = Color.parseColor("#4A90E2");
        if(StringUtil.isNotBlank(color1)){
            lineColor = Color.parseColor(color1);
            textColor = Color.parseColor(color2);
        }

        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setEnabled(false);

        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setAxisMinimum(0);
        axisLeft.setLabelCount(5);
        if(maxYValue>0){
            axisLeft.setAxisMaximum(maxYValue);
        }
        axisLeft.setGridColor(lineColor);
        axisLeft.setTextColor(textColor);
        axisLeft.setAxisLineColor(lineColor);

        axisLeft.setDrawLabels(true);//不显示数值
        if(StringUtil.isNotBlank(color1)){
            // X轴可以缩放，Y轴不能缩放
            lineChart.setScaleXEnabled(true);
            lineChart.setScaleYEnabled(false);
            // 可以拖动，而不影响缩放比例
            lineChart.setDragEnabled(true);
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);//不显示数值
        if(StringUtil.isNotBlank(color1)){
            xAxis.setDrawLabels(true);//不显示数值
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(maxCount);
        xAxis.setLabelCount(maxCount);
        xAxis.setTextColor(textColor);

        Collections.sort(mData, new EntryXComparator());
        LineData lineData = new LineData(getSet(mData));


        lineData.setDrawValues(false);//不显示数值
        if(StringUtil.isNotBlank(color1)){
            lineData.setDrawValues(true);//不显示数值
        }

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private static LineDataSet getSet(List<Entry> mData,String color) {
        int valueColor = Color.parseColor(color);
        LineDataSet set = new LineDataSet(mData, "");
        set.setDrawFilled(false);
        set.setFillColor(valueColor);
        set.setColor(valueColor);
        set.setValueTextColor(valueColor);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return set;
    }

    private static LineDataSet getSet(List<Entry> mData) {
        int valueColor = Color.parseColor("#4A90E2");
        LineDataSet set = new LineDataSet(mData, "");
        set.setDrawFilled(true);
        set.setFillColor(valueColor);
        set.setColor(valueColor);
        set.setValueTextColor(valueColor);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return set;
    }
}
