package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.Company;
import net.inconnection.charge.weixin.service.AgentService;

import java.util.List;

public class AgentController extends Controller {
    private static Log log = Log.getLog(AgentController.class);

    private static AgentService agentService = new AgentService();

    public AgentController() {
    }

    public void index() {
        log.info("跳转到代理商设备展示页面");
        this.render("agent/deviceState.html");
    }

    public void showAgentDevice(){
        log.info("跳转到代理商设备展示页面");
        this.render("agent/deviceState.html");
    }
    public void showAgentData(){
        log.info("跳转到代理商数据展示页面");
        this.render("agent/agentData.html");
    }

    public void getDeviceState(){
        String userIdStr = this.getPara("userId");

        Long userId ;
        if (userIdStr != null && !userIdStr.isEmpty()){
            userId = Long.parseLong(userIdStr);
        }else {
            log.error("userId is null or empty!");
            HnKejueResponse json = new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            this.renderJson(json);
            return;
        }

        HnKejueResponse json = agentService.getDeviceState(userId);

        this.renderJson(json);



    }


    public void getAgentData(){

        String userIdStr = this.getPara("userId");
        String startDate = this.getPara("startDate");
        String endDate = this.getPara("endDate");
        log.info("获取经营数据，用户Id"+userIdStr+",开始时间："+startDate+",结束时间："+endDate);
        Long userId ;
        if (userIdStr != null && !userIdStr.isEmpty()){
            userId = Long.parseLong(userIdStr);
        }else {
            log.error("userId is null or empty!");
            HnKejueResponse json = new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            this.renderJson(json);
            return;
        }

        List<Company> companyList = Company.dao.getCompanyByAgentId(Long.valueOf(userIdStr));
        StringBuffer companyIds = new StringBuffer();
        String companyIdStr;
        if(companyList.size() > 0){
            for (Company company : companyList){
                companyIds.append(company.get("id")).append(",");
            }
             companyIdStr = companyIds.substring(0, companyIds.toString().length() - 1);
        }else{
            log.error("用户未绑定公司！");
            HnKejueResponse json = new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            this.renderJson(json);
            return;
        }


        HnKejueResponse json = agentService.getAgetData(companyIdStr,startDate,endDate);
        log.info(json.toString());
        this.renderJson(json);



    }



}

