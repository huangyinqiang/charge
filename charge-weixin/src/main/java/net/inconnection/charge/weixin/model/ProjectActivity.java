package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class ProjectActivity extends Model<ProjectActivity> {
    private static final long serialVersionUID = -5261109095351354011L;
    private static final Log log = Log.getLog(ProjectActivity.class);
    public static final ProjectActivity dao = new ProjectActivity();

    public ProjectActivity() {
    }

    public List<ProjectActivity> queryActivityByProjectId(int projectId) {
        log.info("查询优惠活动信息，projectId=" + projectId);
        List<ProjectActivity> activityList = dao.find("select * from yc_project_activity where project_id = ?", new Object[]{projectId});
        log.info("查询优惠信息结果：" + activityList);
        return activityList;
    }
}

