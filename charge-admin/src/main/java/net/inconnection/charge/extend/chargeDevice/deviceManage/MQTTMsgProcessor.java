package net.inconnection.charge.extend.chargeDevice.deviceManage;


import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargePileDevice;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class MQTTMsgProcessor {
    private static MQTTMsgProcessor instance = new MQTTMsgProcessor();

    private static ChargePileManager chargePileManager = ChargePileManager.getInstance();

    public static MQTTMsgProcessor getInstance() {
        return instance;
    }

    private MQTTMsgProcessor() {
    }

    public void processIncomeMsg(String topic, String message) {
        GeneralTopic generalTopic = new GeneralTopic(topic);
        Long gwId = Long.parseLong(generalTopic.getGwId());
        String messageType = generalTopic.getMessageType();

        if (TOPIC_NOTIFY.equals(messageType)){
            //待注册设备
            if (!chargePileManager.hasChargePile(gwId)){
                chargePileManager.addChargePile(gwId);
            }

        }else {

            ChargePileDevice chargePileDevice = chargePileManager.getChargePile(gwId);

//            System.out.println("----chargePileManager----");
//            System.out.println(chargePileManager);
//
//            System.out.println("----chargePileDevice----");
//            System.out.println(chargePileDevice);

            if (null == chargePileDevice){
                return;
            }

            switch (messageType) {
                case TOPIC_DATA:
                    chargePileDevice.dataMsgHandle(message);
                    break;
                case TOPIC_IMAGE:
                    chargePileDevice.imageMsgHandle(message);
                    break;
                case TOPIC_REQUEST:
                    chargePileDevice.requestMsgHandle(message);
                    break;
                case TOPIC_RESPONSE:
                    chargePileDevice.responseMsgHandle(message);
                    break;
                case TOPIC_UPDATE:
                    chargePileDevice.updateMsgHandle(message);
                    break;
                default:
                    break;
            }
        }
    }

}
