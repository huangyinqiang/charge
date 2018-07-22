package net.inconnection.charge.extend.chargeDevice.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static net.inconnection.charge.extend.chargeDevice.utils.ConfigXmlConstant.*;


/**
 * 从配置文件中报警信息的配置
 */
public class AlarmInfoConfig {


    private static Map<String, AlarmInfo> alarmInfoMap = new HashMap<>();

    public AlarmInfoConfig(String xmlPath) {

        JSONArray myJsonArray = (JSONArray)(XmlConfigUtil.getJson(xmlPath));
        for (int index = 0; index < myJsonArray.size(); index++) {
            JSONObject myjObject = myJsonArray.getJSONObject(index);

            AlarmInfo alarmInfo = new AlarmInfo();
            if (myjObject.containsKey(XML_INFO_TAG) && !myjObject.getString(XML_INFO_TAG).equals("")){
                alarmInfo.setTag((myjObject.getString(XML_INFO_TAG)));
            }else {
                continue;
            }

            if (myjObject.containsKey(XML_INFO_ALARM_MSG) && !myjObject.getString(XML_INFO_ALARM_MSG).equals("")){
                alarmInfo.setAlarmMessage(myjObject.getString(XML_INFO_ALARM_MSG));
            }else {
                continue;
            }

            if (myjObject.containsKey(XML_INFO_ADDRESS) && !myjObject.getString(XML_INFO_ADDRESS).equals("")){
                alarmInfo.setAddress(myjObject.getString(XML_INFO_ADDRESS));
            }else {
                continue;
            }

            if (myjObject.containsKey(XML_INFO_VALUE) && !myjObject.getString(XML_INFO_VALUE).equals("")){
                alarmInfo.setValue(myjObject.getString(XML_INFO_VALUE));
            }else {
                continue;
            }

            alarmInfoMap.put(alarmInfo.getTag(), alarmInfo);

        }


    }


    public boolean containsTag(String alarmTag){
        return alarmInfoMap.containsKey(alarmTag);
    }

    public AlarmInfo getAlarmInfo(String alarmTag){
        return alarmInfoMap.get(alarmTag);
    }

    public Map<String, AlarmInfo> getAlarmInfoMap(){
        return alarmInfoMap;
    }

}
