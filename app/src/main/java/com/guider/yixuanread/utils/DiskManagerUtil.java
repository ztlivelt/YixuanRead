package com.guider.yixuanread.utils;

import android.os.StatFs;

import java.io.File;

/**
 * 磁盘管理工具类
 * Created by zt on 2018/5/9.
 */

public class DiskManagerUtil {
    /**
     * 磁盘空间统计
     * @param path
     * @return
     */
    public static  String getRootSubtitle(String path) {
        StatFs stat = new StatFs(path);
        long total = (long) stat.getBlockCount() * (long) stat.getBlockSize();
        long free = (long) stat.getAvailableBlocks()
                * (long) stat.getBlockSize();
        if (total == 0) {
            return "";
        }
        return "Free " + formatFileSize(free) + " of " + formatFileSize(total);
    }

    /**
     * 空间大小转换为可识别
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        if (size < 1024) {
            return String.format("%d B", size);
        } else if (size < 1024 * 1024) {
            return String.format("%.1f KB", size / 1024.0f);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", size / 1024.0f / 1024.0f);
        } else {
            return String.format("%.1f GB", size / 1024.0f / 1024.0f / 1024.0f);
        }
    }

    /**
     *  检查文件是否规则
     * @param file
     * @return
     */
    public static boolean checkFileInRules(File file){
        if (file.isDirectory()) return true;
        if (file.getName().endsWith(".txt")) return true;
        return false;

    }
}
