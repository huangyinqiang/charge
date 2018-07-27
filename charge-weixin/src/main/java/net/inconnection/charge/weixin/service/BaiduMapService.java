package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.BaiduMapBean;
import net.inconnection.charge.weixin.bean.GaoDeBean;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.BaiDuMap;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaiduMapService {
    private static Log log = Log.getLog(BaiduMapService.class);
    private static double EARTH_RADIUS = 6371.393D;

    public BaiduMapService() {
    }

    public HnKejueResponse queryDeviceByQrNum() {
        HnKejueResponse hnKejueResponse = new HnKejueResponse();
        ArrayList list = new ArrayList();

        try {
            List<BaiDuMap> queryBaiduMapList = BaiDuMap.dao.queryBaiduMap();
            Iterator var5 = queryBaiduMapList.iterator();

            while(var5.hasNext()) {
                BaiDuMap baiDuMap = (BaiDuMap)var5.next();
                BaiduMapBean bean = new BaiduMapBean();
                bean.setTitle((String)baiDuMap.get("title"));
                StringBuffer sb = new StringBuffer();
                sb.append(baiDuMap.get("e_point")).append(",").append(baiDuMap.get("n_point"));
                bean.setPoint(sb.toString());
                bean.setAddress(baiDuMap.getStr("address"));
                bean.setTel((String)baiDuMap.get("tel"));
                list.add(bean);
            }

            hnKejueResponse.setRespObj(list);
            hnKejueResponse.setRespCode(RespCode.SUCCESS.getKey());
            hnKejueResponse.setRespMsg(RespCode.SUCCESS.getValue());
            return hnKejueResponse;
        } catch (Exception var8) {
            log.error("查询地图出错", var8);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public HnKejueResponse queryGaoDeMap(String longitude, String latitude, String distance) {
        log.info("手机坐标经度：" + longitude + " 手机坐标维度：" + latitude + " 距离范围(米)：" + distance);
        if (StringUtils.isBlank(longitude)) {
            log.error("经度不能为空");
            return new HnKejueResponse("经度不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(latitude)) {
            log.error("纬度不能为空");
            return new HnKejueResponse("纬度不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(distance)) {
            log.error("距离不能为空");
            return new HnKejueResponse("距离不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            HnKejueResponse hnKejueResponse = new HnKejueResponse();
            ArrayList list = new ArrayList();

            try {
                List<BaiDuMap> mapList = BaiDuMap.dao.queryGaoDeMap();
                int i = 0;
                Iterator var9 = mapList.iterator();

                while(var9.hasNext()) {
                    BaiDuMap baiDuMap = (BaiDuMap)var9.next();
                    Double lng1 = Double.valueOf(longitude);
                    Double lat1 = Double.valueOf(latitude);
                    Double lng2 = (Double)baiDuMap.get("e_point");
                    Double lat2 = (Double)baiDuMap.get("n_point");
                    double getDistance = GetDistance(lat1, lng1, lat2, lng2);
                    Double valueOf = Double.valueOf(distance);
                    if (valueOf > getDistance) {
                        ++i;
                        GaoDeBean bean = new GaoDeBean();
                        StringBuffer sb = new StringBuffer();
                        sb.append("http://webapi.amap.com/theme/v1.3/markers/n/mark_b");
                        sb.append(i);
                        sb.append(".png");
                        bean.setIcon(sb.toString());
                        List<Double> doubleList = new ArrayList();
                        doubleList.add((Double)baiDuMap.get("e_point"));
                        doubleList.add((Double)baiDuMap.get("n_point"));
                        bean.setPosition(doubleList);
                        list.add(bean);
                    }

                    if (list.size() >= 10) {
                        log.info("附近的充电桩大于" + list.size() + "台");
                        break;
                    }
                }

                List<Double> doubleList = new ArrayList();
                GaoDeBean bean = new GaoDeBean();
                doubleList.add(Double.parseDouble(longitude));
                doubleList.add(Double.parseDouble(latitude));
                bean.setPosition(doubleList);
                list.add(bean);
                log.info("充电设备数量：" + (list.size() - 1));
                hnKejueResponse.setRespObj(list);
                hnKejueResponse.setRespCode(RespCode.SUCCESS.getKey());
                hnKejueResponse.setRespMsg(RespCode.SUCCESS.getValue());
                return hnKejueResponse;
            } catch (Exception var20) {
                log.error("查询高德地图出错", var20);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }

    public HnKejueResponse queryNearDevice(String longitude, String latitude, String distance) {
        log.info("手机坐标经度：" + longitude + " 手机坐标维度：" + latitude + " 距离范围(米)：" + distance);
        if (StringUtils.isBlank(longitude)) {
            log.error("经度不能为空");
            return new HnKejueResponse("经度不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(latitude)) {
            log.error("纬度不能为空");
            return new HnKejueResponse("纬度不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(distance)) {
            log.error("距离不能为空");
            return new HnKejueResponse("距离不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            HnKejueResponse hnKejueResponse = new HnKejueResponse();
            ArrayList list = new ArrayList();

            try {
                List<BaiDuMap> queryBaiduMapList = BaiDuMap.dao.queryBaiduMap();
                Iterator var8 = queryBaiduMapList.iterator();

                while(var8.hasNext()) {
                    BaiDuMap baiDuMap = (BaiDuMap)var8.next();
                    BaiduMapBean bean = new BaiduMapBean();
                    Double lng1 = Double.valueOf(longitude);
                    Double lat1 = Double.valueOf(latitude);
                    Double lng2 = (Double)baiDuMap.get("e_point");
                    Double lat2 = (Double)baiDuMap.get("n_point");
                    double getDistance = GetDistance(lat1, lng1, lat2, lng2);
                    Double valueOf = Double.valueOf(distance);
                    if (valueOf > getDistance) {
                        bean.setLongitude(lng2);
                        bean.setLatitude(lat2);
                        bean.setTitle(baiDuMap.getStr("title"));
                        bean.setAddress(baiDuMap.getStr("address"));
                        bean.setTel((String)baiDuMap.get("tel"));
                        bean.setDistance(getDistance / 1000.0D);
                        list.add(bean);
                    }

                    if (list.size() >= 10) {
                        log.info("附近的充电桩大于" + list.size() + "台");
                        break;
                    }
                }

                hnKejueResponse.setRespObj(list);
                hnKejueResponse.setRespCode(RespCode.SUCCESS.getKey());
                hnKejueResponse.setRespMsg(RespCode.SUCCESS.getValue());
                return hnKejueResponse;
            } catch (Exception var17) {
                log.error("查询导航信息出错", var17);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }

    public HnKejueResponse queryAddressByXzy(String longitude, String latitude) {
        log.info("查询坐标地址，经度：" + longitude + " 维度：" + latitude);
        if (StringUtils.isBlank(longitude)) {
            log.error("经度不能为空");
            return new HnKejueResponse("经度不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(latitude)) {
            log.error("纬度不能为空");
            return new HnKejueResponse("纬度不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            HnKejueResponse hnKejueResponse = new HnKejueResponse();

            try {
                List<BaiDuMap> address = BaiDuMap.dao.queryAddress(longitude, latitude);
                log.info("查询坐标地址结果：" + address);
                if (address.size() >= 1) {
                    hnKejueResponse.setRespObj(address.get(0));
                }

                hnKejueResponse.setRespCode(RespCode.SUCCESS.getKey());
                hnKejueResponse.setRespMsg(RespCode.SUCCESS.getValue());
                return hnKejueResponse;
            } catch (Exception var5) {
                log.error("查询坐标地址出错", var5);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }

    private static double rad(double d) {
        return d * 3.141592653589793D / 180.0D;
    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0D), 2.0D) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0D), 2.0D)));
        s *= EARTH_RADIUS;
        s = (double)Math.round(s * 1000.0D);
        return s;
    }
}

