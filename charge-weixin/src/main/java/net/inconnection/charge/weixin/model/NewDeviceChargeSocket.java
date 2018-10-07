package net.inconnection.charge.weixin.model;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;
import java.util.List;

public class NewDeviceChargeSocket extends Model<NewDeviceChargeSocket> {
    private static final long serialVersionUID = 3097666259122699423L;
    private static final Log log = Log.getLog(NewDeviceChargeSocket.class);
    public static final NewDeviceChargeSocket dao = new NewDeviceChargeSocket();

    public NewDeviceChargeSocket() {
    }

    public List<NewDeviceChargeSocket> queryChargeSocketStatus(Long deviceId) {
        log.info("根据充电桩ID查询充电插座信息:" + deviceId);
        Date newTime = new Date();
        newTime.setTime(newTime.getTime() - 10*60*1000);
//        List<NewDeviceChargeSocket> resp = dao.find("select charge_socket_sn,is_used,charge_state from yc_charge_socket where charge_pile_id=? and update_time>?", new Object[]{deviceId, newTime});
        List<NewDeviceChargeSocket> resp = dao.find("select charge_socket_sn,is_used,charge_state from yc_charge_socket where charge_pile_id=? ", new Object[]{deviceId});

        log.info("根据充电桩ID查询充电插座信息结果:" + resp);
        return resp;
    }

    public List<NewDeviceChargeSocket> queryChargeSocket(Long deviceId) {
        log.info("根据充电桩ID查询充电插座信息:" + deviceId);
        List<NewDeviceChargeSocket> resp = dao.find("select charge_socket_sn,is_used,charge_state from yc_charge_socket where charge_pile_id=? ", new Object[]{deviceId});
        log.info("根据充电桩ID查询充电插座信息结果:" + resp);
        return resp;
    }

}

