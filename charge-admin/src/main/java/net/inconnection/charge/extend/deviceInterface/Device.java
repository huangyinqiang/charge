package net.inconnection.charge.extend.deviceInterface;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public interface Device extends Node {

    public void setGWId(Long gwId);
    public Long getGWId();
    //更新数据
    boolean updateData(Date updateTime, JSONObject deviceObj);
    //更新状态，（报警）
    boolean updateStatus(Date updateTime, JSONObject deviceObj);

}
