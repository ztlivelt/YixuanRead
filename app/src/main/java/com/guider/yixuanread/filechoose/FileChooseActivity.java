package com.guider.yixuanread.filechoose;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.guider.yixuanread.R;

public class FileChooseActivity extends AppCompatActivity implements DirectoryFragment.FileChooseListener{
    private Toolbar toolbar;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;
    private DirectoryFragment directoryFragment;
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;
    public static void openFileChooseActivity(Context context) {
        Intent intent = new Intent(context, FileChooseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);
        initActionBar();
        initView();
        isCheckPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE,EXTERNAL_STORAGE_REQ_CODE,"添加图书需要此权限，请允许!");

    }

    private void initActionBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("目录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (directoryFragment.onBackPressed()){
                    finish();
                }
            }
        });
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        directoryFragment = DirectoryFragment.newInstance(this);
        fragmentTransaction.add(R.id.fragment_container, directoryFragment, DirectoryFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void updateTitle(String title) {
        if (toolbar != null){
            toolbar.setTitle(title);
        }
    }
    @Override
    protected void onDestroy() {
//        directoryFragment.onFragmentDestroy();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (directoryFragment.onBackPressed()){
            super.onBackPressed();
        }
    }

    public void isCheckPermission(Activity activity, String permission, int requestCode, String errorText){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(activity, permission, requestCode,errorText);
        }
    }

    /**
     * 检查是否具有权限
     * @param activity
     * @param permission
     * @param requestCode
     * @param errorText
     */
    public void checkPermission(Activity activity, String permission, int requestCode, String errorText) {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)){
                Toast.makeText(this,errorText,Toast.LENGTH_SHORT).show();
                //进行权限请求
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        requestCode);
            }
        }
    }
}
