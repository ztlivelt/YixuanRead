package com.guider.yixuanread;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.guider.yixuanread.base.BaseActivity;
import com.guider.yixuanread.base.Config;
import com.guider.yixuanread.db.Book;
import com.guider.yixuanread.widget.PageFactory;
import com.guider.yixuanread.widget.PageWidget;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.OnClick;

public class ReadActivity extends BaseActivity {
    private static final String TAG = "ReadActivity";
    private final static String EXTRA_BOOK = "book";
    private final static int MESSAGE_CHANGEPROGRESS = 1;
    @Bind(R.id.bookPage)
    PageWidget bookPage;

    @Bind(R.id.progressLayout)
    RelativeLayout progressLayout;
    @Bind(R.id.progressTv)
    TextView progressTv;

    @Bind(R.id.preTvBtn)
    TextView preTvBtn;
    @Bind(R.id.progressSb)
    SeekBar progressSb;
    @Bind(R.id.nextTvBtn)
    TextView nextTvBtn;

    @Bind(R.id.directoryBtn)
    TextView directoryBtn;
    @Bind(R.id.dayornightBtn)
    TextView dayornightBtn;
    @Bind(R.id.pagemodeBtn)
    TextView pagemodeBtn;
    @Bind(R.id.settingBtn)
    TextView settingBtn;

    @Bind(R.id.bookPopBottom) //总跟
    LinearLayout bookPopBottom;
    @Bind(R.id.bottomLayout) //设置
    RelativeLayout bottomLayout;

    @Bind(R.id.rlReadBottomLayout)
    RelativeLayout rlReadBottomLayout;
    @Bind(R.id.stopReadTvBtn)
    TextView stopReadTvBtn;

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.appBar)
    AppBarLayout appBar;



    private Config config;
    private PageFactory pageFactory;
    private Book book;
    //popwindow是否显示
    private boolean isShow = false;
    private boolean mDayOrNight;
    //语音合成
    private boolean isSpeaking = false;

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
    protected void initData() {
        initActionBar();

        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //隐藏
        //hideSystemUI();
        hideReadSetting();
        //改变屏幕亮度
        //获取intent中携带信息
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra(EXTRA_BOOK);
        //设置书本信息

        if(Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 19){
            bookPage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        config = Config.getInstance();
        pageFactory = PageFactory.getInstance();
        bookPage.setPageMode(config.getPageMode());
        pageFactory.setPageWidget(bookPage);
        pageFactory.openBook(book);
        //initDayOrNigth();


    }
    @Override
    protected void initListener() {
        bookPage.setTouchListener(new PageWidget.TouchListener() {
            @Override
            public void center() {
                if (isShow) {
                    hideReadSetting();
                } else {
                    showReadSetting();
                }
            }

            @Override
            public boolean prePage() {
                if (isShow || isSpeaking){
                    return false;
                }

                pageFactory.prePage();
                if (pageFactory.isfirstPage()) {
                    return false;
                }

                return true;
            }

            @Override
            public boolean nextPage() {
                if (isShow || isSpeaking){
                    return false;
                }

                pageFactory.nextPage();
                if (pageFactory.islastPage()) {
                    return false;
                }
                return true;
            }

            @Override
            public void cancel() {
                pageFactory.cancelPage();
            }
        });
    }

    private void initActionBar(){
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.mipmap.return_button);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_bookmark: //添加书签
                break;
            case R.id.action_read_book: //读书功能
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 隐藏菜单，沉浸式阅读
     */
    private void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                //  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                |View.SYSTEM_UI_FLAG_FULLSCREEN  // hide status bar
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
    private void showSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    //显示书本进度
    public void showProgress(float progress){
        if (progressLayout.getVisibility() != View.VISIBLE){
            progressLayout.setVisibility(View.VISIBLE);
        }
        setProgress(progress);
    }
    //隐藏书本进度
    public void hideProgress(){
        progressLayout.setVisibility(View.GONE);
    }
    private void setProgress(float progress){
        DecimalFormat decimalFormat=new DecimalFormat("00.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(progress * 100.0);//format 返回的是字符串
        progressTv.setText(p + "%");
    }

    public void setSeekBarProgress(float progress){
        progressSb.setProgress((int) (progress * 10000));
    }


    //白天与夜间模式切换
    public void initDayOrNigth(){
        mDayOrNight = false;//TODO 配置获取
        if (mDayOrNight){
            dayornightBtn.setText("白天");
        }else{
            dayornightBtn.setText("夜间");
        }
    }
    public void changeDayOrNight(){
        if (mDayOrNight){
            mDayOrNight = false;
            dayornightBtn.setText("夜间");
        } else{
            mDayOrNight = true;
            dayornightBtn.setText("白天");
        }
        //配置修改
        //控件修改
    }

    //设置功能显示
    private void showReadSetting(){
        isShow = true;
        progressLayout.setVisibility(View.GONE);
        if (isSpeaking){
            //动画未添加
            rlReadBottomLayout.setVisibility(View.VISIBLE);
        } else {
            showSystemUI();
            //动画未添加
            bottomLayout.setVisibility(View.VISIBLE);
            appBar.setVisibility(View.VISIBLE);
        }
    }
    //设置功能隐藏
    private void hideReadSetting(){
        isShow = false;
        bottomLayout.setVisibility(View.GONE);
        appBar.setVisibility(View.GONE);
        rlReadBottomLayout.setVisibility(View.GONE);
        hideSystemUI();
    }

    @OnClick({R.id.preTvBtn,R.id.nextTvBtn,R.id.directoryBtn,R.id.dayornightBtn,
    R.id.pagemodeBtn,R.id.settingBtn,R.id.stopReadTvBtn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.preTvBtn:
                break;
            case R.id.nextTvBtn:
                break;
            case R.id.directoryBtn:
                break;
            case R.id.dayornightBtn:
                break;
            case R.id.pagemodeBtn:
                break;
            case R.id.settingBtn:
                break;
            case R.id.stopReadTvBtn:
                break;
        }
    }
}