package com.ngo.sphtech.Pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class ApiResult implements Serializable {
    String resource_id;
    int limit;
    int total;
    ArrayList<Fields> fields;
    ArrayList<Records> records;
    Links _links;

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Fields> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Fields> fields) {
        this.fields = fields;
    }

    public ArrayList<Records> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Records> records) {
        this.records = records;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
