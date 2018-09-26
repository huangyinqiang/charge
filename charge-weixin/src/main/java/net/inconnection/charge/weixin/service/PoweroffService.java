package net.inconnection.charge.weixin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ChargeBatteryInfo;
import net.inconnection.charge.weixin.utils.HttpUrlConnectionUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PoweroffService {
    private static Log log = Log.getLog(PoweroffService.class);

    public PoweroffService() {
    }

    public HnKejueResponse poweroff(String id, String openId, String devicelId, String channelNum) {
        if (StringUtils.isBlank(id)) {
            return new HnKejueResponse("id不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(openId)) {
            return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(devicelId)) {
            return new HnKejueResponse("devicelId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(channelNum)) {
            return new HnKejueResponse("channelNum不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            try {
                String powerOffServerUrl = PropKit.get("powerOffServerUrl");
                String serverUrl = powerOffServerUrl + "?devicelId=" + devicelId + "&channelNum=" + channelNum + "&openId=" + openId;
                String invokeServer = HttpUrlConnectionUtil.invokeServer(serverUrl);
                JSONObject jsonObject = JSON.parseObject(invokeServer);
                boolean obj = (Boolean)jsonObject.get("success");
                if (obj) {
                    ChargeBatteryInfo.dao.updateEndTimeById(id);
                    return new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
                }
            } catch (Exception var10) {
                log.error("远程断电出错：" + var10);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public HnKejueResponse queryPoweroff(String openId) {
        if (StringUtils.isBlank(openId)) {
            return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            ArrayList resp = new ArrayList();

            try {
                List<ChargeBatteryInfo> batteryInfoList = ChargeBatteryInfo.dao.queryPowerOff(openId);
                Iterator var5 = batteryInfoList.iterator();

                while(var5.hasNext()) {
                    ChargeBatteryInfo chargeBatteryInfo = (ChargeBatteryInfo)var5.next();
                    Date operStartTime = (Date)chargeBatteryInfo.get("operStartTime");
                    String deviceId = (String)chargeBatteryInfo.get("deviceId");
                    String devicePort = (String)chargeBatteryInfo.get("devicePort");
                    ChargeBatteryInfo info = ChargeBatteryInfo.dao.queryLastBatteryInfo(openId, operStartTime, deviceId, devicePort);
                    if (info != null) {
                        log.info("此端口被占用，不能远程断电。再次充电的信息为 id=" + info.toString());
                    } else {
                        long chargeTime = Long.parseLong((String)chargeBatteryInfo.get("chargeTime")) * 60L * 1000L;
                        long abs = operStartTime.getTime() + chargeTime - (new Date()).getTime();
                        if (abs < -10000L) {
                            log.info("当前时间大于充电时长，继续判断下一条充电信息");
                        } else {
                            if (!chargeBatteryInfo.get("status").equals("F")) {
                                resp.add(chargeBatteryInfo);
                            }
                            log.info("判断在充电时间内，可远程断电的信息" + chargeBatteryInfo.toString());
                        }
                    }
                }

                return new HnKejueResponse(resp, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (Exception var14) {
                log.error("查询远程断电出错：" + var14);
                return new HnKejueResponse(var14.getMessage(), RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }



    public HnKejueResponse queryPoweroffByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            ArrayList resp = new ArrayList();

            try {
                List<Record> batteryInfoList = ChargeBatteryInfo.dao.queryPowerOffByOpenId(openId);
                Iterator var5 = batteryInfoList.iterator();
                Record chargeBatteryInfo;
                while(var5.hasNext()) {
                    chargeBatteryInfo = (Record)var5.next();
//                    ChargeBatteryInfo chargeBatteryInfo = (ChargeBatteryInfo)var5.next();
                    Date operStartTime = (Date)chargeBatteryInfo.get("operStartTime");
                    String deviceId = (String)chargeBatteryInfo.get("deviceId");
                    String devicePort = (String)chargeBatteryInfo.get("devicePort");
                    ChargeBatteryInfo info = ChargeBatteryInfo.dao.queryLastBatteryInfo(openId, operStartTime, deviceId, devicePort);
                    if (info != null) {
                        log.info("此端口被占用，不能远程断电。再次充电的信息为 id=" + info.toString());
                    } else {
                        long chargeTime = Long.parseLong((String)chargeBatteryInfo.get("chargeTime")) * 60L * 1000L;
                        long abs = operStartTime.getTime() + chargeTime - (new Date()).getTime();
                        if (abs < -10000L) {
                            log.info("当前时间大于充电时长，继续判断下一条充电信息");
                        } else {
                            if (!chargeBatteryInfo.get("status").equals("F")) {
                                String operType = chargeBatteryInfo.get("operType").toString();
                                if("W".equals(operType)){
                                    chargeBatteryInfo.set("operType","会员充电");
                                }else{
                                    chargeBatteryInfo.set("operType","临时充电");
                                }

                                String serverResultDesc = chargeBatteryInfo.getStr("serverResultDesc");
                                String powerSection="0-200";
                                if(serverResultDesc != null && StringUtils.isNotEmpty(serverResultDesc)){
                                    Integer power = Integer.valueOf(serverResultDesc);
                                    //充电成功
                                    if (power < 200){
                                        powerSection = "0-200";
                                    }else if (power < 300){
                                        powerSection = "201-300";
                                    }else if (power < 350){
                                        powerSection = "301-350";
                                    }else if (power < 500){
                                        powerSection = "351-500";
                                    }else if (power < 700){
                                        powerSection = "501-700";
                                    }else if (power < 1000){
                                        powerSection = "701-1000";
                                    }else {
                                        powerSection = "701-1000";
                                    }
                                    chargeBatteryInfo.set("power",powerSection);
                                }else{
                                    chargeBatteryInfo.set("power",powerSection);
                                }

                                String chargeTimeStr = chargeBatteryInfo.get("chargeTime").toString();
                                if("800".equals(chargeTimeStr)){
                                    chargeBatteryInfo.set("chargeTime","充满自停");
                                }else{
                                    chargeBatteryInfo.set("chargeTime",chargeTimeStr+"分钟");
                                }
                                String area = chargeBatteryInfo.getStr("area");
                                if(StringUtils.isEmpty(area) || area == null ){
                                    chargeBatteryInfo.set("area",chargeBatteryInfo.get("name"));
                                }

                                resp.add(chargeBatteryInfo);
                            }
                            log.info("判断在充电时间内，可远程断电的信息" + chargeBatteryInfo.toString());
                        }
                    }
                }

                return new HnKejueResponse(resp, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (Exception var14) {
                log.error("查询远程断电出错：" + var14);
                return new HnKejueResponse(var14.getMessage(), RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }
}

