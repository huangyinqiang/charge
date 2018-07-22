package net.inconnection.charge.extend.deviceManage.alarm;

import java.util.Date;

import static net.inconnection.charge.extend.deviceManage.alarm.AlarmStatus.START;


/**
 * Created by zhengkun on 17-12-12.
 */
public class Alarm {
    private int tag;
    private String message;
    private Date startTime;
    private Date endTime;
    private AlarmStatus status;

    public Alarm(int tag, String message, Date startTime, Date endTime) {
        this.tag = tag;
        this.message = message;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = START;
    }

    public Alarm(int tag, String message, Date startTime, Date endTime, AlarmStatus status) {
        this.tag = tag;
        this.message = message;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Alarm() {
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    public void setStatus(AlarmStatus status) {
        this.status = status;
    }
}
