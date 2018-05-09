package com.guider.yixuanread.filechoose;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.guider.yixuanread.R;
import com.guider.yixuanread.db.Book;

import java.util.List;

/**
 * Created by zt on 2018/5/9.
 */

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryHolder>{
    private Context context;
    private List<FileItem> fileItems;
    private List<FileItem> checkItems;
    private List<Book> bookList;
    private DirectoryListener listener;

    public DirectoryAdapter(Context context, List<FileItem> fileItems,List<FileItem> checkItems, List<Book> bookList,DirectoryListener listener) {
        this.context = context;
        this.fileItems = fileItems;
        this.listener = listener;
        this.checkItems = checkItems;
        this.bookList = bookList;
    }

    @Override
    public DirectoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_directory_item,parent,false);
        DirectoryHolder holder = new DirectoryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DirectoryHolder holder, int position) {
        final FileItem item = fileItems.get(position);
        holder.bindView(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClickDir(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileItems.size();
    }

    class DirectoryHolder extends RecyclerView.ViewHolder{
        private ImageView fileTypeIv;
        private TextView fileNameTv;
        private TextView fileDesTv;
        private View fileStatusLayout;
        private TextView fileStatusTv;
        private CheckBox isSelectCb;

        public DirectoryHolder(View view) {
            super(view);
            fileTypeIv = view.findViewById(R.id.fileTypeIv);
            fileNameTv = view.findViewById(R.id.fileTitleTv);
            fileDesTv = view.findViewById(R.id.fileDesTv);
            fileStatusLayout = view.findViewById(R.id.fileStatueLayout);
            fileStatusTv = view.findViewById(R.id.fileStatueTv);
            isSelectCb = view.findViewById(R.id.isSelectCb);
        }
        public void bindView(final FileItem item){
            String type = null;
            fileNameTv.setText(item.getTitle());
            fileDesTv.setText(item.getSubtitle());
            fileTypeIv.setImageResource(item.getIcon());
            fileTypeIv.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(item.getExt())){
                fileStatusLayout.setVisibility(View.GONE);
                isSelectCb.setVisibility(View.VISIBLE);
                fileStatusTv.setVisibility(View.GONE);
            } else {
                fileStatusLayout.setVisibility(View.VISIBLE);
                if (isStorage(item.getThumb())){
                    isSelectCb.setVisibility(View.GONE);
                    fileStatusTv.setVisibility(View.VISIBLE);
                }else{
                    isSelectCb.setVisibility(View.VISIBLE);
                    fileStatusTv.setVisibility(View.GONE);
                }
            }




//            if (type != null){
//                if (isStorage(item.getThumb())){
//                    isSelectCb.setVisibility(View.GONE);
//                    fileStatusTv.setVisibility(View.VISIBLE);
//                } else {
//                    isSelectCb.setVisibility(View.VISIBLE);
//                    fileStatusTv.setVisibility(View.GONE);
//                }
//            }else {
//                fileStatusLayout.setVisibility(View.GONE);
//                isSelectCb.setVisibility(View.GONE);
//                fileStatusTv.setVisibility(View.GONE);
//            }


            isSelectCb.setOnCheckedChangeListener(null);
            isSelectCb.setChecked(isCheck(item.getThumb()));
            isSelectCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        checkItems.add(item);
                    }else{
                        removeCheckItem(item.getThumb());
                    }
                    if (listener != null){
                        listener.changeCheckBookNum(checkItems.size());
                    }
                }
            });
        }
    }

    /**
     * 是否已被选中
     * @param path
     * @return
     */
    private boolean isCheck(String path){
        for (FileItem item : checkItems){
            if (item.getThumb().equals(path)){
                return true;
            }
        }
        return false;
    }

    /**
     * 移除选中项
     * @param path
     */
    private void removeCheckItem(String path){
        for (FileItem item: checkItems){
            if (item.getThumb().equals(path)){
                checkItems.remove(item);
                break;
            }
        }
    }

    /**
     * 是否已经被添加到书架中
     * @param path
     * @return
     */
    private boolean isStorage(String path){
        boolean isStore = false;
        for (Book book : bookList){
            if (book.getBookpath().equals(path)){
                return true;
            }
        }
        return false;
    }
    public interface DirectoryListener{
        void changeCheckBookNum(int num);
        void onClickDir(FileItem fileItem);
    }
}
