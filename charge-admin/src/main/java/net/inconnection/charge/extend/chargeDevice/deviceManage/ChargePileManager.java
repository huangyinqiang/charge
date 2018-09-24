package net.inconnection.charge.extend.chargeDevice.deviceManage;


import net.inconnection.charge.extend.chargeDevice.deviceInterface.Device;
import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargePileDevice;
import net.inconnection.charge.extend.chargeDevice.deviceManage.device.ChargeSocketComponent;
import net.inconnection.charge.extend.model.ChargePile;
import net.inconnection.charge.extend.model.ChargeSocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargePileManager {
    private static ChargePileManager ourInstance = new ChargePileManager();

    private Map<Long, ChargePileDevice> chargePileMap = new HashMap<>();


    public static ChargePileManager getInstance() {
        return ourInstance;
    }

    private ChargePileManager() {

        List<ChargePile> chargePiles = ChargePile.dao.find("select * from yc_charge_pile "  );

        for (ChargePile chargePile : chargePiles){
            Long chargePileId = chargePile.getId();
            ChargePileDevice chargePileDevice = new ChargePileDevice(chargePileId);
            chargePileDevice.setName(chargePile.getName());
//            chargePileDevice.setBatVol(chargePile.getBatVol());
//            chargePileDevice.setControllerVol(chargePile.getControllerVol());
            if (chargePile.getIsOnline() != null){
                chargePileDevice.setOnline(chargePile.getIsOnline());
            }
//            chargePileDevice.setVoltage(chargePile.getTotalVoltage());
//            chargePileDevice.setPower(chargePile.getPowerTotal());

            List<ChargeSocket> chargeSockets= ChargeSocket.dao.find("select * from yc_charge_socket where charge_pile_id=" + chargePileId);
            Map<Long, ChargeSocketComponent> chargeSocketComponentMap = new HashMap<>();
            for (ChargeSocket chargeSocket: chargeSockets){
                ChargeSocketComponent device = new ChargeSocketComponent(chargePileId, (Long.valueOf(chargeSocket.getChargeSocketSn())));


                chargeSocketComponentMap.put((Long.valueOf(chargeSocket.getChargeSocketSn())),device);
            }

            chargePileDevice.setChargeSocketMap(chargeSocketComponentMap);

            chargePileMap.put(chargePileId, chargePileDevice);
        }

        System.out.println("test all ChargePile : " + chargePiles);

    }

    public ChargePileDevice getChargePile(Long chargePileId){
        if (chargePileMap.containsKey(chargePileId)){
            return chargePileMap.get(chargePileId);
        }

        return null;
    }

    public boolean hasChargePile(Long chargePileId){
        return chargePileMap.containsKey(chargePileId);
    }

    public void addChargePile(Long chargePileId){
        chargePileMap.put(chargePileId, new ChargePileDevice(chargePileId));
        ChargePile chargePileDo = new ChargePile();
        chargePileDo.setIsOnline(false);
        chargePileDo.setId(chargePileId).save();
    }


}
