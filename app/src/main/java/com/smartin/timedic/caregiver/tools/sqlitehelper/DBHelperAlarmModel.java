package com.smartin.timedic.caregiver.tools.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.model.utilitymodel.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafid on 06/05/2018.
 */

public class DBHelperAlarmModel extends SQLiteOpenHelper {
    public static final String TAG = "[DBHelperAlarmModel]";

    private AlarmModel alarmModel;
    private DBHelperTime timeListTable;


    public DBHelperAlarmModel(Context context, AlarmModel alarmModel) {
        super(context, Constants.LOCALDB_NAME, null, Constants.LOCALDB_VERSION);
        this.alarmModel = alarmModel;
        timeListTable = new DBHelperTime(context, this.alarmModel);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + AlarmModel.T_ALARM_MODEL + "(" +
                        AlarmModel.T_ALARM_ID + T_INTEGER + T_PRIMARY_KEY + ", " +
                        AlarmModel.T_MEDICINE_NAME + T_TEXT + ", " +
                        AlarmModel.T_NUM_OF_MEDICINE + T_INTEGER + ", " +
                        AlarmModel.T_INTERVAL_TIME + T_INTEGER + ", " +
                        AlarmModel.T_MEDICINE_SHAPE + T_TEXT + ", " +
                        AlarmModel.T_INTERVAL_DAY + T_INTEGER + ", " +
                        AlarmModel.T_STARTING_DATE + T_NUMERIC + ", " +
                        AlarmModel.T_STATUS + T_INTEGER +
                        ")"
        );
        timeListTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AlarmModel.T_ALARM_MODEL);
        onCreate(sqLiteDatabase);
    }

    public Integer updateAlarm(AlarmModel newData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmModel.T_INTERVAL_DAY, newData.getIntervalDay());
        contentValues.put(AlarmModel.T_MEDICINE_NAME, newData.getMedicineName());
        contentValues.put(AlarmModel.T_INTERVAL_TIME, newData.getIntervalTime());
        contentValues.put(AlarmModel.T_MEDICINE_SHAPE, newData.getMedicineShape());
        contentValues.put(AlarmModel.T_NUM_OF_MEDICINE, newData.getNumOfMedicine());
        contentValues.put(AlarmModel.T_STARTING_DATE, newData.getStartingDate());
        contentValues.put(AlarmModel.T_STATUS, newData.getStatus().getId());
        int numrow = db.update(AlarmModel.T_ALARM_MODEL, contentValues, "id = ? ", new String[]{newData.getId().toString()});
        if (newData.getTime() != null) {
            for (int i = 0; i < newData.getTime().size(); i++) {
                AlarmModel.AlarmTime at = newData.getTime().get(i);
                timeListTable.updateTime(at.getId(), at.getTime());
            }
        }
        return numrow;
    }

    public long insertAlarm(AlarmModel newData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmModel.T_INTERVAL_DAY, newData.getIntervalDay());
        contentValues.put(AlarmModel.T_MEDICINE_NAME, newData.getMedicineName());
        contentValues.put(AlarmModel.T_INTERVAL_TIME, newData.getIntervalTime());
        contentValues.put(AlarmModel.T_MEDICINE_SHAPE, newData.getMedicineShape());
        contentValues.put(AlarmModel.T_NUM_OF_MEDICINE, newData.getNumOfMedicine());
        contentValues.put(AlarmModel.T_STARTING_DATE, newData.getStartingDate());
        contentValues.put(AlarmModel.T_STATUS, newData.getStatus().getId());
        return db.insert(AlarmModel.T_ALARM_MODEL, null, contentValues);
    }

    public Integer deleteAlarm() {
        SQLiteDatabase db = this.getWritableDatabase();
        timeListTable.deleteAll();
        return db.delete(AlarmModel.T_ALARM_MODEL,
                "id = ? ",
                new String[]{alarmModel.getId().toString()});
    }

    public AlarmModel getAlarmModel() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AlarmModel.T_ALARM_MODEL,
                new String[]{
                        AlarmModel.T_ALARM_ID,
                        AlarmModel.T_INTERVAL_DAY,
                        AlarmModel.T_MEDICINE_NAME,
                        AlarmModel.T_INTERVAL_TIME,
                        AlarmModel.T_MEDICINE_SHAPE,
                        AlarmModel.T_NUM_OF_MEDICINE,
                        AlarmModel.T_STARTING_DATE,
                        AlarmModel.T_STATUS},
                AlarmModel.T_ALARM_ID + "=?",
                new String[]{alarmModel.getId().toString()
                }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        AlarmModel alMod = new AlarmModel();
        alMod.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.T_ALARM_ID)));


        alMod.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.T_ALARM_ID)));
        alMod.setIntervalDay(cursor.getInt(cursor.getColumnIndex(AlarmModel.T_INTERVAL_DAY)));
        alMod.setMedicineName(cursor.getString(cursor.getColumnIndex(AlarmModel.T_MEDICINE_NAME)));
        alMod.setIntervalTime(cursor.getInt(cursor.getColumnIndex(AlarmModel.T_INTERVAL_TIME)));
        alMod.setMedicineShape(cursor.getString(cursor.getColumnIndex(AlarmModel.T_MEDICINE_SHAPE)));
        alMod.setNumOfMedicine(cursor.getInt(cursor.getColumnIndex(AlarmModel.T_NUM_OF_MEDICINE)));
        alMod.setStartingDate(cursor.getString(cursor.getColumnIndex(AlarmModel.T_STARTING_DATE)));

        AlarmModel.IDStatus idStatus = new AlarmModel.IDStatus();
        idStatus.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.T_STATUS)));
        alMod.setStatus(idStatus);
        cursor.close();

        alMod.setTime(timeListTable.getAllTime());

        return alMod;
    }

    public List<AlarmModel> getAllAlarm() {
        List<AlarmModel> alMods = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + AlarmModel.T_ALARM_MODEL + " ORDER BY " +
                AlarmModel.T_ALARM_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AlarmModel alMod = new AlarmModel();
                alMod.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.T_ALARM_ID)));


                alMod.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.T_ALARM_ID)));
                alMod.setIntervalDay(cursor.getInt(cursor.getColumnIndex(AlarmModel.T_INTERVAL_DAY)));
                alMod.setMedicineName(cursor.getString(cursor.getColumnIndex(AlarmModel.T_MEDICINE_NAME)));
                alMod.setIntervalTime(cursor.getInt(cursor.getColumnIndex(AlarmModel.T_INTERVAL_TIME)));
                alMod.setMedicineShape(cursor.getString(cursor.getColumnIndex(AlarmModel.T_MEDICINE_SHAPE)));
                alMod.setNumOfMedicine(cursor.getInt(cursor.getColumnIndex(AlarmModel.T_NUM_OF_MEDICINE)));
                alMod.setStartingDate(cursor.getString(cursor.getColumnIndex(AlarmModel.T_STARTING_DATE)));

                AlarmModel.IDStatus idStatus = new AlarmModel.IDStatus();
                idStatus.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.T_STATUS)));
                alMod.setStatus(idStatus);

                alMods.add(alMod);
            } while (cursor.moveToNext());
        }
        db.close();
        for (int i = 0; i < alMods.size(); i++){
            AlarmModel alMod = alMods.get(i);
            alMod.setTime(timeListTable.getTimeByAlmModelId(alMod.getId()));
        }
        return alMods;
    }

    public int getAlarmCount() {
        String countQuery = "SELECT  * FROM " + AlarmModel.T_ALARM_MODEL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void setAlarmModel(AlarmModel alarmModel) {
        this.alarmModel = alarmModel;
    }

    public DBHelperTime getTimeListTable() {
        return timeListTable;
    }

    public void setTimeListTable(DBHelperTime timeListTable) {
        this.timeListTable = timeListTable;
    }

    public class DBHelperTime extends SQLiteOpenHelper {
        private AlarmModel almModel;

        public DBHelperTime(Context context, AlarmModel alarmModel) {
            super(context, Constants.LOCALDB_NAME, null, Constants.LOCALDB_VERSION);
            this.almModel = alarmModel;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(
                    "create table " + AlarmModel.AlarmTime.T_TIME_LIST + "(" +
                            AlarmModel.AlarmTime.T_TIME_ID + T_INTEGER + T_PRIMARY_KEY + ", " +
                            AlarmModel.AlarmTime.T_TIME_ADDED + T_NUMERIC + ", " +
                            AlarmModel.AlarmTime.T_ALARM_ID + T_NUMERIC +
                            ")"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AlarmModel.AlarmTime.T_TIME_LIST);
            onCreate(sqLiteDatabase);
        }

        public long updateTime(Long timeId, String time) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(AlarmModel.AlarmTime.T_TIME_ADDED, time);
            return db.update(AlarmModel.AlarmTime.T_TIME_LIST, contentValues, "id = ? ", new String[]{timeId.toString()});
        }

        public long insertTime(AlarmModel.AlarmTime time) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(AlarmModel.AlarmTime.T_TIME_ADDED, time.getTime());
            contentValues.put(AlarmModel.AlarmTime.T_ALARM_ID, almModel.getId());
            return db.insert(AlarmModel.AlarmTime.T_TIME_LIST, null, contentValues);
        }

        public long insertTime(Long alarm_id, AlarmModel.AlarmTime time) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(AlarmModel.AlarmTime.T_TIME_ADDED, time.getTime());
            contentValues.put(AlarmModel.AlarmTime.T_ALARM_ID, alarm_id);
            return db.insert(AlarmModel.AlarmTime.T_TIME_LIST, null, contentValues);
        }

        public Integer deleteTime(AlarmModel.AlarmTime alarm) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(AlarmModel.AlarmTime.T_TIME_LIST,
                    "id = ? ",
                    new String[]{alarm.getId().toString()});
        }

        public Integer deleteAll() {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(AlarmModel.AlarmTime.T_TIME_LIST,
                    AlarmModel.AlarmTime.T_ALARM_ID + " = ? ",
                    new String[]{almModel.getId().toString()});
        }

        public AlarmModel.AlarmTime getTime() {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(AlarmModel.AlarmTime.T_TIME_LIST,
                    new String[]{
                            AlarmModel.AlarmTime.T_TIME_ADDED,
                            AlarmModel.AlarmTime.T_ALARM_ID,
                            AlarmModel.AlarmTime.T_TIME_ID},
                    AlarmModel.T_ALARM_ID + "=?",
                    new String[]{almModel.getId().toString()
                    }, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            // prepare note object
            AlarmModel.AlarmTime timeMod = new AlarmModel.AlarmTime();
            timeMod.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.AlarmTime.T_TIME_ID)));
            timeMod.setAlarmId(cursor.getLong(cursor.getColumnIndex(AlarmModel.AlarmTime.T_ALARM_ID)));
            timeMod.setTime(cursor.getString(cursor.getColumnIndex(AlarmModel.AlarmTime.T_TIME_ADDED)));

            cursor.close();

            return timeMod;
        }

        public List<AlarmModel.AlarmTime> getAllTime() {
            List<AlarmModel.AlarmTime> alTimes = new ArrayList<>();
            String selectQuery = "SELECT  * FROM " + AlarmModel.AlarmTime.T_TIME_LIST +
                    " WHERE " +
                    AlarmModel.AlarmTime.T_ALARM_ID + " = " + almModel.getId() + " " +
                    " ORDER BY " +
                    AlarmModel.AlarmTime.T_TIME_ID + " DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    AlarmModel.AlarmTime timeMod = new AlarmModel.AlarmTime();
                    timeMod.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.AlarmTime.T_TIME_ID)));
                    timeMod.setAlarmId(cursor.getLong(cursor.getColumnIndex(AlarmModel.AlarmTime.T_ALARM_ID)));
                    timeMod.setTime(cursor.getString(cursor.getColumnIndex(AlarmModel.AlarmTime.T_TIME_ADDED)));

                    alTimes.add(timeMod);
                } while (cursor.moveToNext());
            }
            db.close();
            return alTimes;
        }

        public List<AlarmModel.AlarmTime> getTimeByAlmModelId(Long almModelId) {
            List<AlarmModel.AlarmTime> alTimes = new ArrayList<>();
            String selectQuery = "SELECT  * FROM " + AlarmModel.AlarmTime.T_TIME_LIST +
                    " WHERE " +
                    AlarmModel.AlarmTime.T_ALARM_ID + " = " + almModelId.toString() + " " +
                    " ORDER BY " +
                    AlarmModel.AlarmTime.T_TIME_ID + " DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    AlarmModel.AlarmTime timeMod = new AlarmModel.AlarmTime();
                    timeMod.setId(cursor.getLong(cursor.getColumnIndex(AlarmModel.AlarmTime.T_TIME_ID)));
                    timeMod.setAlarmId(cursor.getLong(cursor.getColumnIndex(AlarmModel.AlarmTime.T_ALARM_ID)));
                    timeMod.setTime(cursor.getString(cursor.getColumnIndex(AlarmModel.AlarmTime.T_TIME_ADDED)));

                    alTimes.add(timeMod);
                } while (cursor.moveToNext());
            }
            db.close();
            return alTimes;
        }

        public int getTimeCount() {
            String countQuery = "SELECT  * FROM " + AlarmModel.AlarmTime.T_TIME_LIST;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);

            int count = cursor.getCount();
            cursor.close();
            // return count
            return count;
        }

        public int getTimeCountByAlarm() {
            String countQuery =
                    "SELECT  * FROM " + AlarmModel.AlarmTime.T_TIME_LIST +
                            " WHERE " +
                            AlarmModel.AlarmTime.T_ALARM_ID + " = " + almModel.getId();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);

            int count = cursor.getCount();
            cursor.close();
            // return count
            return count;
        }
    }

    public static final String T_INTEGER = " integer";
    public static final String T_PRIMARY_KEY = " primary key";
    public static final String T_TEXT = " text";
    public static final String T_NUMERIC = " numeric";
}
