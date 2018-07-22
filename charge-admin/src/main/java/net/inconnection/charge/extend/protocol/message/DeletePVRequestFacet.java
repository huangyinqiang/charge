package net.inconnection.charge.extend.protocol.message;


import static net.inconnection.charge.extend.protocol.ProtocolConstant.*;

/**
 * Created by zhengkun on 18-2-24.
 */
public class DeletePVRequestFacet extends RequestFacet {

    private Long pvsn;

    public DeletePVRequestFacet(String requestType, Long pvsn){
        this.requestType = requestType;
        this.pvsn = pvsn;
    }

    public Long getPvsn() {
        return pvsn;
    }

    public void setPvsn(Long pvsn) {
        this.pvsn = pvsn;
    }

    public String toString(){
        return MSG_REQUEST + MSG_COMPONENT_SEPARATOR + requestType + MSG_SEGMENT_SEPARATOR + MSG_PVSN + MSG_COMPONENT_SEPARATOR + String.format("%012d", pvsn);
    }

}
