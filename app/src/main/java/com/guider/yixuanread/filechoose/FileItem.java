package com.guider.yixuanread.filechoose;

import java.io.File;

/**
 * Created by zt on 2018/5/9.
 */

public class FileItem {
    private int icon;  //图标资源
    private String title; //标题
    private String subtitle = ""; //描述
    private String ext = ""; //扩张名
    private String thumb; //路径
    private File file; //文件本身

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
