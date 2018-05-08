package com.guider.yixuanread.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by zt on 2018/5/7.
 */

public abstract class BaseFragment extends Fragment {
    private View rootView;

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getLayoutRes();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        rootView = view;
        ButterKnife.bind(this, view);
        initListener();
        initData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public View getRootView() {
        return rootView;
    }
}
