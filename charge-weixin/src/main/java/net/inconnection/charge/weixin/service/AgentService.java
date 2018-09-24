package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.AgentDevice;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.Company;
import net.inconnection.charge.weixin.model.Device;
import net.inconnection.charge.weixin.model.NewDevice;
import net.inconnection.charge.weixin.model.NewDeviceChargeSocket;

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
                Long deviceId = newDevice.getLong("id");
                agentDevice.setId(deviceId);
                agentDevice.setName(newDevice.getStr("name"));
                agentDevice.setStatus(newDevice.getInt("status").toString());
                agentDevice.setUpdateTime(newDevice.getDate("update_time"));

                List<NewDeviceChargeSocket> chargeSockets = NewDeviceChargeSocket.dao.queryChargeSocket(deviceId);

                Integer usedSocketSum = 0;
                Integer unUsedSocketSum = 0;
                Integer socketSum = 0;
                for (NewDeviceChargeSocket socket : chargeSockets){
                    socketSum ++;
                    if (socket.getBoolean("is_used")){
                        usedSocketSum ++;
                    }else {
                        unUsedSocketSum ++;
                    }
                }

                agentDevice.setSockeSum(socketSum);
                agentDevice.setUsedSockeSum(usedSocketSum);
                agentDevice.setNoUsedSockeSum(unUsedSocketSum);

                agentDevices.add(agentDevice);
            }

            System.out.println(agentDevices);

            return new HnKejueResponse(agentDevices, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());

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

