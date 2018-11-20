package net.inconnection.charge.weixin.service;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import net.inconnection.charge.weixin.bean.ChargeMoneyInfoBean;
import net.inconnection.charge.weixin.bean.resp.HnKejueResponse;
import net.inconnection.charge.weixin.bean.resp.PageResponse;
import net.inconnection.charge.weixin.code.RespCode;
import net.inconnection.charge.weixin.model.ChargeMoneyInfo;
import net.inconnection.charge.weixin.model.RechargeHistory;
import net.inconnection.charge.weixin.model.TUser;
import net.inconnection.charge.weixin.utils.EncDecUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ChargeMoneyService {
    private static Log log = Log.getLog(ChargeMoneyService.class);

    public ChargeMoneyService() {
    }

    public HnKejueResponse addRechargInfo(String openId, String deviceId, String chargeType, String walletAccount, String money, String chargeNum, String coupon, String cardNumber, String walletRealMoney, String walletGiftMoney, String companyId,String projectId) {
        try {
            if (StringUtils.isBlank(openId)) {
                log.error("openId不能为空");
                return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(deviceId)) {
                log.error("设备编号不能为空");
                return new HnKejueResponse("设备编号不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(chargeType)) {
                log.error("充值类型不能不能为空");
                return new HnKejueResponse("充值类型不能不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(money)) {
                log.error("支付金额不能为空");
                return new HnKejueResponse("支付金额不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(chargeNum)) {
                log.error("入帐金额不能为空");
                return new HnKejueResponse("入帐金额不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(coupon)) {
                log.error("入帐赠送金额不能为空");
                return new HnKejueResponse("入帐赠送金额不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(walletAccount)) {
                log.error("钱包剩余金额不能为空");
                return new HnKejueResponse("钱包剩余金额不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (chargeType.equals("CH") && StringUtils.isBlank(cardNumber)) {
                log.error("电卡号不能为空");
                return new HnKejueResponse("电卡号不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            if (StringUtils.isBlank(companyId)){
                log.error("代理商公司ID不能为空");
                return new HnKejueResponse("代理商公司ID不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }

            log.info("openId = " + openId+ ", deviceId=" + deviceId + ",chargeType=" + chargeType + ",walletAccount=" + walletAccount + ",money=" + money + ",chargeNum=" + chargeNum + ",coupon=" + coupon + ",cardNumber" + cardNumber + ",walletRealMoney=" + walletRealMoney + ",walletGiftMoney=" + walletGiftMoney);

            ChargeMoneyInfoBean chargeMoneyInfoBean = new ChargeMoneyInfoBean();
            chargeMoneyInfoBean.setOpenid(openId);
            chargeMoneyInfoBean.setDeviceid(deviceId);
            chargeMoneyInfoBean.setMoney(Integer.parseInt(money));
            chargeMoneyInfoBean.setChargetype(chargeType);
            chargeMoneyInfoBean.setCreatetime(new Date());
            this.addAndUpdate(openId, deviceId, chargeType, walletAccount, money, coupon, chargeMoneyInfoBean, cardNumber, walletRealMoney, walletGiftMoney, companyId,projectId);
        } catch (Exception var10) {
            log.error("新增充值记录失败" + var10);
            return new HnKejueResponse(false, RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        }

        return new HnKejueResponse(true, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
    }

    private void addAndUpdate(final String openId, final String deviceId, final String chargeType, final String walletAccount, final String money, final String coupon, final ChargeMoneyInfoBean chargeMoneyInfoBean, final String cardNumber, final String walletRealMoney, final String walletGiftMoney, final String companyId,final String projectId) {
        Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                String format = (new SimpleDateFormat("yy/MM/dd HH:mm:ss")).format(new Date());
                String MD5 = EncDecUtils.getMD5(openId + deviceId + format);
                chargeMoneyInfoBean.setMd5(MD5);
                int chargeMoney = Integer.parseInt(money);
                int couponMoney = Integer.parseInt(coupon);
                int totalMoney = chargeMoney + couponMoney;

                ChargeMoneyService.log.info("充值钱包总额：" + totalMoney);
                chargeMoneyInfoBean.setAmount(totalMoney);
                chargeMoneyInfoBean.setCardAmount(0);
                Integer account = Integer.valueOf(walletAccount);
                Integer total = totalMoney + account;

                Integer walletRealMoneyInt = 0;
                if (!StringUtils.isBlank(walletRealMoney)){
                    walletRealMoneyInt = Integer.parseInt(walletRealMoney);
                }else {
                    walletRealMoneyInt = account;
                }

                Integer walletGiftMoneyInt = 0;
                if (!StringUtils.isBlank(walletGiftMoney)){
                    walletGiftMoneyInt = Integer.parseInt(walletGiftMoney);
                }

                TUser.dao.updateWalletAccount(total,chargeMoney+walletRealMoneyInt,couponMoney+walletGiftMoneyInt, openId);


                ChargeMoneyInfo.dao.addChargeMoneyLog(chargeMoneyInfoBean);

                RechargeHistory.dao.addRechargeHistoryLog(openId, companyId, totalMoney , chargeMoney, couponMoney,deviceId,projectId);

                return true;
            }
        });
    }

    public HnKejueResponse queryAllByOpenId(String openId, String pageNumber, String pageSize) {
        int num = 1;
        int size = 5;
        if (StringUtils.isBlank(openId)) {
            log.error("openId不能为空");
            return new HnKejueResponse("openId不能为空", RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
        } else {
            if (StringUtils.isNotBlank(pageNumber)) {
                num = Integer.valueOf(pageNumber);
            }

            if (StringUtils.isNotBlank(pageSize)) {
                size = Integer.valueOf(pageSize);
            }

            try {
                Page<ChargeMoneyInfo> chargeMoneyList = ChargeMoneyInfo.dao.queryAllByOpenId(openId, num, size);
                List<Object> object = new ArrayList();

                ChargeMoneyInfo chargeMoney;
                for(Iterator var9 = chargeMoneyList.getList().iterator(); var9.hasNext(); object.add(chargeMoney)) {
                    chargeMoney = (ChargeMoneyInfo)var9.next();
                    String chargeType = (String)chargeMoney.get("chargeType");
                    if (chargeType.equals("CH")) {
                        chargeMoney.set("amount", chargeMoney.get("card_amount"));
                        chargeMoney.set("chargeType", "次电卡");
                    } else {
                        chargeMoney.set("chargeType", "元钱包");
                    }
                }

                PageResponse resp = new PageResponse();
                resp.setList(object);
                resp.setPageNumber(chargeMoneyList.getPageNumber());
                resp.setPageSize(chargeMoneyList.getPageSize());
                resp.setTotalCouts(chargeMoneyList.getTotalRow());
                return new HnKejueResponse(resp, RespCode.SUCCESS.getKey(), RespCode.SUCCESS.getValue());
            } catch (Exception var11) {
                log.error("分页查询充值信息失败:" + var11);
                return new HnKejueResponse(RespCode.FAILD.getKey(), RespCode.FAILD.getValue());
            }
        }
    }


    public List<Record>  getSumByCompanyId(String companyIdStr, String startDate, String endDate) {
        StringBuffer sql =new StringBuffer("SELECT ifnull(sum(real_money),0) as `sum` , COUNT(real_money) as " +
                "`count` FROM" +
                " " +
                "yc_recharge_history  where");
        sql.append(" company_id in (").append(companyIdStr).append(")");
        if(startDate!= null && StringUtils.isNotEmpty(startDate)){
            sql.append(" and recharge_time >='").append(startDate).append("'");
        }
        if(endDate!= null && StringUtils.isNotEmpty(endDate)){
            sql.append(" and recharge_time <='").append(endDate).append("'");
        }
        List<Record> records = Db.find(sql.toString());
        if(startDate == null || StringUtils.isEmpty(startDate)){
            List<Record> records2 = Db.find("select recharge_time as rechargeTime from yc_recharge_history  ORDER BY"
                    + " `recharge_time` ASC LIMIT 1");
            records.addAll(records2);
        }

//        List<ChargeMoneyInfo> chargeMoneyInfos = ChargeMoneyInfo.dao.find(sql.toString());
        return records;
    }
}

