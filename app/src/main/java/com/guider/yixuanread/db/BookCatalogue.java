package com.guider.yixuanread.db;

/**
 * Created by zt on 2018/5/7.
 */

public class BookCatalogue {
    private int id;
    private String bookpath;
    private String bookCatalogue;
    private long bookCatalogueStartPos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookpath() {
        return bookpath;
    }

    public void setBookpath(String bookpath) {
        this.bookpath = bookpath;
    }

    public String getBookCatalogue() {
        return bookCatalogue;
    }

    public void setBookCatalogue(String bookCatalogue) {
        this.bookCatalogue = bookCatalogue;
    }

    public long getBookCatalogueStartPos() {
        return bookCatalogueStartPos;
    }

    public void setBookCatalogueStartPos(long bookCatalogueStartPos) {
        this.bookCatalogueStartPos = bookCatalogueStartPos;
    }
}
