package net.inconnection.charge.extend.chargeDevice.protocol;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.*;


public class MsgConvertUtil {


    //将message转为json
    public static JSONArray msg2Json(String message) {
        JSONArray jsonArray = new JSONArray();
        if (message != null && !message.equals("")){
            //得到 facet
            String[] facetStrArr = message.split(MSG_FACET_SEPARATOR_INSIDE);

            for (String facetStr : facetStrArr){
                //得到 segment
                String[] segmentStrArr = facetStr.split(MSG_SEGMENT_SEPARATOR);

                JSONObject jsonObject = new JSONObject();
                for (String segmentStr : segmentStrArr){
                    String[] componentStrArr = segmentStr.split(MSG_COMPONENT_SEPARATOR);
                    //只有两个组件的才需要添加到map，一个是key，一个是value
                    if (componentStrArr.length == 2){
                        String key = componentStrArr[0];
                        String value = componentStrArr[1];
                        if (key.equals(MSG_STATUS) || key.equals(MSG_ALARM) ){
                            //硬件报警或者采集设备状态,可能出现重复字段
                            String[] valueRepeatArr = value.split(MSG_REPEAT_SEPARATOR);
                            if (valueRepeatArr.length > 0){
                                jsonObject.put(key, valueRepeatArr);
                            }
                        }else {
                            //其他字段
                            jsonObject.put(key, value);
                        }
                    }
                }
                jsonArray.add(jsonObject);
            }
        }


        return jsonArray;

    }

}
