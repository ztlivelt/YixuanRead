package com.guider.yixuanread.widget;

import java.util.List;

/**
 * Created by zt on 2018/5/10.
 */

public class TrPage {
    private long begin;
    private long end;
    private List<String> lines;

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public String getLineToString(){
        StringBuffer text = new StringBuffer();
        if (lines != null){
            for (String line: lines){
                text.append(line);
            }
        }
        return text.toString();
    }
}
