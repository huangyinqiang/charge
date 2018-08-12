package net.inconnnection.charge.cost.weixin;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import net.inconnnection.charge.cost.bean.ChargeBatteryInfoBean;
import net.inconnnection.charge.cost.model.ChargeBatteryInfo;
import net.inconnnection.charge.cost.model.Result;
import net.inconnnection.charge.cost.model.TUser;

import java.sql.SQLException;
import java.util.Date;

public class Cost {
    private static Log log = Log.getLog(Cost.class);

    public Cost() {
    }

    public static Result updatePowerCharge1(final ChargeBatteryInfoBean req) {
        log.info("开始更新根据功率充电的信息 Cost.updateIntelCharge " + req.toString());
        Result result = new Result();
        final String openId = req.getOpenId();
        TUser tUser = TUser.dao.findUserByOpenid(openId);
        int walletAccount = (Integer)tUser.get("walletAccount");
        final int account = walletAccount - Integer.parseInt(req.getMoney());
        ChargeBatteryInfoBean cb = new ChargeBatteryInfoBean();
        cb.setOpenId(openId);
        cb.setDeviceId(req.getDeviceId());
        cb.setDevicePort(req.getDevicePort());
        final ChargeBatteryInfo findFirst = ChargeBatteryInfo.dao.findFirst(cb);
        if (findFirst == null) {
            log.info("查询更新根据功率充电的信息为空！");
            result.setStatus(0);
            result.setMsg("查询最后一条充电的信息为空");
            return result;
        } else {
            Db.tx(new IAtom() {
                public boolean run() throws SQLException {
                    int update = Db.update("update charge_battery_info set charge=?,walletAccount=?,feeStatus=?,status=?,startTime=? where id=? and feeStatus=?", new Object[]{req.getMoney(), account, "S", "S", new Date(), findFirst.get("id"), "U"});
                    if (update != 1) {
                        Cost.log.error("根据功率充电 更新订单失败");
                    }

                    TUser.dao.updateWalletAccountByOpenid(openId, account);
                    return true;
                }
            });
            result.setStatus(1);
            result.setMsg("成功");
            return result;
        }
    }

    public static Result updateAutoCharge(ChargeBatteryInfoBean req) {
        final String openId = req.getOpenId();
        Result result = new Result();
        TUser tUser = TUser.dao.findUserByOpenid(openId);
        int walletAccount = (Integer)tUser.get("walletAccount");
        int returnMoney = Integer.parseInt(req.getMoney());
        ChargeBatteryInfoBean cb = new ChargeBatteryInfoBean();
        cb.setOpenId(openId);
        cb.setDeviceId(req.getDeviceId());
        cb.setDevicePort(req.getDevicePort());
        ChargeBatteryInfo findFirst = ChargeBatteryInfo.dao.findFirst(cb);
        if (findFirst == null) {
            log.info("查询更新根据功率充电的信息为空！");
            result.setStatus(0);
            result.setMsg("查询最后一条充电的信息为空");
            return result;
        } else {
            int charge = Integer.parseInt(findFirst.get("charge").toString());
            final int costMoney = charge - returnMoney;
            final int account = walletAccount + returnMoney;
            final String status = req.getStatus();
            final String realChargeTime = req.getRealChargeTime();
            final int batteryId = (Integer)findFirst.get("id");
            String add_money = "Y";
            boolean flag = Db.tx(new IAtom() {
                public boolean run() throws SQLException {
                    int update = Db.update("update charge_battery_info set charge=?,walletAccount=?,status=?,endTime=?,realChargeTime=?,add_money=? where id=?", new Object[]{costMoney, account, status, new Date(), realChargeTime, "Y", batteryId});
                    if (update != 1) {
                        Cost.log.error("充满自动停止 更新订单失败");
                        return false;
                    } else {
                        TUser.dao.updateWalletAccountByOpenid(openId, account);
                        return true;
                    }
                }
            });
            if (flag) {
                result.setStatus(1);
                result.setMsg("成功");
            } else {
                result.setStatus(0);
                result.setMsg("失败");
            }

            return result;
        }
    }

    public static Result updatePowerCharge(ChargeBatteryInfoBean req) {
        String openId = req.getOpenId();
        Result result = new Result();
        ChargeBatteryInfoBean cb = new ChargeBatteryInfoBean();
        cb.setOpenId(openId);
        cb.setDeviceId(req.getDeviceId());
        cb.setDevicePort(req.getDevicePort());
        ChargeBatteryInfo findFirst = ChargeBatteryInfo.dao.findFirst(cb);
        if (findFirst == null) {
            log.info("查询更新根据功率充电的信息为空！");
            result.setStatus(0);
            result.setMsg("查询最后一条充电的信息为空");
            return result;
        } else {
            final String status = req.getStatus();
            final String realChargeTime = req.getRealChargeTime();
            final int batteryId = (Integer)findFirst.get("id");
            String add_money = "A";
            boolean flag = Db.tx(new IAtom() {
                public boolean run() throws SQLException {
                    int update = Db.update("update charge_battery_info set status=?,endTime=?,realchargeTime=?,add_money=? where id=?", new Object[]{status, new Date(), realChargeTime, "A", batteryId});
                    if (update != 1) {
                        Cost.log.error("充满自动停止 更新订单失败");
                        return false;
                    } else {
                        return true;
                    }
                }
            });
            if (flag) {
                result.setStatus(1);
                result.setMsg("成功");
            } else {
                result.setStatus(0);
                result.setMsg("失败");
            }

            return result;
        }
    }
}

