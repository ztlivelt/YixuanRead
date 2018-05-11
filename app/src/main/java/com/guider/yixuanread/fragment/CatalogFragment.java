package com.guider.yixuanread.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.guider.yixuanread.R;
import com.guider.yixuanread.adapter.CatalogueAdapter;
import com.guider.yixuanread.base.BaseFragment;
import com.guider.yixuanread.db.Book;
import com.guider.yixuanread.db.BookCatalogue;
import com.guider.yixuanread.widget.PageFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zt on 2018/5/11.
 */

public class CatalogFragment extends BaseFragment implements CatalogueAdapter.CatalogueListener{
    public static final String ARGUMENT = "argument";


    @Bind(R.id.cataLogueRv)
    RecyclerView cataLogueRv;
    private List<BookCatalogue> catalogues = new ArrayList<>();
    private CatalogueAdapter adapter;
    private PageFactory pageFactory;

    public static CatalogFragment newInstance(String bookpath){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,bookpath);
        CatalogFragment catalogFragment = new CatalogFragment();
        catalogFragment.setArguments(bundle);
        return catalogFragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_mark_fragment_catalogue;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        pageFactory = PageFactory.getInstance();
        catalogues.addAll(pageFactory.getDirectoryList());
        adapter = new CatalogueAdapter(getContext(),catalogues,this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cataLogueRv.setLayoutManager(layoutManager);
        cataLogueRv.setAdapter(adapter);
    }

    @Override
    public void openBookByCatalogue(BookCatalogue catalogue, int position) {
        pageFactory.changeChapter(catalogue.getBookCatalogueStartPos());
        getActivity().finish();
    }
}
