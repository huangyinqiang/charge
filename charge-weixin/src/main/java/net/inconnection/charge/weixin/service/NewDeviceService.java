package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.NewDevice;

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



}
