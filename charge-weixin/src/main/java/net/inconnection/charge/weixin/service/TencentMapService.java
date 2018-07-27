package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.TencentMapBean;
import net.inconnection.charge.weixin.bean.TencentMapSuperBean;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.BaiDuMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TencentMapService {
    private static Log log = Log.getLog(TencentMapService.class);

    public TencentMapService() {
    }

    public HnKejueResponse queryTencentMap() {
        HnKejueResponse hnKejueResponse = new HnKejueResponse();
        ArrayList tMaplist = new ArrayList();

        try {
            List<BaiDuMap> mapList = BaiDuMap.dao.queryBaiduMap();
            int i = 0;
            Iterator var6 = mapList.iterator();

            while(var6.hasNext()) {
                BaiDuMap baiDuMap = (BaiDuMap)var6.next();
                ++i;
                TencentMapBean tMapBean = new TencentMapBean();
                tMapBean.setId(i);
                tMapBean.setName((String)baiDuMap.get("title"));
                tMapBean.setLocate((String)baiDuMap.get("address"));
                tMapBean.setLongitude(Double.toString((Double)baiDuMap.get("e_point")));
                tMapBean.setLatitude(Double.toString((Double)baiDuMap.get("n_point")));
                tMaplist.add(tMapBean);
            }

            log.info("充电设备数量：" + (mapList.size() - 1));
            TencentMapSuperBean tMapSuperBean = new TencentMapSuperBean();
            tMapSuperBean.setData(tMaplist);
            tMapSuperBean.setCode(0);
            tMapSuperBean.setMsg("success");
            tMapSuperBean.setData(tMaplist);
            hnKejueResponse.setRespObj(tMapSuperBean);
            hnKejueResponse.setRespCode(RespCode.SUCCESS.getKey());
            hnKejueResponse.setRespMsg(RespCode.SUCCESS.getValue());
            return hnKejueResponse;
        } catch (Exception var8) {
            log.error("查询腾讯地图出错", var8);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }
}

