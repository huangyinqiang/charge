package net.inconnection.charge.extend.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.admin.common.DBTool;
import net.inconnection.charge.admin.common.ZcurdTool;
import net.inconnection.charge.admin.common.base.BaseController;
import net.inconnection.charge.admin.common.util.StringUtil;
import net.inconnection.charge.extend.model.ChargeBatteryInfo;
import net.inconnection.charge.extend.model.Chargeprice;
import net.inconnection.charge.extend.model.Company;
import net.inconnection.charge.extend.model.CompanyActivity;
import net.inconnection.charge.extend.model.Project;

import java.util.Date;
import java.util.List;

public class CompanyController extends BaseController{



    public void listPage() {
        this.render("list.html");
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

        List<Record> list = DBTool.findByMultPropertiesDbSource("zcurd_busi", "yc_company", properties, symbols, values, orderBy, this.getPager());
        this.renderDatagrid(list, DBTool.countByMultPropertiesDbSource("zcurd_busi", "yc_company", properties, symbols, values));
    }


    public void addPage() {
        this.render("add.html");
    }


    //增加
    public void add() {
        Company model = getModel(Company.class, "model");
        model.setCreateTime(new Date());
        model.save();
        renderSuccess();
    }


    //修改页面
    public void updatePage() {
        //setAttr("dictDatastatus", Company.me.getDictDatastatus());
        setAttr("model", Company.me.findById(getPara("id")));
        render("update.html");
    }

    public void update() {
        Company model = Company.me.findById(this.getPara("id"));
        model.set("company_name",this.getPara("model.company_name"));
        model.set("taxpayer_id",this.getPara("model.taxpayer_id"));
        model.set("province",this.getPara("model.province"));
        model.set("city",this.getPara("model.city"));
        model.set("detail_location",this.getPara("model.detail_location"));
        model.set("legal_person",this.getPara("model.legal_person"));
        model.set("weixin_account",this.getPara("model.weixin_account"));
        model.set("admin_id",this.getPara("model.admin_id"));
        model.set("balance_rate",this.getPara("model.balance_rate"));
        model.set("balance_type",this.getPara("model.balance_type"));
        model.set("contact",this.getPara("model.contact"));
        model.set("contact_phone",this.getPara("model.contact_phone"));
        model.set("update_time",new Date());
        model.update();
        this.renderSuccess();
    }

    public void delete() {
        Long[] ids = this.getParaValuesToLong("id[]");
        for(int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            ((new Company()).set("id", id)).delete();
        }

        this.addOpLog("[公司信息] 删除");
        this.renderSuccess();
    }

    //修改页面
    public void updatePricePage() {

        List<Chargeprice> chargeprices = Chargeprice.dao.find("select * from yc_chargeprice where company_id="+getPara("id"));
        if (chargeprices==null || chargeprices.size()==0){
            setAttr("company_name", getPara("company_name"));
            setAttr("company_id", getPara("id"));
            render("addPrice.html");
        }else {
            setAttr("company_name", getPara("company_name"));
            setAttr("model", chargeprices.get(0));
            setAttr("id", chargeprices.get(0).getId());
            render("updatePrice.html");
        }
    }


    //增加
    public void addPrice() {
        Chargeprice model = getModel(Chargeprice.class, "model");
        model.setCreatetime(new Date());
        model.save();
        renderSuccess();
    }

    public void getCompanyByAgentId() {
        String userId = this.getPara("userId");
        List<Company> companyList = Company.dao.getCompanyByAgentId(Long.valueOf(userId));
        this.renderJson("companyList",companyList);
    }


    public void updateAdminId() {
        String adminId = this.getPara("admin_id");
        List<Company> companyList = Company.dao.getCompanyByAgentId(Long.valueOf(adminId));
        for (int n=0;n < companyList.size();n++){
            Company company = companyList.get(n);
            Company model = Company.me.findById(company.getId());
            model.set("admin_id","0");
            model.set("update_time",new Date());
            model.update();
        }
        String ids = this.getPara("id");
        if(ids!= null && StringUtil.isNotEmpty(ids)){
            String[] split = ids.split(",");
            for (int i =0 ;i < split.length;i++){
                Company model = Company.me.findById(split[i]);
                model.set("admin_id",adminId);
                model.set("update_time",new Date());
                model.update();
            }
        }


        this.renderSuccess();
    }

    public void updatePrice() {

        Chargeprice model = Chargeprice.me.findById(this.getPara("id"));
        model.set("tow_hours_price",this.getPara("model.tow_hours_price"));
        model.set("four_hours_price",this.getPara("model.four_hours_price"));
        model.set("eight_hours_price",this.getPara("model.eight_hours_price"));
        model.set("twelve_hours_price",this.getPara("model.twelve_hours_price"));
        model.set("tow_hours_mem_price",this.getPara("model.tow_hours_mem_price"));
        model.set("four_hours_mem_price",this.getPara("model.four_hours_mem_price"));
        model.set("eight_hours_mem_price",this.getPara("model.eight_hours_mem_price"));
        model.set("twelve_hours_mem_price",this.getPara("model.twelve_hours_mem_price"));
        model.set("auto_price",this.getPara("model.auto_price"));
        model.set("auto_unit_price",this.getPara("model.auto_unit_price"));

        model.setUpdatetime(new Date());
        model.update();
        renderSuccess();
    }



