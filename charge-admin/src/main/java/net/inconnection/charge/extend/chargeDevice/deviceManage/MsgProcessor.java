package net.inconnection.charge.extend.chargeDevice.deviceManage;


import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargePileDevice;
import net.inconnection.charge.extend.chargeDevice.protocol.TopicAndMsgStruct;
import net.inconnection.charge.extend.chargeDevice.protocol.topic.GeneralTopic;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class MsgProcessor {
    private static MsgProcessor ourInstance = new MsgProcessor();

    public static MsgProcessor getInstance() {
        return ourInstance;
    }

    private MsgProcessor() {
    }

    public void processIncomeMsg(String topicAndMsg) {

        TopicAndMsgStruct topicAndMsgStruct = new TopicAndMsgStruct(topicAndMsg);

        String message = topicAndMsgStruct.getMessage();
        String topicStr = topicAndMsgStruct.getTopic();

        GeneralTopic generalTopic = new GeneralTopic(topicStr);
        Long gwId = Long.parseLong(generalTopic.getGwId());
        String messageType = generalTopic.getMessageType();

        if (TOPIC_NOTIFY.equals(messageType)){

            //待注册设备
            if (!ChargePileManager.getInstance().hasChargePile(gwId)){
                ChargePileManager.getInstance().addChargePile(gwId);
            }

        }else {

            ChargePileDevice chargePileDevice = ChargePileManager.getInstance().getChargePile(gwId);

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
