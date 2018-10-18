package net.inconnection.charge.extend.service;

import com.jfinal.log.Log;
import net.inconnection.charge.extend.model.ProjectActivity;

public class ProjectActivityService {
    private static Log log = Log.getLog(ProjectActivityService.class);

    public ProjectActivity queryProjectActivityByProjectIdAndActNum(String projectId,String actNum) {
        ProjectActivity projectActivity = null;
        try {
            projectActivity = ProjectActivity.dao.queryProjectActivityByProjectIdAndActNum(projectId,actNum);
            return projectActivity;
        } catch (Exception var3) {
            log.error("根据id和actNum查询活动信息失败:", var3);
            return projectActivity;
        }
    }

}

