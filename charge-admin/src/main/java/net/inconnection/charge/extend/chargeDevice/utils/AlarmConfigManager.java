package net.inconnection.charge.extend.chargeDevice.utils;

import java.util.HashMap;
import java.util.Map;

public class AlarmConfigManager {

    private static AlarmConfigManager alarmConfigManagerInstance = new AlarmConfigManager();

    private static Map<String, AlarmInfoConfig> alarmInfoConfigMap = new HashMap<>();

    private AlarmConfigManager(){}

    public static AlarmConfigManager getInstance(){
        if (alarmConfigManagerInstance == null){
            alarmConfigManagerInstance = new AlarmConfigManager();
        }

        return alarmConfigManagerInstance;
    }

    public AlarmInfoConfig getAlarmConfig(String deviceType){
        String fileName = "alarmConfig/" + deviceType + ".xml";
        if (!alarmInfoConfigMap.containsKey(fileName)){
            AlarmInfoConfig alarmInfoConfig = new AlarmInfoConfig(fileName);
            alarmInfoConfigMap.put(fileName, alarmInfoConfig);
        }

        return alarmInfoConfigMap.get(fileName);
    }



}
