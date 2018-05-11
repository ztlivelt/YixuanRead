package com.guider.yixuanread;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.guider.yixuanread.adapter.MyPagerAdapter;
import com.guider.yixuanread.base.BaseActivity;
import com.guider.yixuanread.base.Config;
import com.guider.yixuanread.widget.PageFactory;

import butterknife.Bind;

public class MarkActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    public static void openMarkActivity(Context context){
        Intent intent = new Intent(context,MarkActivity.class);
//        intent.putExtra(EXTRA_BOOK,book);
        context.startActivity(intent);
    }


    private PageFactory pageFactory;
    private Config config;
    private Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_mark;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initActionBar();

        pageFactory = PageFactory.getInstance();
        config = Config.getInstance();
//        dm = getResources().getDisplayMetrics();
        typeface = config.getTypeface();
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),pageFactory.getBookPath()));
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initActionBar(){
        setSupportActionBar(toolbar);
        //设置导航图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
