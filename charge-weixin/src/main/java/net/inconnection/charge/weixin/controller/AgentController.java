package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.Company;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.service.AgentService;
import net.inconnection.charge.weixin.service.ChargeInfoBatteryService;

import java.util.List;

public class AgentController extends Controller {
    private static Log log = Log.getLog(AgentController.class);

    private static AgentService agentService = new AgentService();

    public AgentController() {
    }

    public void showAgentDevice(){
        log.info("跳转到代理商设备展示页面");
        this.render("agent/deviceState.html");
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



}

