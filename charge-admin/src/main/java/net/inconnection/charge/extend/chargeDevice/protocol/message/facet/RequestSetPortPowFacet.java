package net.inconnection.charge.extend.chargeDevice.protocol.message.facet;

        import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequestSetPortPowFacet extends RequestChargePlieFacet {
    private Integer maxPortPow;

    public RequestSetPortPowFacet(String requestType, Integer maxPortPow) {
        super(requestType);
        this.maxPortPow = maxPortPow;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_REQUEST_MAX_PORTPOW).append(MSG_COMPONENT_SEPARATOR).append(maxPortPow);

        return msg.toString();
    }
}
