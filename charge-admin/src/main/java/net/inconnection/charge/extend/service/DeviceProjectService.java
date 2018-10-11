package net.inconnection.charge.extend.service;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import net.inconnection.charge.admin.common.ZcurdTool;

import java.util.List;

public class DeviceProjectService {
    private static Log logger = Log.getLog(AnalysisService.class);
    public List<Object> getProjectByDeviceId(String deviceId) {

        String sql="SELECT" +
                "	qmd.remark AS `deviceName`," +
                "	qmd.id AS `deviceId`," +
                "	yp.NAME AS `projectName`, " +
                "	yp.id AS `projectId` " +
                "FROM" +
                "	qr_match_device qmd" +
                "	LEFT JOIN yc_device_project ydp ON qmd.id = ydp.device_id" +
                "	LEFT JOIN yc_project yp ON ydp.project_id = yp.id" +
                " where qmd.id = "+deviceId;

        List<Object> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query(sql);

        return list;
    }
}
