package net.inconnection.charge.extend.chargeDevice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.inconnection.charge.extend.chargeDevice.deviceManage.ChargePileCommander;
import net.inconnection.charge.extend.chargeDevice.jms.ResponseMsgReceiver;
import net.inconnection.charge.service.DeviceControlService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class DeviceControlServiceImpl implements DeviceControlService {
    public String sayHello(String name) {
        return "ceshi demo service          " +name;
    }


    @Override
    public Boolean requestPermissionOnLine(Long gwId, Long timeout) {
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = ChargePileCommander.getInstance().permissionOnLine(gwId, callBackQueueName);
        if (!sendSuccess){
            return false;
        }

        return getResult(callBackQueueName, timeout, MSG_RESPONCE_RESULT);
    }


    @Override
    public Boolean requestShutDownAllSockets(Long gwId, Long timeout) {
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = ChargePileCommander.getInstance().shutDownAllSockets(gwId, callBackQueueName);
        if (!sendSuccess){
            return false;
        }
        return getResult(callBackQueueName, timeout, MSG_RESPONCE_RESULT);
    }

    @Override
    public Map requestShutDownChargeSocket(Long gwId, Vector<Long> socketIds, Long timeout) {
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = ChargePileCommander.getInstance().shutDownChargeSocket(gwId, socketIds, callBackQueueName);
        if (!sendSuccess){
            return null;
        }

        return getMapResult(timeout, callBackQueueName);
    }

    @Override
    public Integer requestShutDownChargeSocket(Long gwId, Long socketId, Long timeout) {

        String callBackQueueName = UUID.randomUUID().toString();
        Vector<Long> socketIds = new Vector<>();
        socketIds.add(socketId);
        boolean sendSuccess = ChargePileCommander.getInstance().shutDownChargeSocket(gwId, socketIds, callBackQueueName);
        if (!sendSuccess){
            return null;
        }

        return getSingleResult(callBackQueueName, timeout, MSG_RESPONCE_RESULT);
    }

    @Override
    public Map requestStartCharge(Long gwId, Map<Long, Integer> socketIdChargeTimeMap, Long timeout) {
        String callBackQueueName = UUID.randomUUID().toString();
        boolean sendSuccess = ChargePileCommander.getInstance().startCharge(gwId, socketIdChargeTimeMap, callBackQueueName);
        if (!sendSuccess){
            return null;
        }

        return getMapResult(timeout, callBackQueueName);
    }

    @Override
    public Integer requestStartCharge(Long gwId, Long socketId, Integer chargeTime, Long timeout) {
        String callBackQueueName = UUID.randomUUID().toString();
        Map<Long, Integer> socketIdChargeTimeMap  = new HashMap<>();
        socketIdChargeTimeMap.put(socketId, chargeTime);
        boolean sendSuccess = ChargePileCommander.getInstance().startCharge(gwId, socketIdChargeTimeMap, callBackQueueName);
        if (!sendSuccess){
            return null;
        }

        return 1;
//        return getSingleResult(callBackQueueName, timeout, MSG_RESPONCE_RESULT);
    }

    //获取响应结果
    private Integer getSingleResult(String callBackQueueName,Long timeout, String resultKey) {
        ResponseMsgReceiver cmsResponseMsgReceiver = new ResponseMsgReceiver();
        String processedResponseMsg = cmsResponseMsgReceiver.getProcessedResponseMsg(callBackQueueName,timeout);
        if(processedResponseMsg != null){
            JSONArray jsonArray = JSONArray.parseArray(processedResponseMsg);
            if(jsonArray.size() > 1){
                JSONObject responseObj = jsonArray.getJSONObject(1);
                return responseObj.getInteger(resultKey);
            }
        }
        return null;
    }

    //获取响应结果
    private Boolean getResult(String callBackQueueName,Long timeout, String resultKey) {
        ResponseMsgReceiver cmsResponseMsgReceiver = new ResponseMsgReceiver();
        String processedResponseMsg = cmsResponseMsgReceiver.getProcessedResponseMsg(callBackQueueName,timeout);
        if(processedResponseMsg != null){
            JSONArray jsonArray = JSONArray.parseArray(processedResponseMsg);
            if(jsonArray.size() > 1){
                for(int i = 1 ;i<jsonArray.size();i++){
                    JSONObject responseObj = jsonArray.getJSONObject(i);
                    if(responseObj.getString(resultKey).equals("1")){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Map getMapResult(Long timeout, String callBackQueueName) {
        Map<Long, Integer> resultMap = new HashMap<>();

        ResponseMsgReceiver cmsResponseMsgReceiver = new ResponseMsgReceiver();
        String processedResponseMsg = cmsResponseMsgReceiver.getProcessedResponseMsg(callBackQueueName,timeout);
        if(processedResponseMsg != null){
            JSONArray jsonArray = JSONArray.parseArray(processedResponseMsg);
            if(jsonArray.size() > 1){
                for(int i = 1 ;i<jsonArray.size();i++){
                    JSONObject responseObj = jsonArray.getJSONObject(i);
                    Long deviceSN = responseObj.getLong(MSG_DEVICESN);
                    Integer status = responseObj.getInteger(MSG_RESPONCE_RESULT);
                    resultMap.put(deviceSN,status);
                }
            }
        }
        return resultMap;
    }
}
