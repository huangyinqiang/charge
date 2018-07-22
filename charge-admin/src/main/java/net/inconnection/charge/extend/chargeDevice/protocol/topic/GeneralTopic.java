package net.inconnection.charge.extend.chargeDevice.protocol.topic;

import java.util.Vector;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_CUR_VERSION;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MQTT_TOPIC_TO_POINT;


public class GeneralTopic implements Topic{

    private String topicDirect;
    private String industry;//行业
    private String version;
    private String gwId;//网关设备ID
    private String messageType;//消息类型

    public GeneralTopic(String topic) {
        parseTopic(topic);
    }

    public GeneralTopic(String industry, String version, String gwId, String messageType) {
        this.industry = industry;
        this.version = version;
        this.gwId = gwId;
        this.messageType = messageType;
    }

    public GeneralTopic(String industry, String gwId, String messageType) {
        this.industry = industry;
        this.version = MQTT_TOPIC_CUR_VERSION;
        this.gwId = gwId;
        this.messageType = messageType;
    }

    public void parseTopic(String topic) {
        Vector vec = TopicUtil.decodeTopic(topic);
        topicDirect = vec.get(0).toString();
        industry = vec.get(1).toString();
        version = vec.get(2).toString();
        gwId = vec.get(3).toString();
        messageType = vec.get(4).toString();
    }

    public String generateTopic(String industry, String version, String gwId, String messageType){

        Vector vector = new Vector();
        vector.add(MQTT_TOPIC_TO_POINT);
        vector.add(industry);
        vector.add(version);
        vector.add(gwId);
        vector.add(messageType);

        return TopicUtil.encodeTopic(vector);

    }

    public GeneralTopic() {

    }

    public String getTopic() {
        return generateTopic(industry, version, gwId,messageType);
    }

    public String getTopicDirect() {
        return topicDirect;
    }

    public void setTopicDirect(String topicDirect) {
        this.topicDirect = topicDirect;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustry(){
        return industry;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion(){
        return version;
    }

    public void setGwId(String gwId) {
        this.gwId = gwId;
    }

    public String getGwId(){
        return gwId;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType(){
        return messageType;
    }

}
