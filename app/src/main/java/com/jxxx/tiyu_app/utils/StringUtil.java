package com.jxxx.tiyu_app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.jxxx.tiyu_app.utils.view.DialogUtils;
import com.jxxx.tiyu_app.wifi.WifiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/4.
 */

public class StringUtil {

    public static String replaceNull(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }

    public static String replaceNullToZero(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0";
        }
        return str;
    }

    public static String replaceNullToOne(String str) {
        if (TextUtils.isEmpty(str)) {
            return "1";
        }
        return str;
    }


    public static String format(String format, Object v1, Object v2) {
        return String.format(format, v1, v2);
    }

    public static String replaceHtmlImgToAbsolutelyUrl(String baseUrl, String html) {
        Pattern pattern = Pattern.compile("src\\s*=\\s*\"(.+?)\"");
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String str = matcher.group(0);
            if (!str.contains("http://")) {
                matcher.appendReplacement(sb, str.substring(0, 5) + baseUrl + str.substring(6));
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }



    /**
     * ?????????????????????
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * ?????????????????????????????????????????? Luhm ???????????????????????????
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //??????????????????????????????N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * ??????????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * ???????????????????????????
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return ("".equals(str) || "null".equals(str) || str == null);
    }

    /**
     * ?????????utf8???????????????  ??????????????????
     *
     * @param text
     * @return
     */
    public static String filterOffUtf8Mb4(String text) {
        try {
            byte[] bytes = text.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                } else if ((b ^ 0xE0) >> 4 == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                } else if ((b ^ 0xF0) >> 4 == 0) {
                    i += 4;
                }
            }
            buffer.flip();
            return new String(buffer.array(), "utf-8");
        } catch (Exception e) {
            Log.e("Exception", e.getMessage().toString());
        }
        return text;
    }

    public static boolean isUTF8(String key) {
        try {
            key.getBytes("utf-8");
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    /**
     * ???????????????emoji??????
     *
     * @param source
     * @return FALSE???????????????
     */
    public static boolean containsEmoji(String source) {
        if (source.equals("")) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing???????????????????????????????????????????????????
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * ??????emoji ?????? ??????????????????????????????
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;//??????????????????????????????
        }
        //?????????????????????
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return source;//?????????????????? emoji??????????????????????????????
        } else {
            if (buf.length() == len) {
                //????????????????????????????????????toString?????????????????????????????????
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }

    /**
     * ???????????????????????????
     */
    public static void etSearchChangedListener(final EditText et, final View btn, final EtChange etChange) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et.getText().toString().trim().length() != 0) {
                    etChange.etYes();
                }
                if (et.getText().toString().trim().length() == 0) {
                    if (btn != null) {
                        btn.setSelected(false);
                    }
                    etChange.etNo();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * int double ????????????
     *
     * @param obj
     * @return
     */
    public static String getValue(String obj) {
        return getValue(Double.valueOf(obj));
    }
    public static String getValue(double obj) {
        if (isIntegerForDouble(obj)) {
            return String.valueOf((int) obj);
        }
        return String.format("%.2f", obj);
    }
    /**
     * ??????double???????????????
     *
     * @param obj
     * @return
     */
    public static boolean isIntegerForDouble(double obj) {
        double eps = 1e-10;  // ????????????
        return obj - Math.floor(obj) < eps;
    }

    public static String getTimeToYMD(long seconds, String layout) {
        Date d = new Date(seconds);
        SimpleDateFormat sdf = new SimpleDateFormat(layout);
        return sdf.format(d).toString();
    }

    /**
     * ??????: ??????????????????????????? ???????????????
     */
    public static long getMsToTime(String time, String layout) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat(layout).parse(time));
            return c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public abstract static class EtChange {
        //?????????
        public abstract void etYes();

        //?????????
        public void etNo() {
        }
    }

    public static String getTime (long time){
        if(time % 60!=0){
            if(time<60){
                return time+"s";
            }
            return time / 60+"m"+ time % 60+"s";
        }
        return time / 60+"m";
    }

    /**
     * ??????????????????????????????
     */
    public static ArrayList<String> getImgStr(String htmlStr) {
        ArrayList<String> pics = new ArrayList<>();
        String img = "";
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // ??????<img />??????
            img = m_image.group();
            // ??????<img>??????src??????
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    public static int getTotalPage(int totalCount, int pageSize) {
        return (totalCount + pageSize - 1) / pageSize;
    }

    public static List<Double> getAverageV(double maxV) {
        List<Double> lists = new ArrayList<>();
        double b = Math.ceil(maxV / 5);
        double c = b;
        lists.add(0d);
        for (int i = 0; i < 5; i++) {
            c = c + b;
            lists.add(c);
        }
        return lists;
    }

    public static void loginNo(Context mContext) {
        ToastUtil.showLongStrToast(mContext, "????????????");
    }

    public static void showKf(Context mContext) {
        ToastUtil.showLongStrToast(mContext, "???????????????....");
    }

    public static Object[] deleteSubString(String str1, String str2) {
        StringBuffer sb = new StringBuffer(str1);
        int delCount = 0;
        Object[] obj = new Object[2];

        while (true) {
            int index = sb.indexOf(str2);
            if (index == -1) {
                break;
            }
            sb.delete(index, index + str2.length());
            delCount++;

        }
        if (delCount != 0) {
            obj[0] = sb.toString();
            obj[1] = delCount;
        } else {
            //???????????????-1
            obj[0] = -1;
            obj[1] = -1;
        }

        return obj;
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //?????????????????????????????????????????????
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //????????????
        int monthNow = cal.get(Calendar.MONTH);  //????????????
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //????????????
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //???????????????
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//??????????????????????????????????????????
            } else {
                age--;//???????????????????????????????????????
            }
        }
        return age;
    }

    public static String getNewContent1(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }
    public static void getTimeMinS(int mss,TimeInterface mTimeInterface) {
        if(mss<60){
            mTimeInterface.btnConfirm(0,mss%60);
        }else{
            mTimeInterface.btnConfirm(mss/60,mss%60);
        }
    }

    public interface TimeInterface {
        public void btnConfirm(int min,int s);
    }
    public static String formatDateTime(long mss) {
        String DateTimes = null;
        long days = mss / ( 60 * 60 * 24);
        long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % ( 60 * 60)) /60;
        long seconds = mss % 60;

        if(days>0){
            DateTimes= days + ":" + hours + ":" + minutes + ":"
                    + seconds + "";
        }else if(hours>0){
            DateTimes=hours + ":" + minutes + ":"
                    + seconds + "";
        }else if(minutes>0){
            DateTimes=(minutes>9?minutes:"0"+minutes) + ":"+ (seconds>9?seconds:"0"+seconds)+ "";
        }else{
            DateTimes="00:"+(seconds>9?seconds:"0"+seconds);
        }

        return DateTimes;
    }
    // ???????????????????????????,birthTimeString:"1994-11-14"
    public static int getAgeFromBirthTime(String birthTimeString) {
        // ??????????????????????????????????????????
        if(StringUtil.isBlank(birthTimeString) || !birthTimeString.contains("-")){
            return 0;
        }
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = Integer.parseInt(strs[2]);
        // ????????????????????????????????????
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // ???????????????????????????????????????
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// ???????????????
        if (yearMinus < 0) {// ?????????????????????
            age = 0;
        } else if (yearMinus == 0) {// ?????????????????????1????????????0
            if (monthMinus < 0) {// ?????????????????????
                age = 0;
            } else if (monthMinus == 0) {// ????????????
                if (dayMinus < 0) {// ?????????????????????
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// ?????????>?????????
            } else if (monthMinus == 0) {// ??????????????????????????????????????????
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age;
    }

    /**
     * ???????????????????????????
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {

        return false ;
    }

}
