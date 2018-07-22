package net.inconnection.charge.extend.deviceManage;


import net.inconnection.charge.extend.deviceManage.device.ChargePile;

import java.util.HashMap;
import java.util.Map;

public class ChargePileManager {
    private static ChargePileManager ourInstance = new ChargePileManager();

    private Map<Long, ChargePile> chargePileMap = new HashMap<>();


    public static ChargePileManager getInstance() {
        return ourInstance;
    }

    private ChargePileManager() {
    }

    public ChargePile getChargePile(Long chargePileId){
        if (chargePileMap.containsKey(chargePileId)){
            return chargePileMap.get(chargePileId);
        }

        return null;
    }

    public boolean hasChargePile(Long chargePileId){
        return chargePileMap.containsKey(chargePileId);
    }

    public void addChargePile(Long chargePileId){
        chargePileMap.put(chargePileId, new ChargePile(chargePileId));
    }


}
