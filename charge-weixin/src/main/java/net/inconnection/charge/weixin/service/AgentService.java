package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.AgentDevice;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.Company;
import net.inconnection.charge.weixin.model.Device;
import net.inconnection.charge.weixin.model.NewDevice;

import java.util.ArrayList;
import java.util.List;

public class AgentService {
    private static Log logger = Log.getLog(AgentService.class);

    public AgentService() {
    }

    public HnKejueResponse getDeviceState(Long userId) {
        try {
            List<Company> companies = Company.dao.getCompanyByAgentId(userId);

            List<Long> companieIds = new ArrayList<>();

            for (Company company : companies){
                companieIds.add(company.getLong("id"));
            }

            System.out.println(companieIds);

            List<NewDevice> newDeviceList = NewDevice.dao.queryDeviceByCompanyIds(companieIds);

            System.out.println(newDeviceList);

            List<AgentDevice> agentDevices = new ArrayList<>();
            for (NewDevice newDevice : newDeviceList){
                AgentDevice agentDevice = new AgentDevice();
                agentDevice.setId(newDevice.getLong("id"));
                agentDevice.setName(newDevice.getStr("name"));
                agentDevice.setStatus(newDevice.getStr("status"));

            }



            return new HnKejueResponse(companies, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());

        } catch (Exception var3) {
            logger.error("根据userId查询运营商设备状态失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public HnKejueResponse queryDeviceByDeviceId(String deviceId) {
        try {
            Device device = Device.dao.queryDeviceByDeviceId(deviceId);
            return new HnKejueResponse(device, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据内部编号查询设备失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }
}

