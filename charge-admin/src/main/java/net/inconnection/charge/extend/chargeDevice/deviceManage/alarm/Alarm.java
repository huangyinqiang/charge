package net.inconnection.charge.extend.chargeDevice.deviceManage.alarm;

import java.util.Date;
import java.util.Objects;

import static net.inconnection.charge.extend.chargeDevice.deviceManage.alarm.AlarmStatus.START;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return tag == alarm.tag &&
                Objects.equals(message, alarm.message) &&
                Objects.equals(startTime, alarm.startTime) &&
                Objects.equals(endTime, alarm.endTime) &&
                status == alarm.status;
    }

    @Override
    public int hashCode() {

        return Objects.hash(tag, message, startTime, endTime, status);
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "tag=" + tag +
                ", message='" + message + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }

}
