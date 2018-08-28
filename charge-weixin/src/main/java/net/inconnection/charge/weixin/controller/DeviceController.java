package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.model.WeixinToken;
import net.inconnection.charge.weixin.service.BaiduMapService;
import net.inconnection.charge.weixin.service.DeviceService;
import net.inconnection.charge.weixin.utils.HttpUrlConnectionUtil;
import org.apache.commons.lang.StringUtils;

public class DeviceController extends Controller {
    private static final Log log = Log.getLog(DeviceController.class);
    private static final DeviceService deviceService = new DeviceService();
    private static final BaiduMapService baiduMapService = new BaiduMapService();

    public DeviceController() {
    }

    public void queryDeviceInfoByQr() {
        String qr = this.getPara("qr");
        log.info("查询充电充电编号开始，qr=" + qr);
        if (qr.contains("weixin") && qr.contains("=") && qr.contains("?")) {
            qr = qr.split("=")[1].replaceAll(" ", "");
            log.info("处理URL类型的二维码成功，qr＝" + qr);
        }

        HnKejueResponse json = deviceService.queryDeviceByQrNum(qr);
        log.info("根据二维码查询设备结束：" + json);
        this.renderJson(json);

    }


    public void queryDeviceInfoByDeviceId() {
        log.info("查询设备内部编号开始,deviceId=" + this.getPara("deviceId"));
        HnKejueResponse json = deviceService.queryDeviceByDeviceId(this.getPara("deviceId"));
        log.info("根据内部编号查询设备结束：" + json);
        this.renderJson(json);
    }

    public void getDeviceStatus() {
        String deviceId = this.getPara("deviceId");
        String deviceStatusUrl = PropKit.get("deviceStatusUrl");
        log.info("获取设备端口状态开始，deviceId=" + deviceId + ",deviceStatusUrl=" + deviceStatusUrl);
        StringBuffer sb = new StringBuffer();
        sb.append(deviceStatusUrl).append("?deviceId=").append(deviceId.replaceAll(" ", ""));
        String invokeServer = HttpUrlConnectionUtil.invokeServer(sb.toString());
        log.info("获取设备端口状态结束:" + invokeServer);
        if (StringUtils.isNotBlank(invokeServer)) {
            this.renderJson(invokeServer);
        }

    }

    public void device() {
        String status = this.getPara("status");
        WeixinToken.dao.updateStatus(status);
        this.renderJson(status);
    }

    public void baiDuMap() {
        log.info("跳转到百度地图");
        this.render("map/baidu_map.html");
    }

    public void gaoDeMap() {
        log.info("跳转到高德地图");
        this.render("map/gaode_map.html");
    }

    public void navigation() {
        log.info("跳转到导航页");
        this.render("map/navigation.html");
    }

    public void queryBaiDuMap() {
        HnKejueResponse json = baiduMapService.queryDeviceByQrNum();
        log.info("查询百度地图信息结束：" + json);
        this.renderJson(json);
    }

    public void queryGaoDeMap() {
        log.info("查询高德地图信息开始");
        String longitude = this.getPara("longitude");
        String latitude = this.getPara("latitude");
        String distance = "10000";
        HnKejueResponse json = baiduMapService.queryGaoDeMap(longitude, latitude, distance);
        log.info("查询高德地图信息结束：" + json);
        this.renderJson(json);
    }

    public void queryNearDevice() {
        log.info("查询导航信息开始");
        String longitude = this.getPara("longitude");
        String latitude = this.getPara("latitude");
        String distance = "10000";
        HnKejueResponse json = baiduMapService.queryNearDevice(longitude, latitude, distance);
        log.info("查询导航信息结束：" + json);
        this.renderJson(json);
    }

    public void queryAddress() {
        String longitude = this.getPara("longitude");
        String latitude = this.getPara("latitude");
        HnKejueResponse json = baiduMapService.queryAddressByXzy(longitude, latitude);
        log.info("查询地址结束：" + json);
        this.renderJson(json);
    }

    public static void main(String[] args) {
        Double a = 222.0D;
        double b = a / 1000.0D;
        System.out.println(b);
    }
}

