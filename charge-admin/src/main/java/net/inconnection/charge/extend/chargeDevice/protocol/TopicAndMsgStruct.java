package net.inconnection.charge.extend.chargeDevice.protocol;

public class TopicAndMsgStruct {

    private static final String TOPIC_MESSAGE_TERMINATOR                     = "$";//topic message分隔符


    private String topic;
    private String message;

    public TopicAndMsgStruct(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public TopicAndMsgStruct(String topicAndMsg){
        topic = topicAndMsg.substring(0, topicAndMsg.indexOf(TOPIC_MESSAGE_TERMINATOR));
        message = topicAndMsg.substring(topicAndMsg.indexOf(TOPIC_MESSAGE_TERMINATOR) + 1, topicAndMsg.length());
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString(){
        return topic + TOPIC_MESSAGE_TERMINATOR + message;
    }
}
