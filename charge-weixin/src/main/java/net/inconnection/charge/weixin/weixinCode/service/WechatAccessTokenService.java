package net.inconnection.charge.weixin.weixinCode.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import net.inconnection.charge.weixin.bean.token.WeixinTokenBean;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.exception.BizException;
import net.inconnection.charge.weixin.model.WeixinToken;
import net.inconnection.charge.weixin.utils.HttpUrlConnectionUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class WechatAccessTokenService {
    private static final Log logger = Log.getLog(WechatAccessTokenService.class);
    private static final String appId = PropKit.get("appId");
    private static final String appSecret = PropKit.get("appSecret");
    public static final String weixin_jssdk_acceToken_url;

    static {
        weixin_jssdk_acceToken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
    }

    public WechatAccessTokenService() {
    }

    public String getAccessToken() throws Exception {
        logger.info("开始获取数据库accessToken");
        WeixinToken weixinToken = WeixinToken.dao.queryAccessTokenByAppId(appId);
        if (weixinToken != null && weixinToken.get("accessToken") != null) {
            String accessToken = (String)weixinToken.get("accessToken");
            if (StringUtils.isBlank(accessToken)) {
                logger.info("accessToken不存在，新建accessToken");
                return this.saveAccessToken();
            } else {
                Date createTime = (Date)weixinToken.get("createTime");
                long m = (new Date()).getTime() - createTime.getTime();
                long tokenTime = m / 1000L;
                String oldAccessToken;
                if (tokenTime > 7200L) {
                    logger.info("accessToken超时，重新获取");
                    oldAccessToken = this.updateAccessToken();
                    return oldAccessToken;
                } else {
                    oldAccessToken = (String)weixinToken.get("accessToken");
                    logger.info("accessToken未超时，返回" + oldAccessToken);
                    return oldAccessToken;
                }
            }
        } else {
            logger.info("accessToken不存在，新建accessToken");
            return this.saveAccessToken();
        }
    }

    public String updateAccessToken() {
        String newAccessToken = this.getWechatAccessTokenByHttp();
        WeixinTokenBean bean = new WeixinTokenBean();
        bean.setAccessToken(newAccessToken);
        bean.setAppId(appId);
        WeixinToken.dao.updateAccessTokenByAppId(bean);
        return newAccessToken;
    }

    private String saveAccessToken() {
        String newAccessToken = this.getWechatAccessTokenByHttp();
        WeixinTokenBean bean = new WeixinTokenBean();
        bean.setAccessToken(newAccessToken);
        bean.setAppId(appId);
        WeixinToken.dao.addAccessToken(bean);
        return newAccessToken;
    }

    private String getWechatAccessTokenByHttp() {
        logger.info("开始http请求微信获取accessToken");

        try {
            String connect = HttpUrlConnectionUtil.connect(weixin_jssdk_acceToken_url);
            logger.info("http请求微信获取accessToken结束:" + connect);
            JSONObject parseObject = JSONObject.parseObject(connect);
            Object access_token = parseObject.get("access_token");
            Object expires_in = parseObject.get("expires_in");
            if (access_token != null && expires_in != null) {
                return access_token.toString();
            } else {
                logger.error("http请求微信获取accessToken失败");
                throw new BizException(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        } catch (Exception var5) {
            logger.error("http请求微信获取accessToken异常", var5);
            throw new BizException(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }
    }
}
