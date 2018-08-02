package net.inconnection.charge.weixin.utils;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import org.json.JSONException;

import java.io.IOException;

public class TencentSMSUtils {
    static Log log = Log.getLog(TencentSMSUtils.class);
    /* 短信配置资源 */
    private static final Prop prop = PropKit.use("tencent_sms.properties");
    /* 短信配置详情 */
    private static final int APP_ID 					= prop.getInt("appId");
    private static final String APP_KEY 					= prop.get("appKey");
    private static final int TEMPLATE_ID 	= prop.getInt("templateId");
    private static final String SMS_SIGN 	= prop.get("smsSign");
    private static final String NATION_CODE 	= prop.get("nationCode");


    public static int SMSCode(String uesrMobile,String code){

        int res_code = 0;

        try {
            String[] params = {code};
            SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_KEY);
            SmsSingleSenderResult result = ssender.sendWithParam(NATION_CODE, uesrMobile,
                    TEMPLATE_ID, params, SMS_SIGN, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print(result);
            res_code = result.result;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

        return res_code;
    }
}
