package net.inconnection.charge.extend.chargeDevice.protocol.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


/**
 * Created by zhengkun on 17-12-27.
 */
public class GatewayFacet {
    private Integer sequenceNum;
    private Date utc;
    private String gatewayID;

    public GatewayFacet(Integer sequenceNum, Date utc, String gatewayID) {
        this.sequenceNum = sequenceNum;
        this.utc = utc;
        this.gatewayID = gatewayID;
    }
    public Integer getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(Integer sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public Date getUtc() {
        return utc;
    }

    public void setUtc(Date utc) {
        this.utc = utc;
    }

    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String toString(){

        String currentTimeStr;
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        currentTimeStr=currentTimeFormat.format(utc);

        return MSG_GWID + MSG_COMPONENT_SEPARATOR + gatewayID + MSG_SEGMENT_SEPARATOR +MSG_SERIALNUMBER
                + MSG_COMPONENT_SEPARATOR +sequenceNum+ MSG_SEGMENT_SEPARATOR + MSG_TIME + MSG_COMPONENT_SEPARATOR +currentTimeStr ;

    }
}
