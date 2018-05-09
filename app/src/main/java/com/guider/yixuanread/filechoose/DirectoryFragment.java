package com.guider.yixuanread.filechoose;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.guider.yixuanread.R;
import com.guider.yixuanread.db.Book;
import com.guider.yixuanread.utils.DiskManagerUtil;

import org.apache.commons.lang3.StringUtils;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DirectoryFragment extends Fragment implements DirectoryAdapter.DirectoryListener, View.OnClickListener {

    private File currentDir;

    private RecyclerView dirRecyclerView;
    private DirectoryAdapter directoryAdapter;
    private List<FileItem> items = new ArrayList<>();
    private List<FileItem> checkItems = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();
    private List<HistoryEntry> historyEntries = new ArrayList<>();

    private LinearLayout bottomLayout;
    private Button chooseAllBtn;
    private Button deleteBtn;
    private Button addFileBtn;

    private String[] chhosefileType = {".txt"};

    private FileChooseListener listener;

    public DirectoryFragment() {
    }

    public static DirectoryFragment newInstance(FileChooseListener listener) {
        DirectoryFragment fragment = new DirectoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setListener(listener);
        return fragment;
    }

    public FileChooseListener getListener() {
        return listener;
    }

    public void setListener(FileChooseListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //注册广播
        return inflater.inflate(R.layout.fragment_directory, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        bookList.clear();
        bookList.addAll(DataSupport.findAll(Book.class));
        directoryAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        bottomLayout = view.findViewById(R.id.bottomLayout);
        chooseAllBtn = view.findViewById(R.id.chooseAllBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        addFileBtn = view.findViewById(R.id.addFileBtn);

        chooseAllBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        addFileBtn.setOnClickListener(this);

        dirRecyclerView = view.findViewById(R.id.dirRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        directoryAdapter = new DirectoryAdapter(getContext(), items, checkItems, bookList, this);
        dirRecyclerView.setLayoutManager(layoutManager);
        dirRecyclerView.setAdapter(directoryAdapter);
    }

    @Override
    public void changeCheckBookNum(int num) {
        addFileBtn.setText("加入书架(" + num + ")");
    }

    @Override
    public void onClickDir(FileItem fileItem) {
        doRunByFileItem(fileItem);
    }


    private void initData() {
        setRootDirs();
    }

    /**
     * 设置根目录
     */
    private void setRootDirs() {
        currentDir = null;
        items.clear();
        String extStorage = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileItem extFileItem = new FileItem();
        if (Build.VERSION.SDK_INT < 9 || Environment.isExternalStorageRemovable()) {
            extFileItem.setTitle("SdCard");
            extFileItem.setIcon(R.mipmap.ic_external_storage);
        } else {
            extFileItem.setTitle("InternalStorage");
            extFileItem.setIcon(R.mipmap.ic_storage);
        }
        extFileItem.setSubtitle(DiskManagerUtil.getRootSubtitle(extStorage));
        extFileItem.setFile(Environment.getExternalStorageDirectory());
        items.add(extFileItem);

        FileItem fs = new FileItem();
        fs.setTitle("/");
        fs.setSubtitle("系统目录");
        fs.setIcon(R.mipmap.ic_directory);
        fs.setFile(new File("/"));
        items.add(fs);
        directoryAdapter.notifyDataSetChanged();
    }

    private void doRunByFileItem(FileItem item) {
        File file = item.getFile();
        if (file == null) {
            if (historyEntries.size() != 0) {
                HistoryEntry he = historyEntries.remove(historyEntries.size() - 1);
                if (he.getDir() != null) {
                    openDir(he.getDir());
                } else {
                    setRootDirs();
                }
                updateTitle(he.getTitle());
            } else {
                setRootDirs();
            }
            //更新actionbar updateName(he.title);
            //滚动到运来的位置
        } else if (file.isDirectory()) {
            HistoryEntry he = new HistoryEntry();
//            he.setScrollItem(dirRecyclerView.getTop());
//            he.setScrollOffset(dirRecyclerView.getVerticalScrollbarPosition());
            if (currentDir != null) {
                he.setDir(currentDir);
                he.setTitle(currentDir.getName());
            }else{
                he.setTitle("目录");
            }
            if (!openDir(file)) {
                return;
            }
            historyEntries.add(he);
            updateTitle(currentDir.getName());
            //更新actionbar 标题
//            dirRecyclerView.setTop(0);
        }
    }

    private boolean openDir(File dir) {
        if (!dir.canRead()) {
            showErrorBox("没有权限！");
            return false;
        }
        File[] files = null;
        files = dir.listFiles();
        currentDir = dir;
        items.clear();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                if (lhs.isDirectory() != rhs.isDirectory()) {
                    return lhs.isDirectory() ? -1 : 1;
                }
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        for (File file : files) {
            if (!DiskManagerUtil.checkFileInRules(file)) { //校验文件是否符合规则
                continue;
            }
            FileItem item = new FileItem();
            item.setTitle(file.getName());
            item.setFile(file);
            if (file.isDirectory()) {
                item.setIcon(R.mipmap.ic_directory);
                item.setSubtitle("文件夹");
            } else {
                String fname = file.getName();
                String[] sp = fname.split("\\.");
                item.setExt(sp.length > 1 ? sp[sp.length - 1] : "?"); //设置文件扩展名
                item.setSubtitle(DiskManagerUtil.formatFileSize(file.length())); //设置文件描述
                item.setThumb(file.getAbsolutePath());
                item.setIcon(R.mipmap.file_type_txt);
            }
            items.add(item);
        }
        addHeadItemWhenOpenDir();
        directoryAdapter.notifyDataSetChanged();
        return true;
    }

    //打开文件夹添加头部item,用于返回上一层操作
    private void addHeadItemWhenOpenDir() {
        FileItem item = new FileItem();
        item.setTitle("..");
        item.setSubtitle("文件夹");
        item.setIcon(R.mipmap.ic_directory);
        item.setFile(null);
        items.add(0, item);
    }


    public void showErrorBox(String error) {
        if (getActivity() == null) {
            return;
        }
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.app_name))
                .setMessage(error).setPositiveButton("OK", null).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.chooseAllBtn:
                //1.选中所用 checkAll();
                checkAll();
                //2.设置选中数 changeCheckBookNum()
                changeCheckBookNum(checkItems.size());
                //3.通知adapter改变
                directoryAdapter.notifyDataSetChanged();
                break;
            case R.id.deleteBtn:
                //1. 清空选中列表 checkItems.clear()
                checkItems.clear();
                changeCheckBookNum(checkItems.size());
                //2. adapter改变
                //3. 设置选中数
                directoryAdapter.notifyDataSetChanged();
                break;
            case R.id.addFileBtn:
                //选中的添加到数据库 changeCheckBookNum()
                addCheckBook();
                break;
        }
    }

    private void checkAll(){
        for (FileItem item: items){
            if (StringUtils.isNotBlank(item.getThumb())){
                boolean isCheck = false;
                for (FileItem checkItem:checkItems){
                    if (checkItem.getThumb().equals(item.getThumb())){
                        isCheck = true;
                        break;
                    }
                }
                for (Book book : bookList){
                    if (book.getBookpath().equals(item.getThumb())){
                        isCheck = true;
                        break;
                    }
                }
                if (!isCheck){
                    checkItems.add(item);
                }
            }
        }
    }

    private void addCheckBook() {
        if (checkItems.size() > 0) {
            List<Book> books = new ArrayList<>();
            for (FileItem item : checkItems) {
                Book book = new Book();
                book.setBookname(item.getTitle());
                book.setBookpath(item.getThumb());
                books.add(book);
            }
            SaveBookToSqlLiteTask mSaveBookToSqlLiteTask = new SaveBookToSqlLiteTask();
            mSaveBookToSqlLiteTask.execute(books);
        }
    }


    private class SaveBookToSqlLiteTask extends AsyncTask<List<Book>, Void, Integer> {
        private static final int FAIL = 0;
        private static final int SUCCESS = 1;
        private static final int REPEAT = 2;
        private Book repeatBook;

        @Override
        protected Integer doInBackground(List<Book>[] lists) {
            List<Book> books = lists[0];
            for (Book book : books) {
                List<Book> temps = DataSupport.where("bookpath = ?", book.getBookpath()).find(Book.class);
                if (temps.size() > 0) {
                    repeatBook = book;
                    return REPEAT;
                }
            }
            DataSupport.saveAll(books);
            return SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            String msg = "";
            switch (result) {
                case FAIL:
                    msg = "由于一些原因添加书本失败";
                    break;
                case SUCCESS:
                    msg = "导入书本成功";
                    checkItems.clear();
                    bookList.clear();
                    bookList.addAll(DataSupport.findAll(Book.class));
                    directoryAdapter.notifyDataSetChanged();
                    changeCheckBookNum(checkItems.size());
                    break;
                case REPEAT:
                    msg = "书本" + repeatBook.getBookname() + "重复了";
                    break;
            }
            Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    public boolean onBackPressed(){
        if (historyEntries.size() > 0){
            HistoryEntry he = historyEntries.remove(historyEntries.size() - 1);
            if (he.getDir() != null){
                openDir(he.getDir());
            }else{
                setRootDirs();
            }
            updateTitle(he.getTitle());
            return false;
        }
        return true;
    }


    public interface FileChooseListener {
        void updateTitle(String title);
    }

    private void updateTitle(String title) {
        if (listener != null) {
            listener.updateTitle(title);
        }
    }
}
