package net.inconnection.charge.extend.chargeDevice.protocol.message;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequsetSocketStartChargeFacet extends RequestSocketFacet{
    private int chargeTime;

    public RequsetSocketStartChargeFacet(String requestType, String deviceSN, Integer chargeTime) {
        super(requestType, deviceSN);
        this.chargeTime = chargeTime;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_DEVICESN).append(MSG_COMPONENT_SEPARATOR).append(deviceSn).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_CHARGETIME).append(MSG_COMPONENT_SEPARATOR).append(chargeTime);

        return msg.toString();
    }
}
