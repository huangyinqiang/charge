package net.inconnection.charge.extend.deviceInterface;

import java.util.Vector;

public interface GateWay extends Node {
    public void setName(String name);
    public String getName();
    public Device getDevice(Long deviceId);
    public Vector<Device> getDevices();
}
