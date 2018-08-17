package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.CompanyActivity;
import net.inconnection.charge.weixin.model.MoneyMatchActivity;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class CompanyActivityService {
    private static Log logger = Log.getLog(CompanyActivityService.class);

    public CompanyActivityService() {
    }

    public HnKejueResponse queryActivityByCompanyId(String companyId) {
        if (StringUtils.isBlank(companyId)) {
            logger.error("companyId不能为空");
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            try {
                int companyIdInt = Integer.parseInt(companyId);
                List<CompanyActivity> activityList = CompanyActivity.dao.queryActivityByCompanyId(companyIdInt);
                return new HnKejueResponse(activityList, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (Exception var4) {
                logger.error("根据公司ID查询优惠信息失败", var4);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }
}
