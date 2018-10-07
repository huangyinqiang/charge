package net.inconnection.charge.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.service.DeviceControlService;
import net.inconnection.charge.service.dubboPlugin.DubboServiceContrain;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ChargeBatteryInfo;
import net.inconnection.charge.weixin.model.NewDevice;
import net.inconnection.charge.weixin.model.WeiXin;
import net.inconnection.charge.weixin.plugin.ActiveMQ;
import net.inconnection.charge.weixin.plugin.JmsSender;
import net.inconnection.charge.weixin.service.ChargeInfoBatteryService;
import net.inconnection.charge.weixin.service.NewDeviceChargePriceService;
import net.inconnection.charge.weixin.service.NewDeviceService;
import org.apache.commons.lang.StringUtils;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewDeviceController extends Controller {
    public static final long TIMEOUT = 40 * 1000L;
    private static DeviceControlService deviceControlService = DubboServiceContrain.getInstance().getService(DeviceControlService.class);

    private static final NewDeviceService newDeviceService = new NewDeviceService();

    private static final NewDeviceChargePriceService newDeviceChargePriceService = new NewDeviceChargePriceService();

    private static ChargeInfoBatteryService chargeBatteryService = new ChargeInfoBatteryService();

    private static final Log log = Log.getLog(NewDeviceController.class);

    public void startCharge4CheckPower(){
        log.info("开始充电，不写数据");
        String openId = this.getPara("openId");
        String deviceId = this.getPara("deviceId");
        String devicePort = this.getPara("devicePort");
        String time = this.getPara("time");
        String chargeType = this.getPara("type");
        String money = this.getPara("money");
        String walletAccount = this.getPara("walletAccount");
        String operType = this.getPara("operType");
        String realGiftRate = this.getPara("realGiftRate");
        String companyId = this.getPara("companyId");
        String autoUnitPrice = this.getPara("autoUnitPrice");

        String autoUnitPriceA1 = this.getPara("power_a1");
        String autoUnitPriceA2 = this.getPara("power_a2");
        String autoUnitPriceA3 = this.getPara("power_a3");
        String autoUnitPriceA4 = this.getPara("power_a4");
        String autoUnitPriceA5 = this.getPara("power_a5");
        String autoUnitPriceA6 = this.getPara("power_a6");
        String autoUnitPriceA7 = this.getPara("power_a7");


        Integer startChargeStatus =9999;

        if (deviceId == null || devicePort == null || time == null){
            this.renderText(startChargeStatus.toString());
            log.error("数据异常，deviceId = " + deviceId +  "，devicePort = " + devicePort + "，time = " + time);
            return;
        }

        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(devicePort);
        Integer chargeTime = Integer.parseInt(time);//分钟
        Integer chargeTimeSenconds = chargeTime*60;
        log.info("开始检测功率 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + " ,chargeTime= " + chargeTime );
        log.info("开始检测功率 chargeType=" + chargeType + ",money=" + money + ",walletAccount=" + walletAccount + " ,operType= " + operType );
        log.info("开始检测功率 realGiftRate=" + realGiftRate + ",companyId=" + companyId + ",autoUnitPrice=" + autoUnitPrice + " ,autoUnitPriceA1= " + autoUnitPriceA1
                + " ,autoUnitPriceA2= " + autoUnitPriceA2 + " ,autoUnitPriceA2= " + autoUnitPriceA2 + " ,autoUnitPriceA3= " + autoUnitPriceA3 + " ,autoUnitPriceA4= "
                + autoUnitPriceA4 + " ,autoUnitPriceA5= " + autoUnitPriceA5 + " ,autoUnitPriceA6= " + autoUnitPriceA6 + " ,autoUnitPriceA7= " + autoUnitPriceA7);

        JSONObject startChargeResltJson;
        if(chargeTime == 800){
            //充满自停
            startChargeResltJson = deviceControlService.requestStartCharge(deviceSN, socketSN, 0, TIMEOUT);
        }else {
            startChargeResltJson = deviceControlService.requestStartCharge(deviceSN, socketSN, chargeTimeSenconds, TIMEOUT);
        }

        Integer startPower = 0;


        if (null != startChargeResltJson){
            startChargeStatus = startChargeResltJson.getInteger("RESULT");
            startPower = startChargeResltJson.getInteger("POW");
            log.info("开始充电设备返回结果："+startChargeResltJson.toJSONString());
        }else{
            log.error("开始充电设备返回结果：null");
        }

//        startChargeStatus = 1;
//        startPower = 800;


        log.info("开始充电结果 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + "," +
                "startChargeStatus=" + startChargeStatus+",startPower:"+startPower);
        String powerSection="0-200";
        if (startChargeStatus.equals(1) || (startChargeStatus.equals(0))){

            //充电成功
            if (startPower < 200){
                powerSection = "0-200";
                autoUnitPrice = autoUnitPriceA1;
            }else if (startPower < 300){
                powerSection = "201-300";
                autoUnitPrice = autoUnitPriceA2;
            }else if (startPower < 350){
                powerSection = "301-350";
                autoUnitPrice = autoUnitPriceA3;
            }else if (startPower < 500){
                powerSection = "351-500";
                autoUnitPrice = autoUnitPriceA4;
            }else if (startPower < 700){
                powerSection = "501-700";
                autoUnitPrice = autoUnitPriceA5;
            }else if (startPower < 1000){
                powerSection = "701-1000";
                autoUnitPrice = autoUnitPriceA6;
            }else {
                powerSection = "1001-1200";
                autoUnitPrice = autoUnitPriceA7;
            }

            if (StringUtils.isBlank(autoUnitPrice)) {
                //如果没有，就默认50分
                log.error("单价异常，autoUnitPrice = " + autoUnitPrice );
                autoUnitPrice = "50";
            }


        }else {
            log.error("开始充电结果错误 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + ",startChargeStatus=" + startChargeStatus);

            this.renderJson(new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue()));
            return;
        }

        Integer moneyInt = Integer.parseInt(money);
        Integer autoUnitPriceInt = Integer.parseInt(autoUnitPrice);
            //按照时间计算总价
        moneyInt = new Double(Double.parseDouble(time)/60.0 * autoUnitPriceInt).intValue();

        Map<String,String> map =new HashMap<>(5);
        map.put("state",startChargeStatus.toString());
        map.put("power",powerSection);
        map.put("money",String.valueOf(moneyInt));
        map.put("serverResultDesc",String.valueOf(startPower));

        log.info("开始充电返回结果 return result :" + map);

        this.renderJson(new HnKejueResponse(map,RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue()));
    }


    public void powerOff4CheckPower(){
        String deviceId = this.getPara("deviceId");
        String channeNum = this.getPara("channeNum");
        String openId = this.getPara("openId");
        log.info("断电开始 openId=" + openId + "," + ",channelNum=" + channeNum + ",deviceId=" + deviceId);
        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(channeNum);
        Integer powerOffStatus = deviceControlService.requestShutDownChargeSocket(deviceSN, socketSN, TIMEOUT);
        log.info("断电结束:"+"powerOffStatus="+powerOffStatus);

    }


    public void startCharge4WriteDate(){

        log.info("开始充电，写数据");
        String openId = this.getPara("openId");
        String deviceId = this.getPara("deviceId");
        String devicePort = this.getPara("devicePort");
        String time = this.getPara("time");
        String chargeType = this.getPara("type");
        String money = this.getPara("money");
        String walletAccount = this.getPara("walletAccount");
        String operType = this.getPara("operType");
        String realGiftRate = this.getPara("realGiftRate");
        String companyId = this.getPara("companyId");
        String autoUnitPrice = this.getPara("autoUnitPrice");

        String autoUnitPriceA1 = this.getPara("power_a1");
        String autoUnitPriceA2 = this.getPara("power_a2");
        String autoUnitPriceA3 = this.getPara("power_a3");
        String autoUnitPriceA4 = this.getPara("power_a4");
        String autoUnitPriceA5 = this.getPara("power_a5");
        String autoUnitPriceA6 = this.getPara("power_a6");
        String autoUnitPriceA7 = this.getPara("power_a7");
        String pow = this.getPara("power");
        String serverResultDesc = this.getPara("serverResultDesc");

        if (deviceId == null || devicePort == null || time == null){
            this.renderText("数据异常，请重试");
            log.error("数据异常，deviceId = " + deviceId +  "，devicePort = " + devicePort + "，time = " + time);
            return;
        }

        log.info("开始充电，写数据 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + " ,time= " + time );
        log.info("开始充电，写数据 chargeType=" + chargeType + ",money=" + money + ",walletAccount=" + walletAccount + " ,operType= " + operType );
        log.info("开始充电，写数据 realGiftRate=" + realGiftRate + ",companyId=" + companyId + ",autoUnitPrice=" + autoUnitPrice + " ,autoUnitPriceA1= " + autoUnitPriceA1
                + " ,autoUnitPriceA2= " + autoUnitPriceA2 + " ,autoUnitPriceA2= " + autoUnitPriceA2 + " ,autoUnitPriceA3= " + autoUnitPriceA3 + " ,autoUnitPriceA4= "
                + autoUnitPriceA4 + " ,autoUnitPriceA5= " + autoUnitPriceA5 + " ,autoUnitPriceA6= " + autoUnitPriceA6 + " ,autoUnitPriceA7= " + autoUnitPriceA7);


        Integer power = Integer.parseInt(pow.split("-")[1]);
        //功率判断，过小或过大，断电，发消息 FAIL
        if(power > 1000){
            Long deviceSN = Long.parseLong(deviceId);
            Long socketSN = Long.parseLong(devicePort);
            deviceControlService.requestShutDownChargeSocket(deviceSN, socketSN, TIMEOUT);
            String title = "检测到充电电流过大，本次充电失败，如需继续充电请确认您的充电设备正常后重试！";
            sendActiveMqStartChargeFail(openId, deviceId, devicePort, title);
            log.info("充电失败,检测到充电电流过大(大于1000W), openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId );
            return;
        }

        Integer startPower = Integer.parseInt(pow.split("-")[0]);

        if (startPower < 200){
            autoUnitPrice = autoUnitPriceA1;
        }else if (startPower < 300){
            autoUnitPrice = autoUnitPriceA2;
        }else if (startPower < 350){
            autoUnitPrice = autoUnitPriceA3;
        }else if (startPower < 500){
            autoUnitPrice = autoUnitPriceA4;
        }else if (startPower < 700){
            autoUnitPrice = autoUnitPriceA5;
        }else if (startPower < 1000){
            autoUnitPrice = autoUnitPriceA6;
        }else {
            autoUnitPrice = autoUnitPriceA7;
        }

        if (StringUtils.isBlank(autoUnitPrice)) {
            //如果没有，就默认50分
            log.error("单价异常，autoUnitPrice = " + autoUnitPrice );
            autoUnitPrice = "50";
        }


        log.info("记录充电数据 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId+"," +
                "autoUnitPrice="+autoUnitPrice+",money="+money );
        chargeBatteryService.saveNewDeviceChargeHistory(openId, deviceId, devicePort, time, chargeType, money,
                walletAccount, operType, realGiftRate, companyId ,autoUnitPrice,serverResultDesc);

        String title;
        if (operType.equals("M")){
            //微信充值,临时充电
            Integer chargeMoney = Integer.parseInt(money);
            title = "您选择临时充电，充电时间" + time + "分钟" + "充电费用" + chargeMoney/100D + "元";
        }else {
            //会员充电
            if (chargeType.equals("auto")){
                //智能充电
                title = "您选择会员充电，电动车功率：" + pow + "W，充满自停,充电完成自动结费";
            }else {
                //会员定时充电
                Integer autoUnitPriceInt = Integer.parseInt(autoUnitPrice);
                Integer moneyInt = new Double(Double.parseDouble(time)/60.0D * autoUnitPriceInt).intValue();
                title = "您选择会员充电，电动车功率：" + pow + "W，充电时间" +  time
                        + "分钟" + "充电费用" + moneyInt/100D + "元";
            }

        }

        sendActiveMqStartCharge(openId, deviceId, devicePort, title);
        this.renderJson(new HnKejueResponse(RespCode.SUCCESS.getKey(),RespCode.SUCCESS.getValue()));

    }

    public void startCharge(){
        String openId = this.getPara("openId");
        String deviceId = this.getPara("deviceId");
        String devicePort = this.getPara("devicePort");
        String time = this.getPara("time");
        String chargeType = this.getPara("type");
        String money = this.getPara("money");
        String walletAccount = this.getPara("walletAccount");
        String operType = this.getPara("operType");
        String realGiftRate = this.getPara("realGiftRate");
        String companyId = this.getPara("companyId");
        String autoUnitPrice = this.getPara("autoUnitPrice");

        String autoUnitPriceA1 = this.getPara("power_a1");
        String autoUnitPriceA2 = this.getPara("power_a2");
        String autoUnitPriceA3 = this.getPara("power_a3");
        String autoUnitPriceA4 = this.getPara("power_a4");
        String autoUnitPriceA5 = this.getPara("power_a5");
        String autoUnitPriceA6 = this.getPara("power_a6");
        String autoUnitPriceA7 = this.getPara("power_a7");


        Integer startChargeStatus =9999;

        if (deviceId == null || devicePort == null || time == null){
            this.renderText(startChargeStatus.toString());
            return;
        }

        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(devicePort);
        Integer chargeTime = Integer.parseInt(time);//分钟
        Integer chargeTimeSenconds = chargeTime*60;
        log.info("开始充电 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId );

        JSONObject startChargeResltJson;
        if(chargeType.equals("auto")){
            startChargeResltJson = deviceControlService.requestStartCharge(deviceSN, socketSN, 0, TIMEOUT);
        }else {
            startChargeResltJson = deviceControlService.requestStartCharge(deviceSN, socketSN, chargeTimeSenconds, TIMEOUT);
        }

        Integer startPower = 0;
        if (null != startChargeResltJson){
            startChargeStatus = startChargeResltJson.getInteger("RESULT");
            startPower = startChargeResltJson.getInteger("POW");

        }


        log.info("开始充电结果 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + ",startChargeStatus=" + startChargeStatus);

        if (startChargeStatus.equals(1) || startChargeStatus.equals(0)){
            //充电成功 todo 0状态是暂时处理，以后硬件解决了问题会去掉

            String powerSection;

            if (startPower < 200){
                autoUnitPrice = autoUnitPriceA1;
                powerSection = "A1";
            }else if (startPower < 300){
                autoUnitPrice = autoUnitPriceA2;
                powerSection = "A2";
            }else if (startPower < 350){
                autoUnitPrice = autoUnitPriceA3;
                powerSection = "A3";
            }else if (startPower < 500){
                autoUnitPrice = autoUnitPriceA4;
                powerSection = "A4";
            }else if (startPower < 700){
                autoUnitPrice = autoUnitPriceA5;
                powerSection = "A5";
            }else if (startPower < 1000){
                autoUnitPrice = autoUnitPriceA6;
                powerSection = "A6";
            }else {
                autoUnitPrice = autoUnitPriceA7;
                powerSection = "A7";
            }

            chargeBatteryService.saveNewDeviceChargeHistory(openId, deviceId, devicePort, time, chargeType, money,
                    walletAccount, operType, realGiftRate, companyId ,autoUnitPrice,null);

            String title;

            if (operType.equals("M")){
                //微信充值,临时充电
                Integer chargeMoney = Integer.parseInt(money);
                title = "您选择临时充电，充电时间" + time + "分钟" + "充电费用" + chargeMoney/100D + "元";

            }else {
                //会员充电
                if (chargeType.equals("auto")){
                    //智能充电
                    title = "您选择会员充电，电车功率段" + powerSection + "段，充满自停,充电完成自动结费";

                }else {
                    //会员定时充电

                    Integer autoUnitPriceInt = Integer.parseInt(autoUnitPrice);
                    Integer moneyInt = new Double(Double.parseDouble(time)/60.0D * autoUnitPriceInt).intValue();

                    title = "您选择会员充电，电车功率段" + powerSection + "段，充电时间" +  time + "分钟" + "充电费用" + moneyInt/100D + "元";

                }

            }

            sendActiveMqStartCharge(openId, deviceId, devicePort, title);

        }else {
            log.error("开始充电结果 openId=" + openId + ",channelNum=" + devicePort + ",deviceId=" + deviceId + ",startChargeStatus=" + startChargeStatus);
        }

        this.renderText(startChargeStatus.toString());
    }

    private void sendActiveMqStartCharge(String openId, String deviceId, String devicePort, String title){
        JmsSender jmsSender = ActiveMQ.getSender("sendtoWeixinPush");

        String area;
        NewDevice device = newDeviceService.queryDeviceInfo(deviceId);

        if (device != null){
            String province = device.get("province");
            String city = device.get("city");
            String detail_location = device.get("detail_location");
            String name = device.get("name");

            area =  province + city + detail_location + name;

        }else {
            log.error("查询不到充电设备");

            area =  "设备ID" + deviceId + "端口" + devicePort;
        }


        WeiXin weixin = new WeiXin();
        weixin.setArea(area);
        weixin.setChannelNum(devicePort);
        weixin.setDeviceId(deviceId);
        weixin.setMessage("开始充电");
        weixin.setOpenId(openId);
        weixin.setTitle(title );
        weixin.setType("AUTO");
        weixin.setMoney("");
        weixin.setWalletAccount(500);
        weixin.setChargeTime("200");
        weixin.setOperStartTime(new Date());
        String str = JSON.toJSONString(weixin);
        System.out.println(str);
        TextMessage msg = null;
        try {
            msg = jmsSender.getSession().createTextMessage(str);
            jmsSender.sendMessage(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void powerOff(){
        String id = this.getPara("id");
        String deviceId = this.getPara("deviceId");
        String channeNum = this.getPara("channeNum");
        String openId = this.getPara("openId");
        log.info("断电开始 openId=" + openId + ",id=" + id + ",channelNum=" + channeNum + ",deviceId=" + deviceId);
        Long deviceSN = Long.parseLong(deviceId);
        Long socketSN = Long.parseLong(channeNum);
        Integer powerOffStatus = deviceControlService.requestShutDownChargeSocket(deviceSN, socketSN, TIMEOUT);

        if (null == powerOffStatus){
            powerOffStatus = 9999;
        }

        log.info("断电结果 openId=" + openId + ",id=" + id + ",channelNum=" + channeNum + ",deviceId=" + deviceId + ",powerOffStatus=" + powerOffStatus);

        if (powerOffStatus == 1) {
            //断电成功
            ChargeBatteryInfo.dao.updateEndTimeById(id);
        }

        this.renderText(powerOffStatus.toString());

    }


    public void permissionOnlineAndUpdate(){
        String deviceId = this.getPara("deviceId");
        String openId = this.getPara("openId");
        String chargePileName = this.getPara("chargePileName");
        String province = this.getPara("province");
        String city = this.getPara("city");
        String location = this.getPara("location");
        String latitude = this.getPara("latitude");
        String longitude = this.getPara("longitude");
        String powerMax = this.getPara("powerMax");
        String socketSum = this.getPara("socketSum");
        String companyId = this.getPara("companyId");
        String projectIdStr = this.getPara("projectId");
        String installed = this.getPara("installed");


        Long chargePileId = Long.parseLong(deviceId);
        Double latitudeDouble = Double.parseDouble(latitude);
        Double longitudeDouble = Double.parseDouble(longitude);
        Long powerMaxLong = Long.parseLong(powerMax);
        Integer socketSumInt = Integer.parseInt(socketSum);
        Long companyIdLong = Long.parseLong(companyId);
        Long projectId = Long.parseLong(projectIdStr);

        log.info("开始入网 deviceId=" + deviceId );


        Boolean permissionOnlineSuccess;

        if (installed.equals("1")){
            //重新设置
            permissionOnlineSuccess = true;
        }else {
            permissionOnlineSuccess = deviceControlService.requestPermissionOnLine(chargePileId, TIMEOUT);
        }

        if (permissionOnlineSuccess != null && permissionOnlineSuccess){
            log.info("新设备入网结果 deviceId=" + deviceId + ", 入网成功");

            Boolean updateResult = newDeviceService.updateInstallInfo(chargePileId, chargePileName, province, city, location, latitudeDouble,
                    longitudeDouble, powerMaxLong, socketSumInt , companyIdLong, projectId);

            if (updateResult != null && updateResult.equals(true)) {

                log.info("新设备入网结果 deviceId=" + deviceId + ", 入网成功，信息更新成功");

                HnKejueResponse json = new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
                this.renderJson(json);
            }else {
                log.info("新设备入网结果 deviceId=" + deviceId + ", 安装信息更新失败");
                HnKejueResponse json = new HnKejueResponse(RespCode.DB_UPDATE_FAIL.getKey(), RespCode.DB_UPDATE_FAIL.getValue());
                this.renderJson(json);
            }
        }else {
            log.info("新设备入网结果 deviceId=" + deviceId + ", 入网失败");
            HnKejueResponse json = new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            this.renderJson(json);
        }

    }

    public void getDeviceInfo(){
        String deviceId = this.getPara("deviceId");
        log.info("根据二维码查询设备 deviceId = " + deviceId);

        //todo qr是设备sn，这里需要完成的工作是查出对应设备的插座数和总功率，最大功率，所属公司，传给前端   后续可以考虑查出该充电桩下面所有插座的使用情况
        HnKejueResponse json = newDeviceService.queryDevice(deviceId);
        log.info("根据二维码查询设备结束：" + json);
        this.renderJson(json);
    }

    public void getNotifyDeviceInfo(){

        String deviceId = this.getPara("deviceId");
        log.info("查询充电充电编号开始，deviceId=" + deviceId);
        if (deviceId.contains("deviceid") && deviceId.contains("=") && deviceId.contains("?")) {
            deviceId = deviceId.split("=")[1].replaceAll(" ", "");
            log.info("处理URL类型的二维码成功，deviceId＝" + deviceId);
        }

        HnKejueResponse json = newDeviceService.queryNotifyDevice(deviceId);
        log.info("根据二维码查询未安装设备结束：" + json);
        this.renderJson(json);

    }

    public void getDeviceCompanyInfo(){
        String deviceId = this.getPara("deviceId");
        log.info("根据deviceId查询设备 deviceId = " + deviceId);
        HnKejueResponse json = newDeviceService.queryDevice(deviceId);
        log.info("根据deviceId查询设备结束：" + json);
        this.renderJson(json);
    }


    public void queryDeviceChargePrice() {
        String deviceId = this.getPara("deviceId");
        String projectIdStr = this.getPara("projectId");
        Long projectId = Long.parseLong(projectIdStr);
        log.info("查询充电价格开始,companyId=" + this.getPara("companyId"));
        HnKejueResponse json = newDeviceChargePriceService.queryProjectChargePrice(projectId);
        log.info("查询充电价格结束：" + json);
        this.renderJson(json);
    }

    public void quereChargeSocketStatus(){
        String deviceIdStr = this.getPara("deviceId");
        Long deviceId = Long.parseLong(deviceIdStr);
        log.info("查询设备充电插座状态开始,deviceId=" + this.getPara("deviceId"));
        HnKejueResponse json = newDeviceService.queryChargeSocketStatus(deviceId);
        log.info("根据设备充电插座状态结束：" + json);
        this.renderJson(json);

    }

    public void getAllCompany(){
        log.info("查询所有运营商公司");

        HnKejueResponse json = newDeviceService.getAllCompany();
        log.info("查询所有运营商公司结束：" + json);
        this.renderJson(json);

    }

    public void getProjectByCompanyId(){
        String companyIdStr = this.getPara("companyId");

        log.info("查询运营商公司下面所有的项目， companyId=" + companyIdStr);

        Long companyId = Long.parseLong(companyIdStr);

        HnKejueResponse json = newDeviceService.getProjectByCompanyId(companyId);
        log.info("查询运营商公司下面所有的项目结束：" + json);
        this.renderJson(json);

    }

    private void sendActiveMqStartChargeFail(String openId, String deviceId, String devicePort, String title){
        JmsSender jmsSender = ActiveMQ.getSender("sendtoWeixinPush");

        String area;
        NewDevice device = newDeviceService.queryDeviceInfo(deviceId);

        if (device != null){
            String province = device.get("province");
            String city = device.get("city");
            String detail_location = device.get("detail_location");
            String name = device.get("name");

            area =  name;

        }else {
            log.error("查询不到充电设备");

            area =  deviceId;
        }


        WeiXin weixin = new WeiXin();
        weixin.setArea(area);
        weixin.setChannelNum(devicePort);
        weixin.setDeviceId(deviceId);
        weixin.setMessage("充电失败");
        weixin.setOpenId(openId);
        weixin.setTitle(title );
        weixin.setType("FAIL");
        weixin.setMoney("");
        weixin.setWalletAccount(500);
        weixin.setChargeTime("200");
        weixin.setOperStartTime(new Date());
        String str = JSON.toJSONString(weixin);
        System.out.println(str);
        TextMessage msg = null;
        try {
            msg = jmsSender.getSession().createTextMessage(str);
            jmsSender.sendMessage(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }



}
