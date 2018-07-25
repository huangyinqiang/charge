package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class Device extends Model<Device> {
    private static final long serialVersionUID = 3097666259122699423L;
    private static final Log log = Log.getLog(Device.class);
    public static final Device dao = new Device();

    public Device() {
    }

    public Device queryDeviceByQrNum(String qrNum) {
        log.info("根据二维码查询设备信息:" + qrNum);
        Device deviceId = (Device)this.findFirst("select * from qr_match_device where status = 'Y' and qr_num = ?", new Object[]{qrNum});
        log.info("根据二维码查询设备信息结果:" + deviceId);
        return deviceId;
    }

    public Device queryDeviceByDeviceId(String deviceId) {
        log.info("根据设备内部编号查询设备信息:" + deviceId);
        Device device = (Device)this.findFirst("select * from qr_match_device where status = 'Y' and match_num = ?", new Object[]{deviceId});
        log.info("根据设备内部编号查询设备信息结果:" + device);
        return device;
    }
}

