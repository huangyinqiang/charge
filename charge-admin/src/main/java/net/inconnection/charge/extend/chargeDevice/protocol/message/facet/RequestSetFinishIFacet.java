package net.inconnection.charge.extend.chargeDevice.protocol.message.facet;

        import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequestSetFinishIFacet extends RequestChargePlieFacet {
    private Integer finishI;

    public RequestSetFinishIFacet(String requestType, Integer finishI) {
        super(requestType);
        this.finishI = finishI;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_REQUEST_FINISH_I).append(MSG_COMPONENT_SEPARATOR).append(finishI);

        return msg.toString();
    }
}