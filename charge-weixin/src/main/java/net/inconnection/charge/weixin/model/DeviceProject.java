package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class DeviceProject extends Model<DeviceProject> {
    private static final long serialVersionUID = 918424281313379941L;
    private static final Log log = Log.getLog(DeviceProject.class);
    public static final DeviceProject me = new DeviceProject();

    public DeviceProject() {
    }

    public DeviceProject queryProjectIdByDeviceId(String deviceId) {
        log.info("根据deviceId查询活动信息:deviceId=" + deviceId);
        DeviceProject deviceProject = this.findFirst("select * from yc_device_project where device_id = ?" ,
                new Object[]{deviceId});
        log.info("根据deviceId查询活动信息:" + deviceProject);
        return deviceProject;
    }
}

