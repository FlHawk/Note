package com.swufe.note.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteBean {
    private int id;
    private String content;
    private String time;

    public NoteBean(String content, String time) {
        super();
        this.content = content;
        this.time = time;
    }
    public NoteBean() {
        super();
        content = "";
        time = "";
    }

    public static final String getrealTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
