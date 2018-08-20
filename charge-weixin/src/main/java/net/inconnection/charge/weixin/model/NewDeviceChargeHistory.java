package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.weixin.bean.ChargeBatteryInfoBean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewDeviceChargeHistory extends Model<NewDeviceChargeHistory> {
    private static final long serialVersionUID = 6913913317742830894L;
    private static final Log log = Log.getLog(NewDeviceChargeHistory.class);
    public static final NewDeviceChargeHistory dao = new NewDeviceChargeHistory();

    public NewDeviceChargeHistory() {
    }

    public NewDeviceChargeHistory selectById(String id) {
        log.info("根据ID查询充电信息:" + id);
        NewDeviceChargeHistory info = (NewDeviceChargeHistory)dao.findFirst("select * from yc_charge_history where id = ?", new Object[]{id});
        log.info("根据ID查询充电信息结果：" + info);
        return info;
    }

    public boolean addChargeHistory(ChargeBatteryInfoBean chargeBatteryInfoBean, final Long companyId, final String type, final int realMoney, final int giftMoney ,final Double raelRate, final Integer autoUnitPriceInt) {

        NewDeviceChargeHistory info = new NewDeviceChargeHistory();
        info.set("openId", chargeBatteryInfoBean.getOpenid());
        info.set("deviceId", chargeBatteryInfoBean.getDeviceid());
        info.set("socketSn", chargeBatteryInfoBean.getDeviceport());
        info.set("company_id", companyId);
        info.set("operStartTime", chargeBatteryInfoBean.getOperstarttime());
        info.set("operType", chargeBatteryInfoBean.getOpertype());
        info.set("chargeType", type);
        info.set("chargeMoney", chargeBatteryInfoBean.getCharge());
        info.set("realMoney", realMoney);
        info.set("giftMoney", giftMoney);
        info.set("realRate", raelRate);
        info.set("autoUnitPrice", autoUnitPriceInt);
        info.set("feeStatus", chargeBatteryInfoBean.getFeestatus());
        info.set("chargeTime", chargeBatteryInfoBean.getChargetime());
        info.set("createDate", chargeBatteryInfoBean.getCreatedate());
        info.set("startTime", chargeBatteryInfoBean.getStarttime());
        info.set("MD5", chargeBatteryInfoBean.getMd5());
        log.info("增加新设备充电记录：" + info.toString());
        boolean save = info.save();
        log.info("增加新设备充电记录结果:" + save);
        return save;
    }

    public Page<Record> queryAllByOpenId(String openId, int pageNumber, int pageSize) {
        log.info("根据openId分页查询充电记录：openId=" + openId + "+pageNumber=" + pageNumber + ",pageSzie=" + pageSize);
        Page<Record> page = Db.paginate(pageNumber, pageSize, "SELECT a.id,a.charge,a.feeStatus,a.deviceId,a.devicePort,a.startTime,a.operStartTime,a.realChargeTime,a.endTime, q.area", "FROM charge_battery_info a LEFT JOIN qr_match_device q on a.deviceId = q.match_num where a.openId = '" + openId + "' order by id desc");
        log.info("根据openId查询充电记录结果：" + page);
        return page;
    }

//    public List<ChargeBatteryInfo> queryPowerOff(String openId) {
//        log.info("查询14小时内可以远程断电的端口,openId:" + openId);
//        List<ChargeBatteryInfo> resp = dao.find("SELECT  DISTINCT cb.id, cb.openId, cb.operStartTime, cb.chargeTime, cb.deviceId, cb.devicePort, cb.operType, dev.area FROM charge_battery_info cb LEFT JOIN qr_match_device dev ON cb.deviceId = dev.match_num WHERE cb.feeStatus = 'S' AND cb.status = 'S' AND cb.operStartTime >= NOW() - INTERVAL 14 HOUR and cb.openId=? GROUP BY cb.deviceId,cb.devicePort", new Object[]{openId});
//        log.info("查询14小时内可以远程断电的端口结果" + resp);
//        return resp;
//    }

    public List<NewDeviceChargeHistory> queryPowerOff(String openId) {
        log.info("查询14小时内可以远程断电的端口,openId:" + openId);
        List<NewDeviceChargeHistory> resp = dao.find("select * from charge_battery_info where openId=? ", new Object[]{openId});
        log.info("查询14小时内可以远程断电的端口结果" + resp);
        return resp;
    }

    public NewDeviceChargeHistory queryLastBatteryInfo(String openId, Date operStartTime, String deviceId, String devicePort) {
        log.info("查询远程断电端口是否被别人占用，根据，openid= " + openId + ",operStartTime=" + operStartTime + ",deviceId=" + deviceId + ",devicePort=" + devicePort);
        NewDeviceChargeHistory chargeBatteryInfo = (NewDeviceChargeHistory)this.findFirst("SELECT id from charge_battery_info where operStartTime > ? and openId <> ? and deviceId = ? and devicePort = ? ", new Object[]{operStartTime, openId, deviceId, devicePort});
        log.info("查询远程断电端口是否被别人占用结果：" + chargeBatteryInfo);
        return chargeBatteryInfo;
    }

    public NewDeviceChargeHistory queryLastBatteryInfo(String openId, String deviceId, String channelNum) {
        log.info("查询最后一条充电记录根据，openid= " + openId + ",deviceId=" + deviceId + ",channelNum=" + channelNum);
        NewDeviceChargeHistory chargeBatteryInfo = (NewDeviceChargeHistory)this.findFirst("select id,createDate,charge from charge_battery_info where deviceId=? and devicePort=? and openId=? and status='U' order by id desc limit 0,1", new Object[]{deviceId, channelNum, openId});
        log.info("查询用户最后一条充电记录结果：" + chargeBatteryInfo);
        return chargeBatteryInfo;
    }

    public int updateStartAndStatus(String money, int account, int id) {
        log.info("更新充电记录根据，id= " + id + ",money=" + money + ",account=" + account);
        int i = Db.update("update charge_battery_info set charge=?,walletAccount=?,feeStatus=?,status=?,startTime=? where id=? and feeStatus=?", new Object[]{money, account, "S", "S", new Date(), id, "U"});
        log.info("更新充电记录结果:" + i);
        return i;
    }

    public List<NewDeviceChargeHistory> queryEndTimeIsNull() {
        log.info("查询时间小于48不时前，大于24小时前的未更新结束时间的充电信息");
        List<NewDeviceChargeHistory> resp = dao.find("SELECT id,operStartTime,chargeTime FROM charge_battery_info WHERE createDate > NOW() - interval 48 hour AND createDate < NOW() - interval 24 hour AND feeStatus = 'S' AND endTime IS NULL AND startTime IS NOT NULL");
        log.info("查询时间小于48不时前，大于24小时前的未更新结束时间的充电结果:" + resp);
        return resp;
    }

    public int updateEndTimeIsNull(Calendar calendar, String id) {
        log.info("更新充电记录为空的充电记录，id= " + id + ",calendar=" + calendar);
        int i = Db.update("update charge_battery_info set endTime = ?, status = ? where id = ? ", new Object[]{calendar.getTime(), "N", id});
        log.info("更新充电记录为空的充电记录结果:" + i);
        return i;
    }

    public int updateEndTimeById(String id) {
        log.info("更新结束时间和充电状态为F，id= " + id);
        int i = Db.update("update charge_battery_info set status = ?, endTime = ? where id=?", new Object[]{"F", new Date(), id});
        log.info("更新结束时间和充电状态为F结果:" + i);
        return i;
    }
}

