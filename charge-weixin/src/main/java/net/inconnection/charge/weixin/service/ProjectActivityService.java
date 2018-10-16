package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.model.ProjectActivity;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProjectActivityService {
    private static Log logger = Log.getLog(ProjectActivityService.class);

    public ProjectActivityService() {
    }

    public  List<ProjectActivity> queryActivityByProjectId(String projectId) {
        List<ProjectActivity> activityList = new ArrayList<>();
        if (StringUtils.isBlank(projectId)) {
            logger.error("projectId不能为空");
            return activityList;
        } else {
            try {
                int projectIdInt = Integer.parseInt(projectId);
                activityList = ProjectActivity.dao.queryActivityByProjectId(projectIdInt);
                return activityList;
            } catch (Exception var4) {
                logger.error("根据projectId查询优惠信息失败", var4);
                return activityList;
            }
        }
    }
}
