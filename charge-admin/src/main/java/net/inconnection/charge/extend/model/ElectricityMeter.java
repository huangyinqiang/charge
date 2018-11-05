package net.inconnection.charge.extend.model;

import net.inconnection.charge.extend.model.base.BaseElectricityMeter;

public class ElectricityMeter extends BaseElectricityMeter<ElectricityMeter> {
	public static final ElectricityMeter dao = new ElectricityMeter().dao();
    public static final ElectricityMeter me = new ElectricityMeter();


}
