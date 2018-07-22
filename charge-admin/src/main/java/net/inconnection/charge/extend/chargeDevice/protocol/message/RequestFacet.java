package net.inconnection.charge.extend.chargeDevice.protocol.message;


import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MSG_COMPONENT_SEPARATOR;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MSG_REQUEST;

/**
 * Created by zhengkun on 17-12-27.
 */
public abstract class RequestFacet {

    protected String requestType;

    public RequestFacet(){
    }

    public RequestFacet(String requestType){
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String toString(){
        return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType;
    }

}
