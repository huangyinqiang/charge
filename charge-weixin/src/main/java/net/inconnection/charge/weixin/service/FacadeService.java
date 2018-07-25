package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.utils.EncDecUtils;
import net.inconnection.charge.weixin.weixinCode.service.WeinXinPayToAgentUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FacadeService {
    private static Log log = Log.getLog(FacadeService.class);
    WeinXinPayToAgentUtils weinXinPayToAgentUtils = new WeinXinPayToAgentUtils();

    public FacadeService() {
    }

    public HnKejueResponse payer(String openId, String money, String desc, String reUserName) {
        if (StringUtils.isBlank(openId)) {
            return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else if (StringUtils.isBlank(money)) {
            return new HnKejueResponse("money不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            try {
                if (StringUtils.isNotBlank(desc)) {
                    desc = URLDecoder.decode(desc, "UTF-8");
                }

                if (StringUtils.isNotBlank(reUserName)) {
                    reUserName = URLDecoder.decode(reUserName, "UTF-8");
                }

                String format = (new SimpleDateFormat("yy/MM/dd HH:mm:ss")).format(new Date());
                String MD5 = EncDecUtils.getMD5(openId + money + format);
                boolean payer = this.weinXinPayToAgentUtils.payer(openId, money, desc, reUserName, MD5);
                return new HnKejueResponse(payer, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (Exception var8) {
                log.error("企业付款失败", var8);
                return new HnKejueResponse(var8.getMessage(), RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }
}

