package net.inconnection.charge.extend.chargeDevice.protocol.message.facet;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;

public class RequestShowImageFacet extends RequestChargePlieFacet {
    private String imageName;
    private Integer timeLast;//显示时长，单位s
    private Integer xPoint;
    private Integer yPoint;
    private String startTime;//显示时间段 开始时间 HHMM
    private String endTime;//显示时间段 结束时间 HHMM
    private Integer dayLast;  //持续天数

    public RequestShowImageFacet(String requestType, String imageName, Integer timeLast, Integer xPoint, Integer yPoint, String startTime, String endTime, Integer dayLast) {
        super(requestType);
        this.imageName = imageName;
        this.timeLast = timeLast;
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayLast = dayLast;

    }

    public String toString(){
        StringBuilder msg = new StringBuilder();
        msg.append(MSG_REQUEST).append(MSG_COMPONENT_SEPARATOR).append(requestType).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_IMAGE_NAME).append(MSG_COMPONENT_SEPARATOR).append(imageName).append(MSG_SEGMENT_SEPARATOR).
                append(MSG_IMAGE_TIMELAST).append(MSG_COMPONENT_SEPARATOR).append(timeLast);

        if (xPoint != null && yPoint != null){
            msg.append(MSG_SEGMENT_SEPARATOR).append(MSG_IMAGE_XPOINT).append(MSG_COMPONENT_SEPARATOR).append(xPoint).
                    append(MSG_SEGMENT_SEPARATOR).append(MSG_IMAGE_YPOINT).append(MSG_COMPONENT_SEPARATOR).append(yPoint);
        }

        if (startTime != null && endTime != null){
            msg.append(MSG_SEGMENT_SEPARATOR).append(MSG_IMAGE_STARTTIME).append(MSG_COMPONENT_SEPARATOR).append(startTime).
                    append(MSG_SEGMENT_SEPARATOR).append(MSG_IMAGE_ENDTIME).append(MSG_COMPONENT_SEPARATOR).append(endTime);
        }

        if (dayLast != null){
            msg.append(MSG_SEGMENT_SEPARATOR).append(MSG_IMAGE_DAYLAST).append(MSG_COMPONENT_SEPARATOR).append(dayLast);
        }


        return msg.toString();
    }
}
