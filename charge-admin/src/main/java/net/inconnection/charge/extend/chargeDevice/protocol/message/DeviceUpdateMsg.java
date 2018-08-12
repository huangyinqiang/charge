package net.inconnection.charge.extend.chargeDevice.protocol.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Map;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


public class DeviceUpdateMsg {
    private String sequenceNum;
    private String timeStr;
    private BigInteger gwid;
    private String update;
    private int offset;
    private int len;
    private int lenAll;
    private int crc;
    private int status;

    public DeviceUpdateMsg(){}

    public DeviceUpdateMsg(String sequenceNum, String timeStr, BigInteger gwid, String update, int offset, int len, int lenAll, int crc) {
        this.sequenceNum = sequenceNum;
        this.timeStr = timeStr;
        this.gwid = gwid;
        this.update = update;
        this.offset = offset;
        this.len = len;
        this.lenAll = lenAll;
        this.crc = crc;
    }

    public byte[] updateEncode(){
        StringBuilder sb=new StringBuilder();
        sb.append(MSG_GWID).append(MSG_COMPONENT_SEPARATOR).append(String.format("%012d",gwid)).append(MSG_SEGMENT_SEPARATOR)
                .append(MSG_SERIALNUMBER).append(MSG_COMPONENT_SEPARATOR).append(sequenceNum).append(MSG_SEGMENT_SEPARATOR)
                .append(MSG_TIME).append(MSG_COMPONENT_SEPARATOR).append(timeStr).append(MSG_SEGMENT_SEPARATOR)
                .append(MSG_FACET_SEPARATOR_INSIDE)
                .append(MSG_UPDATE_UPDATE).append(MSG_COMPONENT_SEPARATOR).append(update).append(MSG_SEGMENT_SEPARATOR)
                .append(MSG_UPDATE_OFFSET).append(MSG_COMPONENT_SEPARATOR).append(offset).append(MSG_SEGMENT_SEPARATOR)
                .append(MSG_UPDATE_LEN).append(MSG_COMPONENT_SEPARATOR).append(len).append(MSG_SEGMENT_SEPARATOR)
                .append(MSG_UPDATE_LEN_ALL).append(MSG_COMPONENT_SEPARATOR).append(lenAll).append(MSG_SEGMENT_SEPARATOR)
                .append(MSG_UPDATE_CRC).append(MSG_COMPONENT_SEPARATOR).append(crc & 0xff).append(MSG_SEGMENT_SEPARATOR);
        byte[] bytes = null;
        try {
            bytes = sb.toString().getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //初始化数据
    public void updateDecode(String updateMsgJsonStr) {
        JSONArray myJsonArray = JSONArray.parseArray(updateMsgJsonStr);
        Map mapFirst = myJsonArray.getJSONObject(0);
        this.sequenceNum = (String) mapFirst.get(MSG_SERIALNUMBER);
        this.timeStr = (String) mapFirst.get(MSG_TIME);
        this.gwid = new BigInteger((String)mapFirst.get(MSG_GWID));
        JSONObject mapSecond = myJsonArray.getJSONObject(1);
        this.update = (String) mapSecond.get(MSG_UPDATE_UPDATE);
        this.offset = Integer.parseInt((String) mapSecond.get(MSG_UPDATE_OFFSET));
        JSONArray statusArr = mapSecond.getJSONArray(MSG_UPDATE_STATUS);
        if (statusArr.size() != 1){
            return;
        }
        this.status = Integer.parseInt((String) statusArr.get(0));
    }

    public String getSequenceNum() {
        return sequenceNum;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public BigInteger getGwid(){ return  gwid; }

    public String getUpdate() {
        return update;
    }

    public int getOffset() {
        return offset;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "DeviceUpdateMsg{" +
                "sequenceNum='" + sequenceNum + '\'' +
                ", timeStr='" + timeStr + '\'' +
                ", gwid=" + gwid +
                ", update='" + update + '\'' +
                ", offset=" + offset +
                ", len=" + len +
                ", lenAll=" + lenAll +
                ", crc=" + (crc & 0xff) +
                ", status=" + status +
                '}';
    }

}
