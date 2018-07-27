package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class BaiDuMap extends Model<BaiDuMap> {
    private static final Log log = Log.getLog(ChargeMoneyInfo.class);
    private static final long serialVersionUID = 8169318243482203044L;
    public static final BaiDuMap dao = new BaiDuMap();

    public BaiDuMap() {
    }

    public List<BaiDuMap> queryBaiduMap() {
        log.info("查询地图信息开始");
        List<BaiDuMap> find = dao.find("select title,device_id,e_point,n_point,address,tel,remark from b_map");
        log.info("查询地图信息结果：" + find);
        return find;
    }

    public List<BaiDuMap> queryAddress(String longitude, String latitude) {
        log.info("查询坐标地址开始");
        List<BaiDuMap> find = dao.find("select title,device_id,address from b_map where e_point= ? and n_point= ?", new Object[]{longitude, latitude});
        log.info("查询坐标地址结果：" + find);
        return find;
    }

    public List<BaiDuMap> queryGaoDeMap() {
        log.info("查询高德地图信息开始");
        List<BaiDuMap> find = dao.find("select e_point,n_point from b_map");
        log.info("查询高德地图信息结果：" + find);
        return find;
    }
}
