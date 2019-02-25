package com.ngo.sphtech.Pojo;

import java.io.Serializable;

public class Links implements Serializable {
    String start;
    String next;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
