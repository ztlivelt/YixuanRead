package com.guider.yixuanread.db;

import org.litepal.crud.DataSupport;

/**
 * Created by zt on 2018/5/7.
 */

public class BookMark extends DataSupport{
    private int id;
//    private int page;
    private long begin; //书签记录页面的结束点位置
//    private int count;
    private String text;
    private String time;
    private String bookpath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBookpath() {
        return bookpath;
    }

    public void setBookpath(String bookpath) {
        this.bookpath = bookpath;
    }
}
