package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.model.Device;
import net.inconnection.charge.weixin.service.DeviceService;
import org.apache.commons.lang.StringUtils;

public class ScanCodeController extends Controller {
    private static Log log = Log.getLog(ScanCodeController.class);

    private static final DeviceService deviceService = new DeviceService();

    public void index() {
        String qrNum = getPara("deviceId");
        if (StringUtils.isBlank(qrNum)) {
            log.info("获取URL中deviceId为空");
            renderText("设备不在线，请稍等片刻或更换其他设备...");
            return;
        }
        HnKejueResponse queryDeviceByQrNum = deviceService.queryDeviceByQrNum(qrNum);
        if ((!(queryDeviceByQrNum.getRespCode().equals("000000"))) || (queryDeviceByQrNum.getRespObj() == null)) {
            log.info("未查询到设备");
            renderText("设备不在线,请稍等片刻或更换其他设备...");
            return;
        }
        Device device = (Device) queryDeviceByQrNum.getRespObj();
        String type = (String) device.get("type");
        Integer activityId = (Integer) device.get("activity_id");
        String deviceId = (String) device.get("match_num");

        String openId = (String) getSessionAttr("openId");
        log.info("微信直接扫码充电:" + openId);
        if (StringUtils.isBlank(openId)) {
            String calbackUrl = PropKit.get("domain") + "/weixinScanCodeOauth?qrNum=" + qrNum;
            String url = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"), calbackUrl, "status", false);
            redirect(url);
            return;
        }
        String url = PropKit.get("domain") + "/charging/choosePortAndTime?activityId=" + activityId + "&deviceId=" + deviceId + "&type=" + type;
        log.info("微信直接扫码充电url=" + url);
        redirect(url);
    }
}


