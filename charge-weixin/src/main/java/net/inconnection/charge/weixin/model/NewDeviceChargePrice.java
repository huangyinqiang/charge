package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class NewDeviceChargePrice extends Model<NewDeviceChargePrice> {
    private static final long serialVersionUID = -8523028502750057786L;
    private static final Log log = Log.getLog(NewDeviceChargePrice.class);
    public static final NewDeviceChargePrice dao = new NewDeviceChargePrice();

    public NewDeviceChargePrice() {
    }

    public NewDeviceChargePrice queryDeviceChargePriceByCompanyId(Long companyId) {
        log.info("根据公司ID查询设备充电费用:" + companyId);
        NewDeviceChargePrice newDeviceChargePrice = (NewDeviceChargePrice)this.findFirst("select * from yc_chargeprice where company_id = ?", new Object[]{companyId});
        log.info("根据公司ID查询设备充电费用结果:" + newDeviceChargePrice);
        return newDeviceChargePrice;
    }
}

