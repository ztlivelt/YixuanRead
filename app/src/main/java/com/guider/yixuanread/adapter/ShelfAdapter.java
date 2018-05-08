package com.guider.yixuanread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guider.yixuanread.R;
import com.guider.yixuanread.db.Book;

import java.util.List;

/**
 * Created by zt on 2018/5/8.
 */

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ShelfHolder>{
    private Context context;
    private List<Book> bookList;
    private ShelfListener listener;

    public ShelfAdapter(Context context, List<Book> bookList, ShelfListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.listener = listener;
    }


    @Override
    public ShelfHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_book_shelf_item,null);
        ShelfHolder holder = new ShelfHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShelfHolder holder, int position) {
        final Book book = bookList.get(position);
        holder.bookNameTv.setText(book.getBookname());
        holder.bookIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.selectBook(null,book);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.selectBook(null,book);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
    class ShelfHolder extends RecyclerView.ViewHolder{
        private TextView bookNameTv;
        private ImageView bookIv;
        public ShelfHolder(View itemView) {
            super(itemView);
            bookNameTv = itemView.findViewById(R.id.bookNameTv);
            bookIv = itemView.findViewById(R.id.bookIv);
        }
    }

    public interface ShelfListener{
        void selectBook(View view,Book book);
    }
}
