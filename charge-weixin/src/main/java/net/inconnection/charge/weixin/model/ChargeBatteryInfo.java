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

public class ChargeBatteryInfo extends Model<ChargeBatteryInfo> {
    private static final long serialVersionUID = 6913913317742830874L;
    private static final Log log = Log.getLog(ChargeBatteryInfo.class);
    public static final ChargeBatteryInfo dao = new ChargeBatteryInfo();

    public ChargeBatteryInfo() {
    }

    public ChargeBatteryInfo selectById(String id) {
        log.info("根据ID查询充电信息:" + id);
        ChargeBatteryInfo info = (ChargeBatteryInfo)dao.findFirst("select * from charge_battery_info where id = ?", new Object[]{id});
        log.info("根据ID查询充电信息结果：" + info);
        return info;
    }

    public boolean addChargeLog(ChargeBatteryInfoBean chargeBatteryInfoBean) {
        log.info("增加充电记录：" + chargeBatteryInfoBean.toString());
        ChargeBatteryInfo info = new ChargeBatteryInfo();
        info.set("openId", chargeBatteryInfoBean.getOpenid());
        info.set("deviceId", chargeBatteryInfoBean.getDeviceid());
        info.set("devicePort", chargeBatteryInfoBean.getDeviceport());
        info.set("operStartTime", chargeBatteryInfoBean.getOperstarttime());
        info.set("operType", chargeBatteryInfoBean.getOpertype());
        info.set("charge", chargeBatteryInfoBean.getCharge());
        info.set("walletAccount", chargeBatteryInfoBean.getWalletaccount());
        info.set("status", chargeBatteryInfoBean.getStatus());
        info.set("feeStatus", chargeBatteryInfoBean.getFeestatus());
        info.set("chargeTime", chargeBatteryInfoBean.getChargetime());
        info.set("createDate", chargeBatteryInfoBean.getCreatedate());
        info.set("startTime", chargeBatteryInfoBean.getStarttime());
        info.set("MD5", chargeBatteryInfoBean.getMd5());
        info.set("auto_charge", chargeBatteryInfoBean.getAutoCharge());
        boolean save = info.save();
        log.info("增加充电记录结果:" + save);
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

    public List<ChargeBatteryInfo> queryPowerOff(String openId) {
        log.info("查询14小时内可以远程断电的端口,openId:" + openId);
        List<ChargeBatteryInfo> resp = dao.find("select * from charge_battery_info where openId=? ", new Object[]{openId});
        log.info("查询14小时内可以远程断电的端口结果" + resp);
        return resp;
    }
    public List<Record> queryPowerOffByOpenId(String openId) {
        List<Record> records = Db.find("SELECT a.id,a.charge,a.feeStatus,a.deviceId,a.operType," +
                "a.devicePort,a.startTime,a.chargeTime,a.operStartTime," +
                "a.realChargeTime,a.endTime,q.area ',',ycp.name ,a.status,a.serverResultDesc FROM " +
                "charge_battery_info a LEFT JOIN qr_match_device " +
                "q ON a.deviceId = q.match_num  LEFT JOIN yc_charge_pile ycp " +
                "on a.deviceId = ycp.id WHERE a.openId = '"+openId+"'  ORDER BY id DESC");
        return records;
    }

    public ChargeBatteryInfo queryLastBatteryInfo(String openId, Date operStartTime, String deviceId, String devicePort) {
        log.info("查询远程断电端口是否被别人占用，根据，openid= " + openId + ",operStartTime=" + operStartTime + ",deviceId=" + deviceId + ",devicePort=" + devicePort);
        ChargeBatteryInfo chargeBatteryInfo = (ChargeBatteryInfo)this.findFirst("SELECT id from charge_battery_info where operStartTime > ? and openId <> ? and deviceId = ? and devicePort = ? ", new Object[]{operStartTime, openId, deviceId, devicePort});
        log.info("查询远程断电端口是否被别人占用结果：" + chargeBatteryInfo);
        return chargeBatteryInfo;
    }

    public ChargeBatteryInfo queryLastBatteryInfo(String openId, String deviceId, String channelNum) {
        log.info("查询最后一条充电记录根据，openid= " + openId + ",deviceId=" + deviceId + ",channelNum=" + channelNum);
        ChargeBatteryInfo chargeBatteryInfo = (ChargeBatteryInfo)this.findFirst("select id,createDate,charge from charge_battery_info where deviceId=? and devicePort=? and openId=? and status='U' order by id desc limit 0,1", new Object[]{deviceId, channelNum, openId});
        log.info("查询用户最后一条充电记录结果：" + chargeBatteryInfo);
        return chargeBatteryInfo;
    }

    public int updateStartAndStatus(String money, int account, int id) {
        log.info("更新充电记录根据，id= " + id + ",money=" + money + ",account=" + account);
        int i = Db.update("update charge_battery_info set charge=?,walletAccount=?,feeStatus=?,status=?,startTime=? where id=? and feeStatus=?", new Object[]{money, account, "S", "S", new Date(), id, "U"});
        log.info("更新充电记录结果:" + i);
        return i;
    }

    public List<ChargeBatteryInfo> queryEndTimeIsNull() {
        log.info("查询时间小于48不时前，大于24小时前的未更新结束时间的充电信息");
        List<ChargeBatteryInfo> resp = dao.find("SELECT id,operStartTime,chargeTime FROM charge_battery_info WHERE createDate > NOW() - interval 48 hour AND createDate < NOW() - interval 24 hour AND feeStatus = 'S' AND endTime IS NULL AND startTime IS NOT NULL");
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

