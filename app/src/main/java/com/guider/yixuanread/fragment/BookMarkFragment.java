package com.guider.yixuanread.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.guider.yixuanread.R;
import com.guider.yixuanread.adapter.MarkAdapter;
import com.guider.yixuanread.base.BaseFragment;
import com.guider.yixuanread.db.Book;
import com.guider.yixuanread.db.BookMark;
import com.guider.yixuanread.widget.PageFactory;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zt on 2018/5/11.
 */

public class BookMarkFragment extends BaseFragment{
    public static final String ARGUMENT = "argument";
    @Bind(R.id.bookMarkRv)
    RecyclerView bookMarkRv;

    private List<BookMark> bookMarks;
    private PageFactory pageFactory;
    private String bookpath;
    private MarkAdapter markAdapter;

    public static BookMarkFragment newInstance(String bookpath){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,bookpath);
        BookMarkFragment bookMarkFragment = new BookMarkFragment();
        bookMarkFragment.setArguments(bundle);
        return bookMarkFragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_mark_fragment;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        pageFactory = PageFactory.getInstance();
        Bundle bundle = getArguments();
        if (bundle != null) {
            bookpath =  bundle.getString(ARGUMENT);
        }
        bookMarks = new ArrayList<>();
        bookMarks = DataSupport.where("bookpath = ?", bookpath).find(BookMark.class);
        markAdapter = new MarkAdapter(getContext(),bookMarks,null);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookMarkRv.setLayoutManager(layoutManager);
        bookMarkRv.setAdapter(markAdapter);
    }
}
