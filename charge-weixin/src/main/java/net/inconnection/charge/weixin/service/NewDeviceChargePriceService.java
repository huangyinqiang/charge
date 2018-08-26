package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.NewDeviceChargePrice;
import net.inconnection.charge.weixin.model.NewDeviceProject;

public class NewDeviceChargePriceService {
    private static Log logger = Log.getLog(NewDeviceChargePriceService.class);

    public HnKejueResponse queryChargePrice(Long companyId) {
        try {
            NewDeviceChargePrice device = NewDeviceChargePrice.dao.queryDeviceChargePriceByCompanyId(companyId);
            return new HnKejueResponse(device, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据公司ID查询价格失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public HnKejueResponse queryProjectChargePrice(Long projectId) {
        try {
            NewDeviceProject device = NewDeviceProject.dao.queryDeviceChargePriceByProjectID(projectId);
            return new HnKejueResponse(device, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据项目ID查询价格失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

}
