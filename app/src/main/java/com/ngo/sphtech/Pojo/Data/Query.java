package com.ngo.sphtech.Pojo.Data;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.ngo.sphtech.Pojo.ApiResponse;
import com.ngo.sphtech.Pojo.Data.Helper.DataBaseHelper;
import com.ngo.sphtech.Pojo.Records;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Query {

    DataBaseHelper databaseHelper = null;

    public Query() {
    }

    public void addData(Context context, ApiResponse apiResponse) {

        ArrayList<Records> list = apiResponse.getResult().getRecords();
        for (Records records : list) {
            try {
                DataConsumption dataConsumption = new DataConsumption();
                dataConsumption.setId(records.get_id());
                dataConsumption.setQuarter(records.getQuarter());
                dataConsumption.setVolume(records.getVolume_of_mobile_data());
                Dao<DataConsumption, Integer> dataConsumptionDao = getHelper(context).getDataConsumptionDao();
                if (!checkRecords(context, records.getQuarter()))
                    dataConsumptionDao.create(dataConsumption);
                else
                    updateData(context, records);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkRecords(Context context, String quarter) {
        try {
            Dao<DataConsumption, Integer> dataConsumptionDao = getHelper(context).getDataConsumptionDao();
            List<DataConsumption> dataConsumptions = dataConsumptionDao.queryForEq("quarter", quarter);

            if (dataConsumptions != null)
                if (dataConsumptions.size() > 0)
                    return true;
                else
                    return false;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateData(Context context, Records records) {
        try {
            Dao<DataConsumption, Integer> dataConsumptionDao = getHelper(context).getDataConsumptionDao();
            List<DataConsumption> dataConsumptions = dataConsumptionDao.queryForEq("quarter", records.getQuarter());

            if (dataConsumptions != null)
                if (dataConsumptions.size() > 0) {
                    DataConsumption dataConsumption = dataConsumptions.get(0);
                    dataConsumption.setVolume(records.getVolume_of_mobile_data());

                    dataConsumptionDao.update(dataConsumption);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DataConsumption> getDataList(Context context) {
        List<DataConsumption> dataConsumptions = null;
        try {
            Dao<DataConsumption, Integer> dataConsumptionDao = getHelper(context).getDataConsumptionDao();
            dataConsumptions = dataConsumptionDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataConsumptions;
    }

    private DataBaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        }
        return databaseHelper;
    }

}
