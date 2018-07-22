package net.inconnection.charge.extend.deviceInterface;


/**
 * IoT节点
 * 节点包括传输节点和设备节点
 * 传输节点：网关和采集器
 * 设备节点：具体的被采集对象，如逆变器，汇流箱，气象站等
 */
public interface Node {


    void setID(Long id);//
    Long getID();

}
