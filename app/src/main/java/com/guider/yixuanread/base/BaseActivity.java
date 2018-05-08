package com.guider.yixuanread.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by zt on 2018/5/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟数据接入
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 显示进度条
     *
     * @param flag
     * @param message
     */
    public void showProgress(boolean flag, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(flag);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
        }
        mProgressDialog.show();
    }

    /**
     * 隐藏进度条
     */
    public void hideProgress() {
        if (mProgressDialog == null)
            return;

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 是否拥有权限
     * @param thisActivity
     * @param permission
     * @param requestCode
     * @param errorText
     */
    protected void checkPermission(Activity thisActivity, String permission, int requestCode, String errorText) {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(thisActivity,permission)!= PackageManager.PERMISSION_GRANTED){
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,permission)){
                Toast.makeText(this,errorText,Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{permission},requestCode );
            }else {
                //进行权限请求
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{permission},
                        requestCode);
            }
        }else{

        }
    }
}
