package com.ngo.sphtech.Pojo.Data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "DataConsumption")
public class DataConsumption {
    @DatabaseField(columnName = "quarter", unique = true)
    String quarter;
    @DatabaseField(columnName = "volume")
    Double volume;
    @DatabaseField(columnName = "id")
    String id;

    public DataConsumption() {
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
