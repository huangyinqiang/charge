package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.Pager;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.Project;
import net.inconnection.charge.extend.model.ProjectActivity;
import net.inconnection.charge.extend.service.ProjectActivityService;

import java.util.ArrayList;
import java.util.List;

public class ActivityController extends BaseController{
    private static ProjectActivityService projectActivityService = new ProjectActivityService();

    public void listPage() {
        this.render("list.html");
    }
    public void addPage() {
        setAttr("projectName",this.getPara("projectName"));
        setAttr("projectId",this.getPara("projectId"));
        setAttr("companyId",this.getPara("companyId"));
        this.render("add.html");
    }
    public void updatePage() {
        ProjectActivity model = ProjectActivity.me.findById(this.getPara("id"));
        setAttr("model", model);
        this.render("update.html");
    }


    public void listData() {
        Object[] queryParams = this.getQueryParams();
        String[] properties = (String[])queryParams[0];
        String[] symbols = (String[])queryParams[1];
        Object[] values = (Object[])queryParams[2];
        String orderBy = this.getOrderBy();
        Pager pager = this.getPager();
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }
        List<Project> projectList = Project.dao.getProjectByLoginUser();
        StringBuffer sb = new StringBuffer();
        if(projectList != null){
            projectList.forEach(project -> {
                sb.append(project.getId()).append(",");
            });
        }

        if((properties != null && properties.length > 0) || "".equals(sb.toString())){
            List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_project_activity", properties, symbols, values, orderBy, this.getPager());
            this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_project_activity", properties, symbols, values));
        }else{
            List<Record> result = new ArrayList<Record>();
            List<Object> projectActivityList = Db.use(ZcurdTool.getDbSource("zcurd_busi"))
                    .query("select id,project_id,name,type,money,chargeNum,coupon,status,remark,start_time," +
                            "expiry_time,sum,province,city,location,actNum from " +
                    "yc_project_activity where project_id in (" + sb.substring(0, sb.length() - 1) + ")"+" limit " + pager.getStartRow() + ", " + pager.getRows());
            for (int i = 0; i < projectActivityList.size(); i++) {
                Object[] object = (Object[])projectActivityList.get(i);
                Record record = new Record();
                record.set("id",object[0]);
                record.set("project_id",object[1]);
                record.set("name",object[2]);
                record.set("type",object[3]);
                record.set("money",object[4]);
                record.set("chargeNum",object[5]);
                record.set("coupon",object[6]);
                record.set("status",object[7]);
                record.set("remark",object[8]);
                record.set("start_time",object[9]);
                record.set("expiry_time",object[10]);
                record.set("sum",object[11]);
                record.set("province",object[12]);
                record.set("city",object[13]);
                record.set("location",object[14]);
                record.set("actNum",object[15]);
                result.add(record);
            }
            int size = Db.use(ZcurdTool.getDbSource("zcurd_busi")).query("select * from yc_project_activity where project_id in ("+sb.substring(0,sb.length()-1)+")").size();
            this.renderDatagrid(result,size);
        }

    }


    //增加
    public void add() {
        ProjectActivity model = getModel(ProjectActivity.class, "model");
        ProjectActivity projectActivity = projectActivityService.queryProjectActivityByProjectIdAndActNum(model.get
                ("project_id").toString(), model.getActNum().toString());
        if(projectActivity == null){
            if(model.getCoupon() >0){
                model.setRemark("优惠详情：充值"+model.getMoney()/100+"元送"+model.getCoupon()/100+"元");
            }else{
                model.setRemark("无赠费");
            }
            model.setStatus("Y");
            model.setType("CH");
            model.setChargeNum(1000);
            model.save();
            renderSuccess();
        }else{
            renderFailed("已存在相同actNum的活动");
        }

    }


    public void update() {
        ProjectActivity model = ProjectActivity.me.findById(this.getPara("id"));
        model.set("name",this.getPara("model.name"));
        model.set("money",this.getParaToInt("model.money"));
        model.set("coupon",this.getParaToInt("model.coupon"));
        model.set("status",this.getPara("model.status"));
        Integer coupon = model.getCoupon();
        if(coupon >0){
            model.setRemark("优惠详情：充值"+model.getMoney()/100+"元送"+model.getCoupon()/100+"元");
        }else{
            model.setRemark("无赠费");
        }
        model.update();
        this.renderSuccess();
    }

    public void delete() {
        Long[] ids = this.getParaValuesToLong("id[]");
        for(int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            ((new ProjectActivity()).set("id", id)).delete();
        }

        this.addOpLog("[活动信息] 删除");
        this.renderSuccess();
    }

}
