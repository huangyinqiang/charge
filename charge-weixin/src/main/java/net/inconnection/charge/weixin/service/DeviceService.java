package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.Device;

public class DeviceService {
    private static Log logger = Log.getLog(DeviceService.class);

    public DeviceService() {
    }

    public HnKejueResponse queryDeviceByQrNum(String qrNum) {
        try {
            Device device = Device.dao.queryDeviceByQrNum(qrNum);
            return new HnKejueResponse(device, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            logger.error("根据二维码查询设备失败", var3);
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

