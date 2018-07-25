package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.MoneyMatchActivity;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class MoneyMatchActivityService {
    private static Log logger = Log.getLog(MoneyMatchActivityService.class);

    public MoneyMatchActivityService() {
    }

    public HnKejueResponse queryActivityByType(String activityId) {
        if (StringUtils.isBlank(activityId)) {
            logger.error("activityId不能为空");
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            try {
                int activityIdInt = Integer.parseInt(activityId);
                List<MoneyMatchActivity> activityList = MoneyMatchActivity.dao.queryActivityByType(activityIdInt);
                return new HnKejueResponse(activityList, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (Exception var4) {
                logger.error("根据充值类型查询优惠信息失败", var4);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }
}
