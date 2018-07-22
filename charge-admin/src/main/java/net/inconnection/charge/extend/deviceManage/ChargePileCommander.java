package net.inconnection.charge.extend.deviceManage;


import net.inconnection.charge.extend.deviceManage.device.ChargePileDevice;

import java.util.Vector;

public class ChargePileCommander {
    private static ChargePileCommander ourInstance = new ChargePileCommander();

    public static ChargePileCommander getInstance() {
        return ourInstance;
    }

    private ChargePileCommander() {
    }


    //请求允许上线
    public void permissionOnLine(Long gwId, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        chargePileDevice.permissionOnLine(callBackQueueName);
    }

    //请求关闭所有插座
    public void shutDownAllSockets(Long gwId, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        chargePileDevice.shutDownAllSockets(callBackQueueName);
    }

    //请求关闭插座
    public void shutDownChargeSocket(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        chargePileDevice.shutDownChargeSocket(socketIds, callBackQueueName);
    }

    //请求测试充电功率
    public void requestTestPower(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        chargePileDevice.requestTestPower(socketIds, callBackQueueName);
    }

    //请求插座开始充电
    public void startCharge(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        chargePileDevice.startCharge(socketIds, callBackQueueName);
    }

}