    public void detailPage() {
        ChargeBatteryInfo model = (ChargeBatteryInfo)ChargeBatteryInfo.me.findById(this.getParaToInt("id"));
        this.setAttr("model", model);
        this.render("detail.html");
    }


    //修改活动
    public void updateCouponPage() {
        Long company_id = getParaToLong("id");
        setAttr("company_id",company_id);
        setAttr("company_name",getPara("company_name"));
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_company_activity where company_id="+company_id);


        if (list==null  || list.size()==0){
            new CompanyActivity().setCompanyId(company_id).setType("CH").setStatus("N").setActNum(2001).setMoney(2000).setChargeNum(2000).setCoupon(500).setRemark("优惠详情：充值20元送5元").setStartTime(new Date()).save();
            new CompanyActivity().setCompanyId(company_id).setType("CH").setStatus("N").setActNum(2002).setMoney(2000).setChargeNum(2000).setCoupon(500).setRemark("优惠详情：充值20元送5元").setStartTime(new Date()).save();
            new CompanyActivity().setCompanyId(company_id).setType("CH").setStatus("N").setActNum(2003).setMoney(2000).setChargeNum(2000).setCoupon(500).setRemark("优惠详情：充值20元送5元").setStartTime(new Date()).save();
            new CompanyActivity().setCompanyId(company_id).setType("CH").setStatus("N").setActNum(2005).setMoney(2000).setChargeNum(2000).setCoupon(500).setRemark("优惠详情：充值20元送5元").setStartTime(new Date()).save();
            new CompanyActivity().setCompanyId(company_id).setType("CH").setStatus("N").setActNum(2006).setMoney(2000).setChargeNum(2000).setCoupon(500).setRemark("优惠详情：充值20元送5元").setStartTime(new Date()).save();
        }
        render("couponList.html");
    }

    //修改页面
    public void updateActivityPage() {
        //setAttr("dictDatastatus", Company.me.getDictDatastatus());
        Record compayActivity = Db.use(ZcurdTool.getDbSource("zcurd_busi")).findById("yc_company_activity", getParaToLong("id"));
        compayActivity.set("company_name",getPara("company_name"));
        Integer money =compayActivity.get("money");
        Integer coupon =compayActivity.get("coupon");
        if (null != money){
            compayActivity.set("money",money/100);
        }
        if (null != coupon){
            compayActivity.set("coupon",coupon/100);
        }
        setAttr("model", compayActivity);
        render("updateActivity.html");
    }


    public void updateActivityData() {
        CompanyActivity model = CompanyActivity.dao.findById(this.getPara("id"));
        model.set("name",this.getPara("model.name"));
        model.set("status",this.getPara("model.status"));
        model.set("type",this.getPara("model.type"));
        model.set("chargeNum",this.getPara("model.chargeNum"));
        model.set("start_time",this.getPara("model.start_time"));
        model.set("expiry_time",this.getPara("model.expiry_time"));
        model.set("sum",this.getPara("model.sum"));
        model.set("province",this.getPara("model.province"));
        model.set("city",this.getPara("model.city"));
        model.set("location",this.getPara("model.location"));

        Integer money = this.getParaToInt("model.money");
        Integer coupon = this.getParaToInt("model.coupon");
        if (money!= null && coupon!=null){
            model.set("money",money * 100);
            model.set("coupon",coupon * 100);
            model.set("remark","优惠详情：充值"+money+"元送"+coupon+"元");//优惠详情：充值20元送5元
        }
        model.update();
        this.renderSuccess();
    }


    public void CouponlistData() {
        Long company_id = getParaToLong("company_id");
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_company_activity where company_id="+company_id);
        for (Record record:list){
            record.set("company_name",getPara("company_name"));
            Integer money =record.get("money");
            Integer coupon =record.get("coupon");
            if (null != money){
                record.set("money",money/100);
            }
            if (null != coupon){
                record.set("coupon",coupon/100);
            }
        }
        this.renderDatagrid(list, list.size());
    }

    public void editProject() {
        Long company_id = getParaToLong("id");
        setAttr("company_id",company_id);
        setAttr("company_name",getPara("company_name"));
//        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_project where company_id="+company_id);

        render("projectList.html");
    }

    public void ProjectListData() {
        Long company_id = getParaToLong("company_id");
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_project where company_id="+company_id);

        this.renderDatagrid(list, list.size());
    }


    //修改页面
    public void updateProjectPage() {
        //setAttr("dictDatastatus", Company.me.getDictDatastatus());
        Record project = Db.use(ZcurdTool.getDbSource("zcurd_busi")).findById("yc_project", getParaToLong("id"));
        project.set("projectName",getPara("projectName"));

        setAttr("model", project);
        render("updateProject.html");
    }


