package net.inconnection.charge.extend.model;

import net.inconnection.charge.extend.model.base.BaseElectricityMeterHistory;


public class ElectricityMeterHistory extends BaseElectricityMeterHistory<ElectricityMeterHistory> {
	public static final ElectricityMeterHistory dao = new ElectricityMeterHistory().dao();
    public static final ElectricityMeterHistory me = new ElectricityMeterHistory();
}
