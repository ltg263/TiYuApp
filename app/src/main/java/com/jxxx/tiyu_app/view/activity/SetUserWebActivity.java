package com.jxxx.tiyu_app.view.activity;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.widget.Toolbar;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.base.BaseActivity;
import com.jxxx.tiyu_app.utils.StringUtil;

import butterknife.BindView;

public class SetUserWebActivity extends BaseActivity {
    @BindView(R.id.web)
    WebView mWebView;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @Override
    public int intiLayout() {
        return R.layout.activity_set_user_web;
    }

    @Override
    public void initView() {
        setToolbar(myToolbar, getIntent().getStringExtra("title"));
        WebSettings webSettings = mWebView.getSettings();//获取webview设置属性
        webSettings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        webSettings.setBlockNetworkImage(false); // 解决图片不显示
        //支持javascript
//        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadData(
                StringUtil.getNewContent1(getIntent().getStringExtra("content")), "text/html; charset=UTF-8", null);//这种写法可以正确解码
    }

    @Override
    public void initData() {

    }
}
