package net.inconnection.charge.extend.task;

import com.jfinal.log.Log;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.extend.model.ChargePileHistory;
import net.inconnection.charge.extend.model.ChargeSocketHistory;

import java.util.List;

public class AddSocketHistory implements Runnable {
    private static Log log = Log.getLog(AddSocketHistory.class);
    private static final String SOCKET_KEY = "socketHis";
    private static final String PILE_KEY = "pileHis";

    public AddSocketHistory(){}


    public void run() {
        log.info("定时任务开始：定时保存ChargeSocketHistory记录");

        Cache cache = Redis.use();
        List<ChargeSocketHistory> socketHis = cache.lrange(SOCKET_KEY, 0, -1);
        DBTool.use(ZcurdTool.getDbSource("zcurd_busi")).batchSave(socketHis, 1000);
        cache.del(SOCKET_KEY);
        List<ChargePileHistory> pileHis = cache.lrange(PILE_KEY, 0, -1);
        DBTool.use(ZcurdTool.getDbSource("zcurd_busi")).batchSave(pileHis, 1000);
        cache.del(PILE_KEY);

        log.info("定时任务结束：定时保存ChargeSocketHistory记录数"+socketHis.size());
    }
}
