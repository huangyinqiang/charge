package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.model.PileCard;

import java.util.Date;

public class PileCardService {
    private static Log log = Log.getLog(PileCardService.class);

    public Boolean save(String iccid,String deviceId) {
        log.info("保存设备卡信息：");
        try {
            PileCard pileCard = PileCard.me.queryPileCardByColumn("iccid", iccid);
            if (pileCard != null) {
                log.info("物联卡已被使用");
                return false;
            }
            PileCard model = PileCard.me.queryPileCardByColumn("deviceId", deviceId);
            if (model != null) {
                log.info("设备已绑定，更新物联卡信息");
                model.set("iccid", iccid).set("update_date", new Date()).update();
            }else{
                log.info("设备未绑定，添加信息");
                new PileCard().set("iccid", iccid).set("deviceId", deviceId).set("state", 1)
                        .set("create_date", new Date()).set("update_date", new Date()).save();
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
