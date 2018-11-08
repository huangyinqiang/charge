package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.Company;
import net.inconnection.charge.weixin.model.NewDevice;
import net.inconnection.charge.weixin.model.NewDeviceChargeSocket;
import net.inconnection.charge.weixin.model.NewDeviceProject;

import java.util.List;

public class NewDeviceService {
    private static Log logger = Log.getLog(NewDeviceService.class);

    public HnKejueResponse queryDevice(String qrNum) {
        try {
            NewDevice device = NewDevice.dao.queryDevice(qrNum);
            return new HnKejueResponse(device, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据二维码查询设备失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public NewDevice queryDeviceInfo(String deviceId) {
        try {
            NewDevice device = NewDevice.dao.queryDevice(deviceId);
            return device;
        } catch (Exception var3) {
            return null;
        }
    }

    public HnKejueResponse queryChargeSocketStatus(Long chargePileId) {
        try {
            List<NewDeviceChargeSocket> chargeSockets = NewDeviceChargeSocket.dao.queryChargeSocketStatus(chargePileId);
            return new HnKejueResponse(chargeSockets, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据设备ID查找充电插座状态失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public HnKejueResponse queryNotifyDevice(String chargePileId) {
        try {
            NewDevice device = NewDevice.dao.queryNotifyDevice(chargePileId);
            return new HnKejueResponse(device, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据充电桩ID查询设备失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public HnKejueResponse getAllCompany() {
        try {
            List<Company> companies = Company.dao.getAllCompany();
            return new HnKejueResponse(companies, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("获取所有运营商数据失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public Boolean updateInstallInfo(Long deviceId, String chargePileName, String province, String city, String location, Double latitude,
                                             Double longitude, Long powerMax, Integer socketSum , Long companyId, Long projectId) {
        try {
            Boolean updateResult = NewDevice.dao.updateInstallInfo(deviceId, chargePileName, province, city, location, latitude,
                    longitude, powerMax, socketSum , companyId, projectId);


            return updateResult;
        } catch (Exception var3) {
            return null;
        }
    }

    public HnKejueResponse getProjectByCompanyId(Long companyId) {
        try {
            List<NewDeviceProject> newDeviceProjects = NewDeviceProject.dao.queryProjectByCompanyId(companyId);
            return new HnKejueResponse(newDeviceProjects, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据运营商ID获取项目名称数据失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }




}
