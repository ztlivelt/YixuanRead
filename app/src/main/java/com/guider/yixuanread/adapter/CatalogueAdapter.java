package com.guider.yixuanread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guider.yixuanread.R;
import com.guider.yixuanread.db.BookCatalogue;

import java.util.List;

/**
 * Created by zt on 2018/5/11.
 */

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.CatalogueHolder>{
    private Context context;
    private List<BookCatalogue> bookCatalogues;
    private CatalogueListener listener;

    public CatalogueAdapter(Context context, List<BookCatalogue> bookCatalogues, CatalogueListener listener) {
        this.context = context;
        this.bookCatalogues = bookCatalogues;
        this.listener = listener;
    }

    @Override
    public CatalogueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_mark_fragment_catalogue_item,null);
        CatalogueHolder holder = new CatalogueHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CatalogueHolder holder, int position) {
        BookCatalogue catalogue = bookCatalogues.get(position);
        holder.catalogueName.setText(catalogue.getBookCatalogue());
    }

    @Override
    public int getItemCount() {
        return bookCatalogues.size();
    }

    class CatalogueHolder extends RecyclerView.ViewHolder{
        private TextView catalogueName;
        public CatalogueHolder(View view) {
            super(view);
            catalogueName = view.findViewById(R.id.catalogue_tv);
        }
    }
    public interface CatalogueListener{

    }
}
