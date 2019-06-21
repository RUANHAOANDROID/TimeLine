package com.example.myapplication;

public class OperationHistoryEntity {
    private String time ;
    private String title;
    private String context;
    private int status;
    public OperationHistoryEntity() {
    }

    public OperationHistoryEntity(String time, String title, String context,int status) {
        this.time = time;
        this.title = title;
        this.context = context;
        this.status = status;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
