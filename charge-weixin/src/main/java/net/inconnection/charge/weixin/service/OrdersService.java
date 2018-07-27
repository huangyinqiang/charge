package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.OrdersBean;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.Orders;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class OrdersService {
    private static Log log = Log.getLog(OrdersService.class);

    public OrdersService() {
    }

    public HnKejueResponse saveOrders(OrdersBean req) {
        try {
            boolean tuser = Orders.dao.saveOrders(req);
            return new HnKejueResponse(tuser, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception var3) {
            log.error("保存微信支付订单信息失败", var3);
            return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }

    public HnKejueResponse selectByTranId(String transactionId) {
        if (StringUtils.isBlank(transactionId)) {
            return new HnKejueResponse("transactionId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            try {
                List<Orders> orders = Orders.dao.selectByTranId(transactionId);
                if (orders == null) {
                    log.info("未查询出已微信支付的订单");
                    return new HnKejueResponse(true, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
                } else {
                    log.info("查询出已微信支付的订单！！");
                    return new HnKejueResponse(false, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
                }
            } catch (Exception var3) {
                log.error("查询微信支付订单信息失败", var3);
                return new HnKejueResponse(false, RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }
}

