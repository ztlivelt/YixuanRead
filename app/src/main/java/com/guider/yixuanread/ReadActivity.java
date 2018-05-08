package com.guider.yixuanread;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guider.yixuanread.base.BaseActivity;
import com.guider.yixuanread.db.Book;

public class ReadActivity extends BaseActivity {
    private static final String TAG = "ReadActivity";
    private final static String EXTRA_BOOK = "book";
    private final static int MESSAGE_CHANGEPROGRESS = 1;
    public static void openReadActivity(Context context,Book book){
        Intent intent = new Intent(context,ReadActivity.class);
        intent.putExtra(EXTRA_BOOK,book);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.activity_read;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


}
