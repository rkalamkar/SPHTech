package com.ngo.sphtech.Pojo;

import java.io.Serializable;

public class Fields implements Serializable {
    String type;
    String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
