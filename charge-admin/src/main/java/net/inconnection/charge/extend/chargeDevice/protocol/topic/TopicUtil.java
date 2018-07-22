package net.inconnection.charge.extend.chargeDevice.protocol.topic;

import java.util.Vector;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_SEPARATOR;


public  class TopicUtil {


    //字符串解析为topic vector
    public static Vector decodeTopic(String topicStr){
        String[] sourceStrArray = topicStr.split(MQTT_TOPIC_SEPARATOR);
        Vector topicVec=new Vector();
        for (int i = 0; i < sourceStrArray.length; i++) {
            topicVec.add(sourceStrArray[i]);
        }
        return topicVec;
    }

    //topic vector编码为字符串
    public static String encodeTopic(Vector topicVec){

        String topicStr = new String();
        for (int index=0; index<topicVec.size()-1; index++){
            topicStr = topicStr + topicVec.get(index).toString() + MQTT_TOPIC_SEPARATOR;
        }

        if (topicVec.size() > 0){
            topicStr = topicStr + topicVec.get(topicVec.size()-1).toString();
        }

        return topicStr;
    }



}
