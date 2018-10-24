package net.inconnection.charge.extend.chargeDevice.protocol.message.facet;

        import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequestSetJlTimeFacet extends RequestChargePlieFacet {
    private Integer jlTime;

    public RequestSetJlTimeFacet(String requestType, Integer tickTime) {
        super(requestType);
        this.jlTime = tickTime;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_REQUEST_JLTIME).append(MSG_COMPONENT_SEPARATOR).append(jlTime);

        return msg.toString();
    }
}
