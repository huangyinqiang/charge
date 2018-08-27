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

    public NewDevice queryNotifyDevice(String deviceId) {
        log.info("根据设备编码查询未安装设备信息:" + deviceId);
        NewDevice newDevice = (NewDevice)this.findFirst("select * from yc_charge_pile where id = ? and is_online = 0", new Object[]{deviceId});
        log.info("根据设备编码查询未安装设备信息结果:" + newDevice);
        return newDevice;
    }

    public boolean updateInstallInfo(Long deviceId, String chargePileName, String province, String city, String location, Double latitude,
                                     Double longitude, Long powerMax, Integer socketSum , Long companyId, Long projectId) {
        log.info("更新设备安装信息：" );
        NewDevice info = new NewDevice();
        info.set("id", deviceId);
        info.set("name", chargePileName);
        info.set("province", province);
        info.set("city", city);
        info.set("detail_location", location);
        info.set("lat", latitude);
        info.set("lng", longitude);
        info.set("power_max", powerMax);
        info.set("socket_sum", socketSum);
        info.set("company_id", companyId);
        info.set("projectId", projectId);
        boolean update = info.update();
        log.info("更新设备安装信息结果:" + update);
        return update;
    }


}

