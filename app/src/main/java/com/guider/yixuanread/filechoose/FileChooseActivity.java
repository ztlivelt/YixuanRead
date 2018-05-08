package com.guider.yixuanread.filechoose;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guider.yixuanread.R;

public class FileChooseActivity extends AppCompatActivity {
    public static void  openFileChooseActivity(Context context){
        Intent intent = new Intent(context,FileChooseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);
    }
}
