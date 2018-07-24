package net.inconnection.charge.extend.chargeDevice;


import net.inconnection.charge.extend.chargeDevice.protocol.MqttMsgSender;
import net.inconnection.charge.extend.chargeDevice.utils.SEQGeneration;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.MSG_FACET_SEPARATOR_INSIDE;
import static net.inconnection.charge.extend.chargeDevice.protocol.ProtocolConstant.TOPIC_DATA;

/**
 * Created by root on 17-11-10.
 */
public class PerformenceTester {
    public static void main(String[] args) {

        MqttMsgSender mqttMsgSender;
         {
            mqttMsgSender =  MqttMsgSender.getInstance();

            //while (true)
            {
                for (int index=0; index<1; index++) {
                    final String industry = "CHARGE";
                    final String version = "1";
                    final String gwId = "1234567890";

                    String topic = "C/" + industry + "/" + version +"/" + gwId + "/" + TOPIC_DATA;


                    Date date = new Date();
                    String timeStr = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
                    String seq = String.valueOf(SEQGeneration.getInstance().getSEQ());//sequenceNum生成

//                    String msg = "POS:" + powerStation + ";SEQ:" + seq + ";TIME:" + timeStr + MSG_FACET_TERMINATOR_INSIDE +
////                            "DEVICE:INV_KS17;DEVICETYPE:KSTAR_KSG10-60K;41:6577;43:6998;45:6858;42:464;44:428;46:265;20:3057;21:3000;22:1821;61:3476;62:3473;31:3975;32:3945;33:3935;37:5000;38:5000;39:5000;25:1150;26:1187;27:1200;5:7808;2:38644;4:23642;1:12;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
////                            "DEVICE:INV_KS18;DEVICETYPE:KSTAR_KSG10-60K;41:7262;43:6198;45:6989;42:451;44:442;46:307;20:3281;21:2740;22:2147;61:3609;62:3632;31:3957;32:3962;33:3937;37:5000;38:5000;39:5000;25:1212;26:1187;27:1212;5:7990;2:60861;4:19288;1:13;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
////                            "DEVICE:INV_KS19;DEVICETYPE:KSTAR_KSG10-60K;41:6752;43:7045;45:7289;42:309;44:276;46:292;20:2091;21:1947;22:2128;61:3619;62:3630;31:3945;32:3952;33:3920;37:5000;38:5000;39:5000;25:987;26:950;27:987;5:6157;2:39499;4:17142;1:10;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
////                            "DEVICE:INV_KS20;DEVICETYPE:KSTAR_KSG10-60K;41:7273;43:7025;45:6445;42:513;44:510;46:307;20:3734;21:3586;22:1979;61:3603;62:3633;31:3982;32:3950;33:3930;37:5000;38:5000;39:5000;25:1325;26:1350;27:1350;5:8986;2:41936;4:23973;1:15;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
////                            "DEVICE:INV_KS10;DEVICETYPE:KSTAR_KSG10-60K;41:7147;43:7052;45:7343;42:513;44:494;46:520;20:3666;21:3485;22:3821;61:3646;62:3661;31:3867;32:3862;33:3867;37:4999;38:4999;39:5000;25:1587;26:1600;27:1600;5:10556;2:61661;4:25955;1:16;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS11;DEVICETYPE:KSTAR_KSG10-60K;41:7065;43:7123;45:7208;42:501;44:519;46:467;20:3540;21:3699;22:3370;61:3560;62:3571;31:3875;32:3882;33:3867;37:5000;38:5000;39:5001;25:1562;26:1537;27:1562;5:10258;2:61331;4:25921;1:17;7:0;6:0;WARN:113,115,112,111" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS12;DEVICETYPE:KSTAR_KSG10-60K;41:6958;43:7149;45:7011;42:485;44:450;46:509;20:3377;21:3221;22:3570;61:3569;62:3575;31:2162;32:2162;33:2163;37:5000;38:5000;39:5000;25:1800;26:1787;27:1812;5:9730;2:54181;4:25203;1:16;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS13;DEVICETYPE:KSTAR_KSG10-60K;41:7138;43:6742;45:6992;42:461;44:485;46:460;20:3292;21:3274;22:3217;61:3527;62:3554;31:3882;32:3870;33:3835;37:5000;38:5001;39:5001;25:1475;26:1487;27:1487;5:9774;2:62339;4:26023;1:17;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS21;DEVICETYPE:KSTAR_KSG10-60K;41:7196;43:7011;45:7320;42:409;44:421;46:401;20:2944;21:2955;22:2938;61:3607;62:3624;31:3967;32:3970;33:3957;37:5000;38:5000;39:5000;25:1337;26:1262;27:1312;5:8641;2:55946;4:25381;1:14;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS22;DEVICETYPE:KSTAR_KSG10-60K;41:6808;43:7189;45:6833;42:435;44:413;46:421;20:2962;21:2970;22:2880;61:3572;62:3599;31:3972;32:3957;33:3905;37:5000;38:5000;39:5000;25:1325;26:1312;27:1287;5:8747;2:55998;4:25386;1:14;7:0;6:0;WARN:113,115,112,111" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS23;DEVICETYPE:KSTAR_KSG10-60K;41:7038;43:6915;45:7229;42:425;44:427;46:437;20:2997;21:2958;22:3164;61:3583;62:3594;31:3950;32:3950;33:3980;37:5000;38:5001;39:5000;25:1287;26:1325;27:1325;5:8789;2:58501;4:25637;1:14;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS24;DEVICETYPE:KSTAR_KSG10-60K;41:7119;43:7107;45:6981;42:401;44:374;46:424;20:2855;21:2663;22:2960;61:3537;62:3538;31:3960;32:3960;33:3952;37:5000;38:5000;39:5000;25:1287;26:1262;27:1287;5:8468;2:54540;4:25239;1:14;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS14;DEVICETYPE:KSTAR_KSG10-60K;41:7067;43:6955;45:6835;42:475;44:452;46:432;20:3362;21:3146;22:2955;61:3507;62:3515;31:3870;32:3882;33:3882;37:5000;38:5000;39:5000;25:1425;26:1425;27:1412;5:9341;2:61608;4:25950;1:16;7:0;6:0;WARN:113,115,112,111" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS15;DEVICETYPE:KSTAR_KSG10-60K;41:7066;43:7220;45:6652;42:431;44:441;46:333;20:3049;21:3186;22:2219;61:3602;62:3594;31:3875;32:3872;33:3887;37:5000;38:5000;39:5000;25:1262;26:1275;27:1275;5:8372;2:35735;4:23350;1:15;7:0;6:0;WARN:" + MSG_FACET_TERMINATOR_INSIDE +
//                            "DEVICE:INV_KS16;DEVICETYPE:KSTAR_KSG10-60K;41:6885;43:6536;45:7121;42:422;44:451;46:449;20:2906;21:2949;22:3199;61:3557;62:3555;31:3882;32:3882;33:3840;37:5000;38:5000;39:5000;25:1412;26:1400;27:1400;5:9045;2:47829;4:24565;1:17;7:0;6:0;WARN:113,115,112,111";

//
                    String msg = "GWID:" + gwId + ";SEQ:" + seq + ";TIME:" + timeStr + "VOLTAGE:220;POWER:12000" + ";STATUS:1" + MSG_FACET_SEPARATOR_INSIDE +
                            "DEVICESN:01;MAINTYPE:1;USE:1;SP:100;CI:234;CT:332;CS:1;WARN:1,2" + MSG_FACET_SEPARATOR_INSIDE +
                            "DEVICESN:02;MAINTYPE:1; USE:1;SP:100;CI:234;CT:332;CS:1";

                    try {

                        mqttMsgSender.Send(topic,msg);
                        System.out.println("PerformenceTester.main: " + timeStr);


                    }
                    catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {

                        Thread.sleep(10000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }


                }

                try {

                    Thread.sleep(29000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }


            }

        }






    }
}