    public void updateProjectData() {
        Project model = Project.dao.findById(this.getPara("id"));
        model.set("name",this.getPara("model.name"));
        model.set("introduce",this.getPara("model.introduce"));
        model.set("admin_name",this.getPara("model.admin_name"));
        model.set("admin_tel",this.getPara("model.admin_tel"));
        model.set("tow_hours_price",this.getPara("model.tow_hours_price"));
        model.set("four_hours_price",this.getPara("model.four_hours_price"));
        model.set("eight_hours_price",this.getPara("model.eight_hours_price"));
        model.set("twelve_hours_price",this.getPara("model.twelve_hours_price"));
        model.set("tow_hours_mem_price",this.getPara("model.tow_hours_mem_price"));
        model.set("four_hours_mem_price",this.getPara("model.four_hours_mem_price"));
        model.set("eight_hours_mem_price",this.getPara("model.eight_hours_mem_price"));
        model.set("twelve_hours_mem_price",this.getPara("model.twelve_hours_mem_price"));
        model.set("auto_price",this.getPara("model.auto_price"));
        model.set("power_a1",this.getPara("model.power_a1"));
        model.set("power_a2",this.getPara("model.power_a2"));
        model.set("power_a3",this.getPara("model.power_a3"));
        model.set("power_a4",this.getPara("model.power_a4"));
        model.set("power_a5",this.getPara("model.power_a5"));
        model.set("power_a6",this.getPara("model.power_a6"));
        model.set("power_a7",this.getPara("model.power_a7"));
        model.set("free",this.getPara("model.free"));

        model.update();
        this.renderSuccess();
    }

    public void addProjectData() {

        Project model = getModel(Project.class, "model");
        model.setCreatetime(new Date());
        model.save();
        renderSuccess();
    }

    //新增项目
    public void addProjectPage() {
        setAttr("company_id",getPara("company_id"));
        render("addProject.html");
    }



    //充值历史记录
    public void chargeListPage() {
        Long company_id = getParaToLong("id");
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_recharge_history where company_id="+company_id);
        Integer money_total=0;
        Integer real_total = 0;
        Integer gift_total = 0;
        for (Record record:list){
            record.set("company_name",getPara("company_name"));
            Integer money_sum = record.get("money_sum");
            if (money_sum!=null){
                money_total+=money_sum;
            }
            Integer real_money = record.get("real_money");
            if (real_money != null){
                real_total += real_money;
            }
            Integer coupon = record.get("coupon");
            if (coupon != null){
                gift_total += coupon;
            }


        }
        setAttr("money_total",money_total/100.00);
        setAttr("real_total",real_total/100.00);
        setAttr("gift_total",gift_total/100.00);
        setAttr("company_id",company_id);
        setAttr("company_name",getPara("company_name"));
        render("chargeList.html");
    }

    //充值历史记录数据
    public void chargelistData() {
        Long company_id = getParaToLong("company_id");
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_recharge_history where company_id="+company_id);
        for (Record record:list){
            record.set("company_name",getPara("company_name"));

            Integer money_sum=record.get("money_sum");
            record.set("money_sum",money_sum/100.00);

            Integer realMoney=record.get("real_money");
            record.set("real_money",realMoney/100.00);

            Integer coupon=record.get("coupon");
            record.set("coupon",coupon/100.00);

        }
        this.renderDatagrid(list, list.size());
    }

    //充电记录页面
    public void chargeElectricityHistoryPage() {
        Long company_id = getParaToLong("id");
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_charge_history where company_id="+company_id);
        Integer money_total=0;//总金额和
        Integer gift_total=0;//总赠送金额和
        for (Record record:list){
            record.set("company_name",getPara("company_name"));
            Integer money_sum = record.get("chargeMoney");
            Integer gift_sum = record.get("giftMoney");
            if (money_sum!=null){
                money_total+=money_sum;
            }
            if (gift_sum!=null){
                gift_total+=gift_sum;
            }
        }
        setAttr("money_total",money_total/100.00);
        setAttr("gift_total",gift_total/100.00);
        setAttr("company_id",company_id);
        setAttr("company_name",getPara("company_name"));
        render("chargeElectricityPage.html");
    }




    //充电记录页面数据
    public void chargeElectricityHistoryData() {
        Long company_id = getParaToLong("company_id");
        List<Record> list = Db.use(ZcurdTool.getDbSource("zcurd_busi")).find("select * from yc_charge_history where company_id="+company_id);
        for (Record record:list){
            record.set("company_name",getPara("company_name"));

            Integer chargeMoney=record.get("chargeMoney");
            record.set("chargeMoney",chargeMoney/100.00);

            Integer realMoney=record.get("realMoney");
            record.set("realMoney",realMoney/100.00);

            Integer giftMoney=record.get("giftMoney");
            record.set("giftMoney",giftMoney/100.00);

            Integer autoUnitPrice=record.get("autoUnitPrice");
            record.set("autoUnitPrice",autoUnitPrice/100.00);

        }
        this.renderDatagrid(list, list.size());
    }

}
