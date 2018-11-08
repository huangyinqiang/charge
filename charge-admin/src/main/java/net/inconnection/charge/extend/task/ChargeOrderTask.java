package net.inconnection.charge.extend.task;

import com.jfinal.log.Log;

public class ChargeOrderTask implements Runnable {
    private static final Log log = Log.getLog(ChargeOrderTask.class);

    public ChargeOrderTask() {
    }

    public void run() {
        log.info("定时任务开始执行--“更新没有正常结束的充电订单”");
        /**没有正常结束的判断条件
         * 1、开始时间与当前时间的差超过设定时间  30分钟以上
         * 2、充电插座当前已经是关闭状态
         * 以上二者满足其一即可


        */
    }
}
