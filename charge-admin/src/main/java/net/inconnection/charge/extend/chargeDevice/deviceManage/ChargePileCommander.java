package net.inconnection.charge.extend.chargeDevice.deviceManage;


import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargePileDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Vector;

public class ChargePileCommander {

    private static Logger _log = LoggerFactory.getLogger(ChargePileCommander.class);

    private static ChargePileCommander ourInstance = new ChargePileCommander();

    public static ChargePileCommander getInstance() {
        return ourInstance;
    }

    private ChargePileCommander() {
    }


    //请求允许上线
    public boolean permissionOnLine(Long gwId, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.permissionOnLine(callBackQueueName);
        return true;
    }

    //请求关闭所有插座
    public boolean shutDownAllSockets(Long gwId, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.shutDownAllSockets(callBackQueueName);
        return true;
    }

    //请求关闭插座
    public boolean shutDownChargeSocket(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.shutDownChargeSocket(socketIds, callBackQueueName);
        return true;
    }

    //请求测试充电功率
    public boolean requestTestPower(Long gwId, Vector<Long> socketIds, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.requestTestPower(socketIds, callBackQueueName);
        return true;
    }

    //请求插座开始充电
    public boolean startCharge(Long gwId, Map<Long, Integer> socketIdChargeTimeMap, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.startCharge(socketIdChargeTimeMap, callBackQueueName);
        return true;
    }

}
