package net.inconnection.charge.extend.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class DeviceProject extends Model<DeviceProject> {
    private static final long serialVersionUID = 2513154115574837292L;
    private static final Log log = Log.getLog(DeviceProject.class);
    public static final DeviceProject dao = new DeviceProject().dao();
    public static final DeviceProject me = new DeviceProject();

    public DeviceProject() {
    }

    public List<DeviceProject> findByDeviceId(String deviceId) {
        return this.find("select * from yc_device_project where device_id = " + deviceId);
    }


}

