package net.inconnection.charge.extend.chargeDevice.deviceManage;


import com.jfinal.log.Log;
import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargePileDevice;


import java.util.Map;
import java.util.Vector;

public class ChargePileCommander {

    private static Log _log = Log.getLog(ChargePileCommander.class);

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

    //6：请求删除某广告图片
    public boolean deleteImage(Long gwId, String imageName, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.deleteImage(imageName, callBackQueueName);

        return true;
    }

    //7：请求设置某广告图片显示效果
    public boolean showImage(Long gwId, String imageName, Integer timeLast, Integer xPoint, Integer yPoint, String startTime, String endTime, Integer dayLast, String callBackQueueName){

        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.showImage(imageName, timeLast, xPoint, yPoint, startTime, endTime, dayLast, callBackQueueName);

        return true;
    }

    //8：设置传输间隔
    public boolean setTick(Long gwId, Integer tickTime, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.setTick(tickTime, callBackQueueName);
        return true;
    }

    //9：请求在线确认
    public boolean checkOnline(Long gwId, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.checkOnline(callBackQueueName);
        return true;
    }

    //10：请求传输基站位置
    public boolean getLocation(Long gwId, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.getLocation(callBackQueueName);
        return true;
    }

    //12：请求设置端口最大支持功率
    public boolean setPortMaxPow(Long gwId, Integer portMaxPow, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.setPortMaxPow(portMaxPow, callBackQueueName);
        return true;
    }

    //13：请求设置强电板最大支持功率
    public boolean setBoardMaxPow(Long gwId, Integer boardMaxPow, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.setBoardMaxPow(boardMaxPow, callBackQueueName);
        return true;
    }

    //15：设置充电截止判断电流
    public boolean setFinishI(Long gwId, Integer finishI, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.setFinishI(finishI, callBackQueueName);
        return true;
    }

    //16：请求设置涓流充电时间
    public boolean setJlTime(Long gwId, Integer jlTime, String callBackQueueName){
        ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);
        if (null == chargePileDevice){
            _log.error("chargedivice is not exist, Id : " + gwId);
            return false;
        }
        chargePileDevice.setJlTime(jlTime, callBackQueueName);
        return true;
    }

}
