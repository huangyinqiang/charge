package net.inconnection.charge.weixin.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TokenCheckUtils {
          /**
           * 验证接口配置信息
           * 加密/校验流程如下：
          1. 将token、timestamp、nonce三个参数进行字典序排序
          2. 将三个参数字符串拼接成一个字符串进行sha1加密
          3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
           */

          public static String tokenCheck(String signature,String timestamp,String nonce,String echostr){
                 String token = "F0932398023";
                 List<String> list = new ArrayList<>();
                 list.add(timestamp);
                 list.add(token);
                 list.add(nonce);

                 Collections.sort(list);//1.字典排序

                 String str = DigestUtils.sha1Hex(list.get(0)+list.get(1)+list.get(2));//2.sha1加密
                 if(signature.equals(str)){//3.判断是否微信信息  此处判断echostr!=null是为了进行下面的操作不影响此处原本的效果
                         return echostr;
                         //    也就是说 如果echostr!=null，那么此次的请求是在验证是否成为开发者的；否则就是其他的微信请求，不用跳出程序
                     }
                 return null;

          }
}
