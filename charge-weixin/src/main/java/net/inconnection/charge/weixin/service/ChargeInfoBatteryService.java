package net.inconnection.charge.weixin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.weixin.bean.ChargeBatteryInfoBean;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.bean.resp.PageResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ChargeBatteryInfo;
import net.inconnection.charge.weixin.model.NewDeviceChargeHistory;
import net.inconnection.charge.weixin.model.TUser;
import net.inconnection.charge.weixin.utils.EncDecUtils;
import net.inconnection.charge.weixin.utils.HttpUrlConnectionUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChargeInfoBatteryService {
    private static Log log = Log.getLog(ChargeInfoBatteryService.class);

    public ChargeInfoBatteryService() {
    }

    public HnKejueResponse startHttpCharging(String openId, String deviceId, String devicePort, String time, String type, String money, String walletAccount, String operType) {
        log.info("新增充电记录 openId=" + openId + ",deviceId=" + devicePort + ",time=" + time + ",type=" + type + ",money=" + money + ",walletAccount=" + walletAccount + ",operType=" + operType);

        try {
            if (StringUtils.isBlank(openId)) {
                log.error("openId不能为空");
                return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(deviceId)) {
                log.error("deviceId不能为空");
                return new HnKejueResponse("deviceId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(devicePort)) {
                log.error("devicePort不能为空");
                return new HnKejueResponse("devicePort不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(time)) {
                log.error("time不能为空");
                return new HnKejueResponse("time不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(type)) {
                log.error("type不能为空");
                return new HnKejueResponse("type不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(money)) {
                log.error("money不能为空");
                return new HnKejueResponse("money不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(walletAccount)) {
                log.error("walletAccount不能为空");
                return new HnKejueResponse("walletAccount不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(operType)) {
                log.error("operType不能为空");
                return new HnKejueResponse("operType不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            ChargeBatteryInfoBean chargeBatteryInfoBean = new ChargeBatteryInfoBean();
            chargeBatteryInfoBean.setOpenid(openId);
            chargeBatteryInfoBean.setDeviceid(deviceId);
            chargeBatteryInfoBean.setDeviceport(devicePort);
            chargeBatteryInfoBean.setOperstarttime(new Date());
            chargeBatteryInfoBean.setChargetime(time);
            chargeBatteryInfoBean.setOpertype(operType);
            chargeBatteryInfoBean.setCharge(money);
            int walletAccountInt = Integer.parseInt(walletAccount);
            int moneyInt = Integer.parseInt(money);
            if (operType.equals("M")) {
                walletAccountInt += moneyInt;
                chargeBatteryInfoBean.setWalletaccount(walletAccountInt);
            } else {
                walletAccountInt -= moneyInt;
                chargeBatteryInfoBean.setWalletaccount(walletAccountInt);
            }

            chargeBatteryInfoBean.setStatus("U");
            chargeBatteryInfoBean.setFeestatus("U");
            chargeBatteryInfoBean.setCreatedate(new Date());
            chargeBatteryInfoBean.setAutoCharge("N");
            if (type.equals("auto")) {
                chargeBatteryInfoBean.setAutoCharge("Y");
            }

            String format = (new SimpleDateFormat("yy/MM/dd HH:mm:ss")).format(new Date());
            String MD5 = EncDecUtils.getMD5(openId + deviceId + format);
            this.addAndUpdate(openId, MD5, operType, chargeBatteryInfoBean, walletAccountInt);
            boolean invokeServer = this.invokeServer(openId, deviceId, devicePort, time, type, money);
            if (invokeServer) {
                log.info("调用服务器充电成功");
                return new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            }
        } catch (Exception var15) {
            log.error("新增充电记录失败", var15);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }

        log.info("调用服务器充电失败");
        return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
    }

    private boolean invokeServer(String openId, String deviceId, String devicePort, String time, String type, String money) {
        String chargingServerUrl = PropKit.get("kejueServiceUrl");
        StringBuffer sb = new StringBuffer();
        sb.append(chargingServerUrl).append("?devicelId=").append(deviceId.replaceAll(" ", "")).append("&channelNum=").append(devicePort).append("&chargeTime=").append(time).append("&openId=").append(openId).append("&type=").append(type).append("&money=").append(money);
        String invokeServer = HttpUrlConnectionUtil.invokeServer(sb.toString());
        JSONObject jsonObject = JSON.parseObject(invokeServer);
        return (Boolean)jsonObject.get("success");
    }

    public Boolean saveNewDeviceChargeHistory(String openId, String deviceId, String devicePort, String time, String type,
                                              String money, String walletAccount, String operType, String
                                                      realGiftRate, String companyId, String autoUnitPrice,String pow) {
        log.info("新增新设备充电记录 openId=" + openId + ",deviceId=" + devicePort + ",time=" + time + ",type=" + type + ",money=" + money
                + ",walletAccount=" + walletAccount + ",operType=" + operType + ",realGiftRate=" + realGiftRate);

        try {
            if (StringUtils.isBlank(openId)) {
                log.error("openId不能为空");
                return false;
            }

            if (StringUtils.isBlank(deviceId)) {
                log.error("deviceId不能为空");
                return false;
            }

            if (StringUtils.isBlank(devicePort)) {
                log.error("devicePort不能为空");
                return false;
            }

            if (StringUtils.isBlank(time)) {
                log.error("time不能为空");
                return false;
            }

            if (StringUtils.isBlank(type)) {
                log.error("type不能为空");
                return false;
            }

            if (StringUtils.isBlank(money)) {
                log.error("money不能为空");
                return false;
            }

            if (StringUtils.isBlank(walletAccount)) {
                log.error("walletAccount不能为空");
                return false;
            }

            if (StringUtils.isBlank(operType)) {
                log.error("operType不能为空");
                return false;
            }

            if (StringUtils.isBlank(companyId)) {
                log.error("companyId不能为空");
                return false;
            }

            if (StringUtils.isBlank(autoUnitPrice)) {
                //如果没有，就默认50分
                autoUnitPrice = "50";
            }

            Integer autoUnitPriceInt = Integer.parseInt(autoUnitPrice);

            Long companyIdInt = Long.parseLong(companyId);

            if (StringUtils.isBlank(realGiftRate)) {
                realGiftRate = "1";
            }
            Double raelRate = Double.parseDouble(realGiftRate);//用户账号中的实充金额占比

            ChargeBatteryInfoBean chargeBatteryInfoBean = new ChargeBatteryInfoBean();
            chargeBatteryInfoBean.setOpenid(openId);
            chargeBatteryInfoBean.setDeviceid(deviceId);
            chargeBatteryInfoBean.setDeviceport(devicePort);
            chargeBatteryInfoBean.setOperstarttime(new Date());
            chargeBatteryInfoBean.setChargetime(time);
            chargeBatteryInfoBean.setOpertype(operType);
            if(pow != null && StringUtils.isNotEmpty(pow)){
                chargeBatteryInfoBean.setServerresultdesc(pow);
            }
            int walletAccountInt = Integer.parseInt(walletAccount);
            Integer moneyInt = Integer.parseInt(money);
            if (operType.equals("W") && type.equals("charge")){
                //按照时间计算总价
                moneyInt = new Double(Double.parseDouble(time)/60.0 * autoUnitPriceInt).intValue();
            }
            chargeBatteryInfoBean.setCharge(moneyInt.toString());

            Double walletRealAccountDouble = (((double)walletAccountInt-moneyInt)*raelRate) ;
            int walletRealAccount = walletRealAccountDouble.intValue();
            int walletGiftAccount = walletAccountInt - moneyInt - walletRealAccount;

            int realMoney = new Double((double) moneyInt*raelRate).intValue();
            int giftMoney = moneyInt - realMoney;

            if (operType.equals("M")) {
                //微信充值的，账户余额不变
                chargeBatteryInfoBean.setWalletaccount(walletAccountInt);
            } else {
                walletAccountInt -= moneyInt;
                chargeBatteryInfoBean.setWalletaccount(walletAccountInt);
            }

            chargeBatteryInfoBean.setStatus("U");
            chargeBatteryInfoBean.setFeestatus("S");
            chargeBatteryInfoBean.setCreatedate(new Date());
            chargeBatteryInfoBean.setStarttime(new Date());
            chargeBatteryInfoBean.setAutoCharge("N");
            if (type.equals("auto")) {
                chargeBatteryInfoBean.setAutoCharge("Y");
                //智能充电等到充电结束再结费
                chargeBatteryInfoBean.setFeestatus("U");
            }

            String format = (new SimpleDateFormat("yy/MM/dd HH:mm:ss")).format(new Date());
            String MD5 = EncDecUtils.getMD5(openId + deviceId + format);
            this.addAndUpdateNewDevice(openId, MD5, operType, type, chargeBatteryInfoBean, walletAccountInt, walletRealAccount, walletGiftAccount, companyIdInt, realMoney, giftMoney, raelRate,autoUnitPriceInt);
        } catch (Exception var15) {
            log.error("新增充电记录失败", var15);
            return false;
        }

        return true;
    }
    private void addAndUpdateNewDevice(final String openId, final String MD5, final String operType, final String type, final ChargeBatteryInfoBean chargeBatteryInfoBean,
                                       final int walletAccountInt , final int walletRealAccount, final int walletGiftAccount, final Long companyId, final int realMoney,
                                       final int giftMoney, final Double raelRate, final Integer autoUnitPriceInt) {
        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                chargeBatteryInfoBean.setMd5(MD5);
                ChargeBatteryInfo.dao.addChargeLog(chargeBatteryInfoBean);
                NewDeviceChargeHistory.dao.addChargeHistory(chargeBatteryInfoBean, companyId, type, realMoney, giftMoney, raelRate, autoUnitPriceInt);
                if (operType.equals("W") && type.equals("charge")) {
                    TUser.dao.updateWalletAccount(walletAccountInt, walletRealAccount ,walletGiftAccount, openId);
                }

                return true;
            }
        });
    }

    private void addAndUpdate(final String openId, final String MD5, final String operType, final ChargeBatteryInfoBean chargeBatteryInfoBean, final int walletAccountInt) {
        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                chargeBatteryInfoBean.setMd5(MD5);
                ChargeBatteryInfo.dao.addChargeLog(chargeBatteryInfoBean);
                if (operType.equals("M")) {
                    TUser.dao.updateWalletAccount(walletAccountInt, openId);
                }

                return true;
            }
        });
    }

    public HnKejueResponse queryAllByOpenId(String openId, String pageNumber, String pageSize) {
        int num = 1;
        int size = 5;
        if (StringUtils.isBlank(openId)) {
            log.error("openId不能为空");
            return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            if (StringUtils.isNotBlank(pageNumber)) {
                num = Integer.valueOf(pageNumber);
            }

            if (StringUtils.isNotBlank(pageSize)) {
                size = Integer.valueOf(pageSize);
            }

            try {
                Page<Record> chargingList = ChargeBatteryInfo.dao.queryAllByOpenId(openId, num, size);
                List<Object> object = new ArrayList();

                Record chargeInfo;
                for(Iterator var9 = chargingList.getList().iterator(); var9.hasNext(); object.add(chargeInfo)) {
                    chargeInfo = (Record)var9.next();
                    String deviceId = (String)chargeInfo.get("deviceId");
                    Double money = Double.parseDouble((String)chargeInfo.get("charge"));
                    deviceId = deviceId.substring(0, 6) + "******" + deviceId.substring(deviceId.length() - 3);
                    chargeInfo.set("deviceId", deviceId);
                    chargeInfo.set("charge", money / 100.0D);
                    String feeStatus = (String)chargeInfo.get("feeStatus");
                    if (StringUtils.isNotBlank(feeStatus) && feeStatus.equals("U")) {
                        chargeInfo.set("charge", 0);
                    }
                }

                PageResponse resp = new PageResponse();
                resp.setList(object);
                resp.setPageNumber(chargingList.getPageNumber());
                resp.setPageSize(chargingList.getPageSize());
                resp.setTotalCouts(chargingList.getTotalRow());
                return new HnKejueResponse(resp, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (NumberFormatException var13) {
                log.error("分页查询充电记录出错：" + var13);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }

    public HnKejueResponse updateIntelCharge(String deviceId, String channelNum, final String openId, final String money) {
        if (StringUtils.isBlank(deviceId)) {
            return new HnKejueResponse("设备ID不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(channelNum)) {
            return new HnKejueResponse("设备端口不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(openId)) {
            return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(money)) {
            return new HnKejueResponse("具体充电费用不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            try {
                ChargeBatteryInfo batteryInfo = ChargeBatteryInfo.dao.queryLastBatteryInfo(openId, deviceId, channelNum);
                final Integer id = (Integer)batteryInfo.get("id");
                TUser tUser = TUser.dao.queryUserByOpenId(openId);
                int walletAccount = (Integer)tUser.get("walletAccount");
                final int account = walletAccount - Integer.parseInt(money);
                Db.tx(new IAtom() {
                    public boolean run() throws SQLException {
                        ChargeBatteryInfo.dao.updateStartAndStatus(money, account, id);
                        TUser.dao.updateWalletAccount(account, openId);
                        return true;
                    }
                });
            } catch (Exception var10) {
                log.error("根据功率扣费，更新具体金额和充电时间出错：" + var10);
                return new HnKejueResponse(var10.getMessage(), RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            return new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        }
    }

    public HnKejueResponse updateEndTimeIsNull() {
        List<ChargeBatteryInfo> resp = ChargeBatteryInfo.dao.queryEndTimeIsNull();
        if (resp != null && resp.size() != 0) {
            try {
                log.info("需要更新结束时间的数量:" + resp.size());
                Calendar calendar = Calendar.getInstance();
                Iterator var4 = resp.iterator();

                while(var4.hasNext()) {
                    ChargeBatteryInfo chargeBatteryInfo = (ChargeBatteryInfo)var4.next();
                    String id = chargeBatteryInfo.get("id").toString();
                    Date operStartTime = (Date)chargeBatteryInfo.get("operStartTime");
                    long time = operStartTime.getTime();
                    String chargeTime = (String)chargeBatteryInfo.get("chargeTime");
                    long endTime = time + Long.parseLong(chargeTime) * 60L * 1000L;
                    calendar.setTimeInMillis(endTime);
                    ChargeBatteryInfo.dao.updateEndTimeIsNull(calendar, id);
                }

                return new HnKejueResponse(resp, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (NumberFormatException var12) {
                log.error("查询需要更新结束时间的充电信息出错：" + var12);
                return new HnKejueResponse(var12.getMessage(), RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        } else {
            log.info("未查询到需要更新结束时间的充电信息！！");
            return new HnKejueResponse(RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        }
    }

    public List<Record>  getSumByCompanyId(String companyIdStr,String operType, String startDate, String endDate) {
        StringBuffer sql =new StringBuffer(" SELECT ifnull(sum(realMoney),0) as `sum` ," +
                " COUNT(realMoney) as `count` FROM yc_charge_history ");
        sql.append(" where feeStatus='S' ");
        sql.append(" and company_id in (").append(companyIdStr).append(")");
        sql.append(" and operType ='").append(operType).append("'");

        if(startDate!= null && StringUtils.isNotEmpty(startDate)){
            sql.append(" and startTime >='").append(startDate).append("'");
        }
        if(endDate!= null && StringUtils.isNotEmpty(endDate)){
            sql.append(" and startTime <='").append(endDate).append("'");
        }
        List<Record> records = Db.find(sql.toString());
        return records;
    }
}

