package com.jxxx.tiyu_app.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jxxx.tiyu_app.R;
import com.jxxx.tiyu_app.app.MainApplication;
import com.jxxx.tiyu_app.utils.StringUtil;
import com.jxxx.tiyu_app.utils.ToastUtil;
import com.jxxx.tiyu_app.utils.view.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/8/25.
 */

public abstract class BaseFragment extends Fragment {

    private View mContentView;
    public Context mContext;
    Unbinder unbinder;
    protected Bundle savedInstanceState;
    protected final String TAG = this.getClass().getSimpleName();
    private LoadingDialog mLoading;

    public BaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
        mContext = getActivity();
        unbinder = ButterKnife.bind(this, mContentView);
        initView();
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    protected abstract void initView();

    protected abstract void initData();



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showLoading() {
        if (mLoading != null && !mLoading.isShowing()) {
            mLoading.show();
        } else {
            mLoading = LoadingDialog.show(mContext, R.string.loading_text, false, null);
        }
    }

    public void hideLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }

    public boolean isResultOk(Result mResult) {
        if(mResult.getCode()==200){
            return true;
        }
        String msg = mResult.getMsg();
        if(StringUtil.isBlank(msg)){
            msg = mResult.getCode()+"";
        }
        ToastUtil.showLongStrToast(MainApplication.getContext(),msg);
        return false;
    }
}
