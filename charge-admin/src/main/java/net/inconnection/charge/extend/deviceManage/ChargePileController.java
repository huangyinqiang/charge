package net.inconnection.charge.extend.deviceManage;


import net.inconnection.charge.extend.deviceManage.device.ChargePile;

import java.util.Vector;

public class ChargePileController {
    private static ChargePileController ourInstance = new ChargePileController();

    public static ChargePileController getInstance() {
        return ourInstance;
    }

    private ChargePileController() {
    }


    //请求允许上线
    public void permissionOnLine(Long gwId, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);
        chargePile.permissionOnLine(callBackQueueName);
    }

    //请求关闭所有插座
    public void shutDownAllSockets(Long gwId, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);
        chargePile.shutDownAllSockets(callBackQueueName);
    }

    //请求关闭插座
    public void shutDownChargeSocket(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);
        chargePile.shutDownChargeSocket(socketIds, callBackQueueName);
    }

    //请求测试充电功率
    public void requestTestPower(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);
        chargePile.requestTestPower(socketIds, callBackQueueName);
    }

    //请求插座开始充电
    public void startCharge(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePile chargePile = ChargePileManager.getInstance().getChargePile(gwId);
        chargePile.startCharge(socketIds, callBackQueueName);
    }

}
