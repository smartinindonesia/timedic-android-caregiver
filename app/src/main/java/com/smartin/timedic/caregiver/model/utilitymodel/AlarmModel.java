package com.smartin.timedic.caregiver.model.utilitymodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hafid on 05/05/2018.
 */

public class AlarmModel implements Serializable {
    private Long id;
    private String medicineName;
    private Integer numOfMedicine;
    private Integer intervalTime;
    private String medicineShape;
    private Integer intervalDay;
    private List<AlarmTime> time;
    private String startingDate;
    private IDStatus status;

    public static final String ACTION_ID = "id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel.id";
    public static final String ACTION_MEDICINE_NAME = "id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel.medicineName";
    public static final String ACTION_NUM_OF_MEDICINE = "id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel.numOfMedicine";
    public static final String ACTION_INTERVAL_TIME = "id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel.intervalTime";
    public static final String ACTION_INTERVAL_DAY = "id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel.intervalDay";
    public static final String ACTION_STARTING_DATE = "id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel.startingDate";
    public static final String ACTION_MEDICINE_SHAPE = "id.smartin.org.homecaretimedic.model.utilitymodel.AlarmModel.medicineShape";

    public static final String T_ALARM_MODEL = "AlarmModel";
    public static final String T_ALARM_ID = "id";
    public static final String T_MEDICINE_NAME = "medicineName";
    public static final String T_NUM_OF_MEDICINE = "numOfMedicine";
    public static final String T_INTERVAL_TIME = "intervalTime";
    public static final String T_MEDICINE_SHAPE = "medicineShape";
    public static final String T_INTERVAL_DAY = "intervalDay";
    public static final String T_STARTING_DATE = "startingDate";
    public static final String T_STATUS = "status";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Integer getNumOfMedicine() {
        return numOfMedicine;
    }

    public void setNumOfMedicine(Integer numOfMedicine) {
        this.numOfMedicine = numOfMedicine;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getMedicineShape() {
        return medicineShape;
    }

    public void setMedicineShape(String medicineShape) {
        this.medicineShape = medicineShape;
    }

    public Integer getIntervalDay() {
        return intervalDay;
    }

    public void setIntervalDay(Integer intervalDay) {
        this.intervalDay = intervalDay;
    }

    public List<AlarmTime> getTime() {
        return time;
    }

    public void setTime(List<AlarmTime> time) {
        this.time = time;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public IDStatus getStatus() {
        return status;
    }

    public void setActive() {
        this.status = new IDStatus();
        this.status.setId((long) 1);
        this.status.setStatus("Aktif");
    }

    public void setInactive() {
        this.status = new IDStatus();
        this.status.setId((long) 2);
        this.status.setStatus("Mati");
    }

    public void setStatus(IDStatus status) {
        this.status = status;
    }

    public static class IDStatus implements Serializable {
        private Long id;
        private String status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class AlarmTime implements Serializable {
        private Long id;
        private String time;
        private Long alarmId;

        public static final String T_TIME_ID = "id";
        public static final String T_TIME_LIST = "TimeList";
        public static final String T_TIME_ADDED = "time";
        public static final String T_ALARM_ID = "alarmId";

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Long getAlarmId() {
            return alarmId;
        }

        public void setAlarmId(Long alarmId) {
            this.alarmId = alarmId;
        }
    }
}
