package net.inconnection.charge.extend.chargeDevice.protocol.message.facet;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MSG_COMPONENT_SEPARATOR;

public class RequestDeleteImageFacet extends RequestChargePlieFacet {
    private String imageName;

    public RequestDeleteImageFacet(String requestType, String imageName) {
        super(requestType);
        this.imageName = imageName;
    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_IMAGE_NAME).append(MSG_COMPONENT_SEPARATOR).append(imageName);

        return msg.toString();
    }
}
