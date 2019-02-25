package com.ngo.sphtech.Pojo;

import java.io.Serializable;

public class Records implements Serializable {
    double volume_of_mobile_data;
    String quarter;
    String _id;

    public Records(double volume_of_mobile_data, String quarter, String _id) {
        this.volume_of_mobile_data = volume_of_mobile_data;
        this.quarter = quarter;
        this._id = _id;
    }

    public double getVolume_of_mobile_data() {
        return volume_of_mobile_data;
    }

    public void setVolume_of_mobile_data(double volume_of_mobile_data) {
        this.volume_of_mobile_data = volume_of_mobile_data;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
