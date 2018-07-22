package net.inconnection.charge.extend.chargeDevice.protocol.message;


import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequestSocketFacet extends RequestFacet {
    private String deviceSn;


    public RequestSocketFacet(String requestType, String deviceSN){
        this.requestType = requestType;
        this.deviceSn = deviceSN;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }


    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_DEVICESN).append(MSG_COMPONENT_SEPARATOR).append(deviceSn).append(MSG_FACET_SEPARATOR_INSIDE);

        msg.deleteCharAt(msg.length()-1);
        return msg.toString();
    }
}
