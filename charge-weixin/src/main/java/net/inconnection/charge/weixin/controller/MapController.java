package net.inconnection.charge.weixin.controller;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.service.BaiduMapService;
import net.inconnection.charge.weixin.service.TencentMapService;

public class MapController extends Controller {
    private static final Log log = Log.getLog(MapController.class);
    private final BaiduMapService baiduMapService = new BaiduMapService();
    private final TencentMapService tMapService = new TencentMapService();

    public MapController() {
    }

    public void baiDuMap() {
        log.info("跳转到百度地图");
        this.render("map/baidu_map.html");
    }

    public void gaoDeMap() {
        log.info("跳转到高德地图");
        this.render("map/gaode_map.html");
    }

    public void tencentMap() {
        log.info("跳转到腾讯地图");
        this.render("map/tencent_map.html");
    }

    public void navigation() {
        log.info("跳转到导航页");
        this.render("map/navigation.html");
    }

    public void queryBaiDuMap() {
        log.info("查询百度地图信息开始");
        HnKejueResponse json = this.baiduMapService.queryDeviceByQrNum();
        log.info("查询百度地图信息结束：" + json);
        this.renderJson(json);
    }

    public void queryGaoDeMap() {
        log.info("查询高德地图信息开始");
        String longitude = this.getPara("longitude");
        String latitude = this.getPara("latitude");
        String distance = "10000";
        HnKejueResponse json = this.baiduMapService.queryGaoDeMap(longitude, latitude, distance);
        log.info("查询高德地图信息结束：" + json);
        this.renderJson(json);
    }

    public void queryTencentMap() {
        log.info("查询腾讯地图信息开始");
        HnKejueResponse json = this.tMapService.queryTencentMap();
        log.info("查询腾讯地图信息结束：" + json);
        this.renderJson(json);
    }

    public void queryNearDevice() {
        log.info("查询导航信息开始");
        String longitude = this.getPara("longitude");
        String latitude = this.getPara("latitude");
        String distance = "10000";
        HnKejueResponse json = this.baiduMapService.queryNearDevice(longitude, latitude, distance);
        log.info("查询导航信息结束：" + json);
        this.renderJson(json);
    }

    public void queryAddress() {
        String longitude = this.getPara("longitude");
        String latitude = this.getPara("latitude");
        HnKejueResponse json = this.baiduMapService.queryAddressByXzy(longitude, latitude);
        log.info("查询地址结束：" + json);
        this.renderJson(json);
    }

    public static void main(String[] args) {
        Double a = 222.0D;
        double b = a / 1000.0D;
        System.out.println(b);
    }
}

