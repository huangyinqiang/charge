package net.inconnection.charge.extend.model;

import net.inconnection.charge.extend.model.base.BaseChargeHistory;

import java.util.Date;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ChargeHistory extends BaseChargeHistory<ChargeHistory> {
	public static final ChargeHistory dao = new ChargeHistory().dao();

    public List<ChargeHistory> queryChargeHistory(long companyId, Date startDate, Date endDate) {
        return this.find("select * from yc_charge_history where company_id = ? " +
                "and createDate > ? and createDate < ?",companyId,startDate,endDate);
    }
    public List<ChargeHistory> queryChargeHistory(int chargeStatus,Date startDate, Date endDate) {
        return this.find("select * from yc_charge_history where chargeStatus = ? " +
                "and createDate > ? and createDate < ?",chargeStatus,startDate,endDate);
    }
}
