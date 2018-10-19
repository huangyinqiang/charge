package net.inconnection.charge.extend.chargeDevice.protocol.message.facet;

        import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequestSetTickFacet extends RequestChargePlieFacet {
    private Integer tickTime;

    public RequestSetTickFacet(String requestType, Integer tickTime) {
        super(requestType);
        this.tickTime = tickTime;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_REQUEST_TICK).append(MSG_COMPONENT_SEPARATOR).append(tickTime);

        return msg.toString();
    }
}
