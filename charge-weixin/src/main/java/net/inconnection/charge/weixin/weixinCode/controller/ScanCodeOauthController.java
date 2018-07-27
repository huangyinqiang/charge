package net.inconnection.charge.weixin.weixinCode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;
import net.inconnection.charge.weixin.bean.TuserBean;
import net.inconnection.charge.weixin.model.TUser;
import net.inconnection.charge.weixin.utils.HttpUrlConnectionUtil;
import net.inconnection.charge.weixin.utils.WeiXinUtils;
import net.inconnection.charge.weixin.weixinCode.service.WechatAccessTokenService;
import org.apache.commons.lang.StringUtils;

public class ScanCodeOauthController extends ApiController
{
    private static final String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    private static final Log log = Log.getLog(ScanCodeOauthController.class);

    private static final WechatAccessTokenService wechatAccessTokenService = new WechatAccessTokenService();

    public ApiConfig getApiConfig()
    {
        ApiConfig ac = new ApiConfig();

        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));

        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", Boolean.valueOf(false)).booleanValue());
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

    public void index() throws Exception {
        int subscribe = 0;

        String code = getPara("code");
        if (code != null) {
            String appId = ApiConfigKit.getApiConfig().getAppId();
            String secret = ApiConfigKit.getApiConfig().getAppSecret();

            SnsAccessToken snsAccessToken = SnsAccessTokenApi.getSnsAccessToken(appId, secret, code);

            String token = snsAccessToken.getAccessToken();
            String openId = snsAccessToken.getOpenid();

            ApiResult apiResult = SnsApi.getUserInfo(token, openId);

            log.warn("获取用户信息WeiXinOauthController.index:" + apiResult.getJson());
            if (apiResult.isSucceed()) {
                JSONObject jsonObject = JSON.parseObject(apiResult.getJson());
                String nickName = jsonObject.getString("nickname");

                int sex = jsonObject.getIntValue("sex");
                String city = jsonObject.getString("city");
                String province = jsonObject.getString("province");
                String country = jsonObject.getString("country");
                String headimgurl = jsonObject.getString("headimgurl");
                String unionid = jsonObject.getString("unionid");

                ApiResult userInfo = UserApi.getUserInfo(openId);
                log.warn(JsonKit.toJson("is subsribe>>" + userInfo));
                if (userInfo.isSucceed()) {
                    String userStr = userInfo.toString();
                    subscribe = JSON.parseObject(userStr).getIntValue("subscribe");
                }
                TuserBean tuser = new TuserBean();
                tuser.setOpenid(openId);
                tuser.setNickname(WeiXinUtils.filterWeixinEmoji(nickName));
                tuser.setUnionid(unionid);
                tuser.setHeadimgurl(headimgurl);
                tuser.setCountry(country);
                tuser.setCity(city);
                tuser.setProvince(province);
                tuser.setSex(Integer.valueOf(sex));

                TUser.dao.saveOrUpdateUser(tuser);
            }

            setSessionAttr("openId", openId);
            log.info("获取用户信息成功" + openId);

            subscribe = getSubscribe(openId);
            if (subscribe == 0) {
                log.info("未关注公众号，调转到二维码页面");
                redirect("/scan/qrImage");
            }
            else {
                String url = "/charging";
                log.info("授权成功，跳转到我要充电页面。" + url);
                redirect(url);
            }
        } else {
            renderText("code is null");
        }
    }

    private int getSubscribe(String openID)
            throws Exception
    {
        log.info("判断用户openId为：" + openID + ",的用户是否关注了公众号");
        if (StringUtils.isBlank(openID)) {
            log.error("openId不能为空");
            return 0;
        }
        String accessToken = wechatAccessTokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN".replace("ACCESS_TOKEN", accessToken).replace("OPENID", openID);
        String invokeServer = HttpUrlConnectionUtil.invokeServer(url);
        JSONObject jsonObject = JSONObject.parseObject(invokeServer);
        Object subscribe = jsonObject.get("subscribe");
        if (subscribe != null) {
            return Integer.parseInt(subscribe.toString());
        }
        if (invokeServer.contains("access_token is invalid")) {
            log.info("二次获取accessToken");
            String accessToken2 = wechatAccessTokenService.updateAccessToken();
            String requestUrl2 = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN".replace("ACCESS_TOKEN", accessToken2).replace("OPENID", openID);
            String connect2 = HttpUrlConnectionUtil.invokeServer(requestUrl2);
            log.info("http请求微信获取ticket结束:" + connect2);
            JSONObject jsonObject2 = JSONObject.parseObject(connect2);
            Object subscribe2 = jsonObject2.get("subscribe");
            if (subscribe2 != null) {
                return Integer.parseInt(subscribe2.toString());
            }
        }
        return 0;
    }
}