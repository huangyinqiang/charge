package net.inconnnection.charge.cost.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import net.inconnnection.charge.cost.bean.ChargeBatteryInfoBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class ChargeBatteryInfo extends Model<ChargeBatteryInfo> {
    private static final long serialVersionUID = 1239056291878940592L;
    static Log log = Log.getLog(ChargeBatteryInfo.class);
    public static final ChargeBatteryInfo dao = new ChargeBatteryInfo();

    public ChargeBatteryInfo() {
    }

    public ChargeBatteryInfo findFirst(ChargeBatteryInfoBean chargeBatteryBean) {
        return (ChargeBatteryInfo)dao.findFirst("select * from charge_battery_info where deviceId=? and devicePort=? and openId=? order by id desc limit 0,1", new Object[]{chargeBatteryBean.getDeviceId(), chargeBatteryBean.getDevicePort(), chargeBatteryBean.getOpenId()});
    }

    public Result updateWalletAccountByOpenid(ChargeBatteryInfoBean bean) {
        Result result = new Result();
        Page<Record> page = this.getRecord(bean.getOpenId(), bean.getDeviceId(), bean.getDevicePort(), this.getDate());
        Iterator var4 = page.getList().iterator();

        while(var4.hasNext()) {
            Record info = (Record)var4.next();

            try {
                log.info("循环判断可充电结束的订单:" + info.toString());
                Date operStartTime = (Date)info.get("operStartTime");
                if (operStartTime != null) {
                    Date jointime = (Date)info.get("jointime");
                    if (jointime != null) {
                        long abs1 = jointime.getTime() - operStartTime.getTime();
                        long chargeTime = Long.parseLong((String)info.get("chargeTime")) * 60L * 1000L;
                        long abs2 = operStartTime.getTime() + chargeTime - (new Date()).getTime();
                        if (abs1 >= -15000L && abs1 <= 15000L && abs2 >= -25000L) {
                            Integer id = (Integer)info.get("id");
                            log.info("更新充电结束时间 id=" + id);
                            Db.update("update charge_battery_info set endTime = ?,status=? where id = ?", new Object[]{new Date(), "F", id});
                        }
                    }
                }
            } catch (Exception var15) {
                log.error("ChargeBatteryInfo.updateWalletAccountByOpenid错误", var15);
                result.setStatus(0);
                result.setMsg(var15.getMessage());
                return result;
            }
        }

        result.setStatus(1);
        result.setMsg("成功");
        return result;
    }

    private Page<Record> getRecord(String openId, String deviceId, String channelNum, String date) {
        StringBuilder sql = new StringBuilder();
        sql.append(" from charge_battery_info cb");
        sql.append(" LEFT JOIN");
        sql.append(" (select * from chargestatus where deviceid='" + deviceId + "' and channelnum='" + channelNum + "' and status='1' order by id desc LIMIT 0,1) cs");
        sql.append(" on cb.deviceId = cs.deviceid and cb.devicePort = cs.channelnum ");
        sql.append(" where cb.openId='" + openId + "' and cb.deviceId='" + deviceId + "' and cb.devicePort='" + channelNum + "'  and cb.status='S' and operStartTime>='" + date + "' ORDER BY id desc ");
        Page<Record> page = Db.paginate(1, 1, "select cb.id, cb.openId,cb.deviceId,cb.charge,cb.walletAccount,cb.devicePort,cb.operStartTime,cb.chargeTime,cs.jointime", sql.toString());
        return page;
    }

    public String getDate() {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(5, date.get(5) - 1);
        String format = null;

        try {
            Date endDate = dft.parse(dft.format(date.getTime()));
            format = dft.format(endDate);
        } catch (Exception var6) {
            log.error("时间减一天出错");
        }

        return format;
    }
}

