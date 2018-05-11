package com.guider.yixuanread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guider.yixuanread.R;
import com.guider.yixuanread.db.BookMark;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by zt on 2018/5/11.
 */

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.MarkHolder> {

    private Context context;
    private List<BookMark> bookMarks;
    private MarkListener listener;

    public MarkAdapter(Context context, List<BookMark> bookMarks, MarkListener listener) {
        this.context = context;
        this.bookMarks = bookMarks;
        this.listener = listener;
    }

    @Override
    public MarkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_mark_fragment_item, null);
        MarkHolder holder = new MarkHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MarkHolder holder, final int position) {
        final BookMark bookMark = bookMarks.get(position);
        holder.textMark.setText(bookMark.getText());
//        long begin = bookMark.getBegin();
//        float fPercent = (float) (begin * 1.0 / pageFactory.getBookLen());
//        DecimalFormat df = new DecimalFormat("#0.0");
//        String strPercent = df.format(fPercent * 100) + "%";
//        holder.progressTv.setText(strPercent);
//        holder.markTimeTv.setText(bookMark.getTime().substring(0,16));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.openBookByMark(bookMark, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.deleteBookMark(bookMark, position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookMarks.size();
    }

    class MarkHolder extends RecyclerView.ViewHolder {
        private TextView textMark;
        private TextView progressTv;
        private TextView markTimeTv;

        public MarkHolder(View view) {
            super(view);
            textMark = view.findViewById(R.id.text_mark);
            progressTv = view.findViewById(R.id.progress1);
            markTimeTv = view.findViewById(R.id.mark_time);
        }
    }

    public interface MarkListener {
        void openBookByMark(BookMark bookMark, int position);

        void deleteBookMark(BookMark bookMark, int position);
    }
}
