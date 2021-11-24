package com.sample.model;

public class Status {
    public Integer before_disaster;
    public Integer after_disaster;
    public String scenario;

    public void set_after(Integer after_disaster) {
        this.after_disaster = after_disaster;
    }
    public String getText(){
        String string=before_disaster+","+after_disaster;
        return string;
    }
    public String getText_S(){
        String string=before_disaster+","+after_disaster+","+scenario;
        return string;
    }
    public String getText_O(){
        String string=before_disaster+"";
        return string;
    }
}

