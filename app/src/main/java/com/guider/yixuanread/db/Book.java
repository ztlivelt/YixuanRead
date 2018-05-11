package com.guider.yixuanread.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by zt on 2018/5/7.
 */

public class Book extends DataSupport implements Serializable{
    private int id;
    private String bookname;
    private String bookpath;
    private long begin;

    private String charset;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookpath() {
        return bookpath;
    }

    public void setBookpath(String bookpath) {
        this.bookpath = bookpath;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
