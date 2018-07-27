package net.inconnection.charge.weixin.scheduler.task;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.service.ChargeInfoBatteryService;

public class StockTask implements Runnable {
    private static final Log log = Log.getLog(StockTask.class);
    ChargeInfoBatteryService chargeInfoBatteryService = new ChargeInfoBatteryService();

    public StockTask() {
    }

    public void run() {
        log.info("定时任务开始执行--“更新结束时间为空的订单”");
        this.chargeInfoBatteryService.updateEndTimeIsNull();
    }
}
