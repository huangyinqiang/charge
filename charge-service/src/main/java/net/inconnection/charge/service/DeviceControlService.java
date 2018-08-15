package net.inconnection.charge.service;
import java.util.Map;
import java.util.Vector;

public interface DeviceControlService {

   String sayHello(String name);

   Boolean requestPermissionOnLine(Long gwId, Long timeout);

   Boolean requestShutDownAllSockets(Long gwId, Long timeout);

   Map requestShutDownChargeSocket(Long gwId, Vector<Long> socketIds, Long timeout);

   Integer requestShutDownChargeSocket(Long gwId, Long socketId, Long timeout);

   Map requestStartCharge(Long gwId, Map<Long, Integer> socketIdChargeTimeMap, Long timeout);

   Integer requestStartCharge(Long gwId, Long socketId, Integer chargeTime, Long timeout);




}
