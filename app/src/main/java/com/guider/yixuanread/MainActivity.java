package com.guider.yixuanread;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.guider.yixuanread.adapter.ShelfAdapter;
import com.guider.yixuanread.base.BaseActivity;
import com.guider.yixuanread.db.Book;
import com.guider.yixuanread.filechoose.FileChooseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements ShelfAdapter.ShelfListener{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fabBtn)
    FloatingActionButton fabBtn;
    @Bind(R.id.bookShelf)
    RecyclerView boolShelfView;

    private WindowManager mWindowManager;
    private View rootView;

    private List<Book> bookList;
    private ShelfAdapter shelfAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开文件选择器 FileChooseActivity
                FileChooseActivity.openFileChooseActivity(MainActivity.this);
            }
        });
        //书本点击打开ReadActivity
    }

    @Override
    protected void initData() {
        initActionBar();
        bookList = new ArrayList<>();
        for (int i = 0 ; i < 10 ;i++){
            Book book = new Book();
            book.setBookname("书集：" + i);
            bookList.add(book);
        }
        shelfAdapter = new ShelfAdapter(this,bookList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        boolShelfView.setLayoutManager(layoutManager);
        boolShelfView.setAdapter(shelfAdapter);
    }
    private void initActionBar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);//设置导航图标
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_select_file:
                //打开文件选择器
                FileChooseActivity.openFileChooseActivity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void selectBook(View view, Book book) {
        ReadActivity.openReadActivity(this,book);
    }
}
