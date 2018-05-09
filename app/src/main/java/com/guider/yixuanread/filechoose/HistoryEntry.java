package com.guider.yixuanread.filechoose;

import java.io.File;

/**
 * Created by zt on 2018/5/9.
 */

public class HistoryEntry {
    private int scrollItem;
    private int scrollOffset;
    private File dir;
    private String title;

    public int getScrollItem() {
        return scrollItem;
    }

    public void setScrollItem(int scrollItem) {
        this.scrollItem = scrollItem;
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
