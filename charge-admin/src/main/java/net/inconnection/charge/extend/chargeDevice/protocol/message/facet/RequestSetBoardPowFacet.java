package net.inconnection.charge.extend.chargeDevice.protocol.message.facet;

        import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequestSetBoardPowFacet extends RequestChargePlieFacet {
    private Integer maxBoardPow;

    public RequestSetBoardPowFacet(String requestType, Integer maxBoardPow) {
        super(requestType);
        this.maxBoardPow = maxBoardPow;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_REQUEST_MAX_BOARDPOW).append(MSG_COMPONENT_SEPARATOR).append(maxBoardPow);

        return msg.toString();
    }
}
