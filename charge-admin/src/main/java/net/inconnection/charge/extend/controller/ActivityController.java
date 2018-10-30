package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ProjectActivity;
import net.inconnection.charge.extend.service.ProjectActivityService;

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
        if (StringUtil.isEmpty(orderBy)) {
            orderBy = "id desc";
        }
        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_project_activity", properties, symbols, values, orderBy, this.getPager());
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_project_activity", properties, symbols, values));
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
