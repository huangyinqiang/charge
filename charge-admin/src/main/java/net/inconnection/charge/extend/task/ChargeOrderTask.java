package net.inconnection.charge.extend.task;

import com.alibaba.fastjson.JSON;
import com.jfinal.log.Log;
import net.inconnection.charge.extend.chargeDevice.deviceManage.ChargePileManager;
import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargePileDevice;
import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargeSocketComponent;
import net.inconnection.charge.extend.chargeDevice.jms.ActiveMQ;
import net.inconnection.charge.extend.chargeDevice.jms.JmsSender;
import net.inconnection.charge.extend.chargeDevice.utils.DateUtil;
import net.inconnection.charge.extend.model.ChargeHistory;
import net.inconnection.charge.extend.model.ChargePile;
import net.inconnection.charge.extend.model.Tuser;
import net.inconnection.charge.extend.model.WeiXin;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChargeOrderTask implements Runnable {
    private static final Log log = Log.getLog(ChargeOrderTask.class);
    private static ChargePileManager chargePileManager = ChargePileManager.getInstance();

    public ChargeOrderTask() {
    }

    public void run() {
        log.info("定时任务开始执行--“更新没有正常结束的充电订单”");
        /** 没有正常结束的判断条件
         * 1、开始时间与当前时间的差超过设定时间  30分钟以上
         * 2、充电插座当前已经是关闭状态
         * 以上二者满足其一即可
        */

        Date date = DateUtil.dateStringToDate(DateUtil.getBeforeByHourTime(15), null);
        log.info("查询充电记录：chargestatus="+1+",startDate="+date+",endDate="+new Date());
        List<ChargeHistory> chargeHistoryList = ChargeHistory.dao.queryChargeHistory(1,date,new Date());

        for (int i = 0; i < chargeHistoryList.size(); i++) {
            ChargeHistory chargeHistory = chargeHistoryList.get(i);
            log.info("充电记录："+chargeHistory);
            String deviceId = chargeHistory.getDeviceId();
            String socketSn = chargeHistory.getSocketSn();
            Date operStartTime = chargeHistory.getOperStartTime();
            Integer chargeTime = chargeHistory.getChargeTime();
            String feeStatus = chargeHistory.getFeeStatus();
            String chargeType = chargeHistory.getChargeType();
            Integer price = chargeHistory.getAutoUnitPrice();
            Double realRate = chargeHistory.getRealRate();
            int min = DateUtil.diffSecondDate(new Date(),operStartTime)/60;
            Date endDate = DateUtil.getAfterDateByMinute(operStartTime, min);
            log.info("充电时间与当前时间比较："+(min-chargeTime));
            String message = "充电完成";

            if("temp".equals(chargeType) || "charge".equals(chargeType)){
                /**
                 * 定时充电或临时充电,超过预设时间半小时,费用已扣
                 * 直接更新充电状态
                 */
                if((min-chargeTime) > 30 ){
                    chargeHistory.setChargeStatus("5");
                    chargeHistory.setEndTime(new Date());
                    chargeHistory.setRealChargeTime(chargeTime);
                    log.info("更新后充电记录："+chargeHistory);
                    chargeHistory.update();
                    updateTuserAndPush(false,chargeHistory,chargeHistory.getOpenId(),chargeHistory.getChargeMoney(),chargeHistory.getRealMoney(),chargeHistory.getGiftMoney(), message,chargeHistory.getRealChargeTime());
                }
            }else{
                //智能充电
                ChargePileDevice chargePile = chargePileManager.getChargePile(Long.parseLong(deviceId));
                Map<Long, ChargeSocketComponent> chargeSocketMap = chargePile.getChargeSocketMap();
                ChargeSocketComponent chargeSocketComponent = chargeSocketMap.get(Long.parseLong(socketSn));
                boolean used = chargeSocketComponent.isUsed();
                /**
                 * 智能充电,插座已关闭，未扣费
                 *          插座已关闭，已扣费
                 *          插座未关闭，未扣费
                 *          插座未关闭，已扣费
                 */
                if(!used){//插座已关闭
                    if("U".equals(feeStatus)){//未扣费 根据时间扣费
                        chargeHistory.setChargeStatus("5");
                        chargeHistory.setRealChargeTime(min);
                        chargeHistory.setEndTime(endDate);
                        //向上取整
                        int hour = (int)Math.ceil(min / 60.0);
                        int totalMoney = hour * price;
                        int realMoney = 0;
                        int giftMoney = 0;
                        if(realRate > 0){
                            realMoney = new Double((double)totalMoney * realRate).intValue();
                        }
                        if(realMoney > 0){
                            giftMoney = totalMoney - realMoney;
                        }
                        chargeHistory.setChargeMoney(totalMoney);
                        chargeHistory.setRealMoney(realMoney);
                        chargeHistory.setGiftMoney(giftMoney);
                        chargeHistory.setFeeStatus("S");
                        log.info("更新后充电记录："+chargeHistory);
                        chargeHistory.update();
                        message = "监测电池充满断电";

                        updateTuserAndPush(true,chargeHistory,chargeHistory.getOpenId(),totalMoney,realMoney,giftMoney, message,chargeHistory.getRealChargeTime());
                    }
                }else{
                    //插座未关闭，判断是否错超过预设时间半小时
                    if((min-chargeTime) > 30) {//超过预设时间半小时，按13小时扣费
                        if("U".equals(feeStatus)){
                            chargeHistory.setChargeStatus("5");
                            chargeHistory.setEndTime(new Date());
                            chargeHistory.setRealChargeTime(800);
                            int totalMoney = new Double(800 / 60.0 * price).intValue();
                            int realMoney = new Double((double)totalMoney * realRate).intValue();
                            int giftMoney = totalMoney - realMoney;
                            chargeHistory.setChargeMoney(totalMoney);
                            chargeHistory.setRealMoney(realMoney);
                            chargeHistory.setGiftMoney(giftMoney);
                            chargeHistory.setFeeStatus("S");
                            log.info("更新后充电记录："+chargeHistory);
                            chargeHistory.update();
                            message = "预设时间充电完成";
                            updateTuserAndPush(true,chargeHistory,chargeHistory.getOpenId(),totalMoney,realMoney,giftMoney,message,800);
                        }
                    }
                }
            }
        }
    }


    private void updateTuserAndPush(boolean flag, ChargeHistory chargeHistory,String openId,int totalMoney,int realMoney,int giftMoney,String message,int chargeTime){
        Tuser tuser = Tuser.dao.findFirst("select * from tuser where openId = ? ", new Object[]{openId});

        if (tuser != null) {
            if(flag){
                int walletAccount = tuser.getWalletAccount() - totalMoney;
                int walletRealMoney;
                int walletGiftMoney;
                if (tuser.get("wallet_real_money") != null) {
                    walletRealMoney = tuser.getWalletRealMoney() - realMoney;
                    if (tuser.get("wallet_gift_money") != null) {
                        walletGiftMoney = tuser.getWalletGiftMoney() - giftMoney;
                    } else {
                        walletGiftMoney = 0;
                    }
                } else {
                    walletRealMoney = tuser.getWalletAccount() - realMoney;
                    walletGiftMoney = 0;
                }

                double realGiftRate = walletRealMoney / (double) walletAccount;
                tuser.setWalletAccount(walletAccount);
                tuser.setWalletRealMoney(walletRealMoney);
                tuser.setWalletGiftMoney(walletGiftMoney);
                tuser.setRealGitRate(realGiftRate);

                tuser.update();

            }



            String title;

            if (chargeHistory.getOperType().equals("M")){
                //临时充电
                title = "您选择临时充电，充电时间" + chargeHistory.getChargeTime() + "分钟，实际充电时间" + chargeHistory.getChargeTime()/60 + "分钟";
            }else {
                if (chargeHistory.getChargeType().equals("auto")){
                    //智能充电
                    title = "您选择会员充满自停，实际充电时间" + chargeHistory.getChargeTime()/60 + "分钟";

                }else {
                    //会员定时充电
                    title = "您选择会员充电，充电时间" + chargeHistory.getChargeTime() + "分钟，实际充电时间" + chargeHistory.getChargeTime()/60 + "分钟";
                }
            }


            sendActiveMqStartCharge(chargeHistory.getOpenId(), Long.valueOf(chargeHistory.getDeviceId()),Long.valueOf( chargeHistory.getSocketSn()), totalMoney, tuser.getWalletAccount(), title, message);

        }
    }


    private void sendActiveMqStartCharge(String openId, Long deviceId, Long devicePort,Integer money, Integer walletAccount, String title, String message){
        JmsSender jmsSender = ActiveMQ.getSender("sendtoWeixinPush");

        ChargePile chargePile = ChargePile.dao.findFirst("select * from yc_charge_pile where id = ? ",new Object[]{deviceId});

        String area;

        if (chargePile != null){
            String province = chargePile.get("province");
            String city = chargePile.get("city");
            String detail_location = chargePile.get("detail_location");
            String name = chargePile.get("name");

            area =  province + city + detail_location + name;

        }else {
            log.error("查询不到充电设备");

            area =  "设备ID" + deviceId + "端口" + devicePort;
        }


        WeiXin weixin = new WeiXin();
        weixin.setArea(area);
        weixin.setChannelNum(devicePort.toString());
        weixin.setDeviceId(deviceId.toString());
        weixin.setMessage(message);
        weixin.setOpenId(openId);
        weixin.setTitle(title );
        weixin.setType("NC");
        weixin.setMoney(money.toString());//单位 分
        weixin.setWalletAccount(walletAccount);//单位 分
        weixin.setChargeTime("");
        weixin.setOperStartTime(new Date());
        String str = JSON.toJSONString(weixin);
        TextMessage msg = null;
        try {
            msg = jmsSender.getSession().createTextMessage(str);
            jmsSender.sendMessage(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
