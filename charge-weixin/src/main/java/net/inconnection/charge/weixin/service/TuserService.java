package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.TUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TuserService
{
    private static Log log = Log.getLog(TuserService.class);

    public HnKejueResponse findByOpenId(String openId)
    {
        try
        {
            TUser tuser = TUser.dao.queryUserByOpenId(openId);
            return new HnKejueResponse(tuser, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
        } catch (Exception e) {
            log.error("根据openid查询用户信息失败", e); }
        return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
    }

    public HnKejueResponse updateCardNumberByOpendid(String cardNumber, String openId, String tel)
    {
        int i = 0;
        String reg = "^((13[0-9])|(14[1,4-9])|(15[^4])|(16[6])|(17[0-8])|(18[0-9])|(19[8-9]))\\d{8}$";
        try {
            if (!(regular(tel, reg))) {
                log.error("手机号校验失败");
                return new HnKejueResponse(RespCode.TEL_FAIL.getKey(), RespCode.TEL_FAIL.getValue());
            }
            i = TUser.dao.updateCardNumberByOpendid(cardNumber, openId, tel);
        } catch (Exception e) {
            log.error("根据openid更新电卡信息失败", e);
            return new HnKejueResponse(Integer.valueOf(0), RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
        return new HnKejueResponse(Integer.valueOf(i), RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
    }

    private boolean regular(String key, String regular)
    {
        Pattern pattern = Pattern.compile(regular);
        Matcher m = pattern.matcher(key);

        return (m.matches());
    }
}
