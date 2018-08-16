package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class NewDevice extends Model<NewDevice> {
    private static final long serialVersionUID = 3097666259122699423L;
    private static final Log log = Log.getLog(NewDevice.class);
    public static final NewDevice dao = new NewDevice();

    public NewDevice() {
    }

    public NewDevice queryDevice(String deviceId) {
        log.info("根据设备编码查询设备信息:" + deviceId);
        NewDevice newDevice = (NewDevice)this.findFirst("select * from yc_charge_pile where id = ?", new Object[]{deviceId});
        log.info("根据设备编码查询设备信息结果:" + newDevice);
        return newDevice;
    }

}

