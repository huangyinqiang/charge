package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.weixin.bean.AgentDevice;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.Company;
import net.inconnection.charge.weixin.model.Device;
import net.inconnection.charge.weixin.model.NewDevice;
import net.inconnection.charge.weixin.model.NewDeviceChargeSocket;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentService {
    private static Log logger = Log.getLog(AgentService.class);
    private static ChargeMoneyService chargeMoneyService = new ChargeMoneyService();
    private static ChargeInfoBatteryService chargeInfoBatteryService = new ChargeInfoBatteryService();

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

    public HnKejueResponse getAgetData(String companyIdStr,String startDate,String endDate) {
        Map<String,Object> map = new HashMap<>();
        //充值
        List<Record> chargeMoneyList = chargeMoneyService.getSumByCompanyId(companyIdStr, startDate, endDate);
        if(chargeMoneyList!= null && chargeMoneyList.size() > 0){
            Record record = chargeMoneyList.get(0);
            map.put("rechargeSum",Float.parseFloat(record.get("sum").toString())/100F);
            map.put("rechargeCount",record.get("count"));
            if(startDate == null || StringUtils.isEmpty(startDate)){
                Record record2 = chargeMoneyList.get(1);
                map.put("rechargeTime",record2.get("rechargeTime"));
            }

        }
        //钱包消费
        List<Record> chargeInfoBatteryList = chargeInfoBatteryService.getSumByCompanyId(companyIdStr,"W",
                startDate, endDate);
        if(chargeInfoBatteryList!= null && chargeInfoBatteryList.size() > 0){
            Record record = chargeInfoBatteryList.get(0);
            map.put("walletConsumptionSum",Float.parseFloat(record.get("sum").toString())/100F);
            map.put("walletConsumptionCount",record.get("count"));
        }
        //临时消费
        List<Record> chargeInfoBatteryListTemp = chargeInfoBatteryService.getSumByCompanyId(companyIdStr,"M",
                startDate, endDate);
        if(chargeInfoBatteryListTemp!= null && chargeInfoBatteryListTemp.size() > 0){
            Record record = chargeInfoBatteryListTemp.get(0);
            map.put("consumptionTempSum",Float.parseFloat(record.get("sum").toString())/100F);
            map.put("consumptionTempCount",record.get("count"));
        }

        //总消费
        map.put("consumptionSum",Float.parseFloat(map.get("walletConsumptionSum").toString())
                +Float.parseFloat(map.get("consumptionTempSum").toString()));
        map.put("consumptionCount",Float.parseFloat(map.get("walletConsumptionCount").toString())
                +Float.parseFloat(map.get("consumptionTempCount").toString()));




        //充值大于钱包消费，平台消费=0
        //充值小于钱包消费，平台消费=钱包消费-充值
        double chargeMoneySum = Double.valueOf(map.get("rechargeSum").toString());
        double chargeInfoBatterySum = Double.valueOf(map.get("walletConsumptionSum").toString());
        if(chargeMoneySum > chargeInfoBatterySum){
            map.put("systemMonySum",0);
        }else{
            double systemMonySum = chargeInfoBatterySum - chargeMoneySum;
            map.put("systemMonySum",systemMonySum/100F);
        }


        return new HnKejueResponse(map,RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
    }
}

