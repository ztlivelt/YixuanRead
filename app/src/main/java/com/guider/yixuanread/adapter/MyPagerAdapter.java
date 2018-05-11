package com.guider.yixuanread.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.guider.yixuanread.base.BaseFragment;
import com.guider.yixuanread.db.Book;
import com.guider.yixuanread.fragment.BookMarkFragment;
import com.guider.yixuanread.fragment.CatalogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zt on 2018/5/11.
 */

public class MyPagerAdapter extends FragmentPagerAdapter{
    private List<BaseFragment> fragmentList ;
    private final String[] titles = {"目录", "书签"};

    public MyPagerAdapter(FragmentManager fm, String bookpath) {
        super(fm);
        fragmentList = new ArrayList<>();
        fragmentList.add(CatalogFragment.newInstance(bookpath));
        fragmentList.add(BookMarkFragment.newInstance(bookpath));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
