package com.ngo.sphtech.Pojo.Data.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ngo.sphtech.Pojo.Data.DataConsumption;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "SphTech.db";
    private static final int DATABASE_VERSION = 1;
    public SQLiteDatabase sqLiteDatabase;
    Context context;
    Dao<DataConsumption, Integer> dataConsumptionDao;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        try {
            sqLiteDatabase = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, DataConsumption.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, DataConsumption.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<DataConsumption, Integer> getDataConsumptionDao() throws SQLException {
        if (dataConsumptionDao == null) {
            dataConsumptionDao = getDao(DataConsumption.class);
        }
        return dataConsumptionDao;
    }
}
