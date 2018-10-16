package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

public class DeviceProject extends Model<DeviceProject> {
    private static final long serialVersionUID = 918424281313379941L;
    private static final Log log = Log.getLog(DeviceProject.class);
    public static final DeviceProject dao = new DeviceProject();

    public DeviceProject() {
    }
}

