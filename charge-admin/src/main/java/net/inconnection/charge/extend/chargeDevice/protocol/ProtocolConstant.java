package net.inconnection.charge.extend.chargeDevice.protocol;

public class ProtocolConstant {
    public static final String MQTT_BROKER_IP = "tcp://139.199.78.128:1883";

    public static final int MSG_UPDATE_WEB_PORT =   7777;       //update的web管理端口
    public static final int MSG_UPDATE_PROCESS_PORT =   8888;       //update的数据管理端口

    public static final String MQTT_TOPIC_SEPARATOR = "/";

    public static final String MQTT_TOPIC_TO_POINT = "P";
    public static final String MQTT_TOPIC_TO_CENTER = "C";

    public static final String MQTT_TOPIC_INDUSTRY_CHARGE = "CHARGE";

    public static final String MQTT_TOPIC_CUR_VERSION = "1";

    public static final String MQTT_TOPIC_GW_INIT = "GW_INIT";

    public static final String TOPIC_DATA = "data";
    public static final String TOPIC_IMAGE = "image";
    public static final String TOPIC_RESPONSE = "response";
    public static final String TOPIC_REQUEST = "request";
    public static final String TOPIC_CONTROL = "control";
    public static final String TOPIC_UPDATE = "update";
    public static final String TOPIC_NOTIFY = "notify";
    public static final String TOPIC_CONFIG = "config";
    public static final String TOPIC_CONFIRM = "confirm";


    public static final String MSG_FACET_SEPARATOR = "\r";              //数据段分隔符
    public static final String MSG_FACET_SEPARATOR_INSIDE = "\r\n";     //内部使用的数据段分隔符
    public static final String MSG_SEGMENT_SEPARATOR = ";";             //字段分隔符
    public static final String MSG_COMPONENT_SEPARATOR = ":";           //组件分隔符
    public static final String MSG_REPEAT_SEPARATOR = ",";              //重复分隔符


    public static final String MSG_STATIONNAME = "POS";         //电站名称
    public static final String MSG_SERIALNUMBER                             = "SEQ";         //信息序号／信息唯一标示号
    public static final String MSG_TIME                                     = "TIME";        //时间
    public static final String MSG_GWID                                     = "GWID";        //网关ID
    public static final String MSG_GW_STATUS                                = "STATUS";        //网关硬件状态

    public static final String MSG_CHARGEVOLTAGE = "CV";//充电电压


    public static final String MSG_REQUEST                                  = "REQUEST";     //REQUEST主题，请求
    public static final String MSG_RESPONSE                                 = "RESPONSE";    //响应
    public static final String MSG_COMMAND                                  = "COMMAND";     //控制
    public static final String MSG_CONFIG                                   = "CONFIG";      //GW入网请求


    public static final String MSG_DEVICESN = "DEVICESN";      //设备名称
    public static final String MSG_PVSN                                     = "PVSN";      //采集设备序列号
    public static final String MSG_ALARM                                    = "WARN";        //设备告警标志
    public static final String MSG_STATUS                                   = "STATUS";      //采集设备故障标志

    public static final String MSG_MAINTYPE_CHARGESOCKET = "1";//主类型 1， 代表充电插座

    public static final String MSG_INUSE = "USE";//充电桩占用情况
    public static final String MSG_STARTPOWER = "SP";//初始充电功率
    public static final String MSG_CHARGEINTENSITY = "CI";//充电电流
    public static final String MSG_CHARGETIME = "CT";//充电时长
    public static final String MSG_CHARGESTATE = "CS";//充电状态



    //2017.10.19况发志新增
    public static final String MSG_IMAGE_DEVICE                             =   "DEVICE";   //发送图像的设备名
    public static final String MSG_IMAGE_FRAME                              =   "FRAME";    //图像分割后的数据数量
    public static final String MSG_IMAGE_INDEX                              =   "INDEX";    //数据在图像的位置
    public static final String MSG_IMAGE_IMAGE                              =   "IMAGE";    //带有图像数据的十六进制码


    public static final String MSG_REQUEST_CODE_REVERSE =   "0";        //0:保留
    public static final String MSG_REQUEST_CODE_PERMISSIONONLINE =   "1";        //1:请求允许上线
    public static final String MSG_REQUEST_CODE_SHUTDOWNALLSOCKETS =   "2";        //2:请求切断所有插座
    public static final String MSG_REQUEST_CODE_SHUTDOWNSOCKET =   "3";        //3:请求切断充电插座电源
    public static final String MSG_REQUEST_CODE_TESTSOCKETPOWER =   "4";        //4:请求测试插座充电功率
    public static final String MSG_REQUEST_CODE_SOCKETSTARTCHARGE =   "5";        //5:插座开始充电


    public static final String MSG_RESPONCE_CODE_REVERSE                    =   "0";        //0:保留
    public static final String MSG_RESPONCE_CODE_PERMISSIONONLINE             =   "1";        //1:响应上线
    public static final String MSG_RESPONCE_CODE_SHUTDOWNALLSOCKETS           =   "2";        //2:响应切断所有插座
    public static final String MSG_RESPONCE_CODE_SHUTDOWNSOCKET              =   "3";        //3:响应切断充电插座
    public static final String MSG_RESPONCE_CODE_TESTSOCKETPOWER         =   "4";        //4:响应测试充电功率
    public static final String MSG_RESPONCE_CODE_SOCKETSTARTCHARGE         =   "5";        //5:响应插座开始充电

    public static final String MSG_RESPONCE_RESULT = "RESULT";



    //2017.11.14况发志新增
    public static final String MSG_UPDATE_UPDATE                           =   "UPDATE";   //升级文件的版本号
    public static final String MSG_UPDATE_OFFSET                           =   "OFFSET";   //本次发送的数据在总的升级文件中的偏移量,单位为字节
    public static final String MSG_UPDATE_LEN                              =   "LEN";      //本次发送的数据长度,单位为字节
    public static final String MSG_UPDATE_LEN_ALL                          =   "LEN_ALL";  //升级文件的总的长度
    public static final String MSG_UPDATE_CRC                              =   "CRC";      //校验,一个字节,对本次所发数据按字节异或操作所得到的结果
    public static final String MSG_UPDATE_STATUS                           =   "STATUS";   //升级状态,可选值为 0、1、3、4、5、10,0 代表该段数据校验有误;1 代表校验成功;3 代表子站网关处于电池供电,升级有可能会中断;4 代表内部 flash 损坏,不可升级;5代表已最新无需升级;10升级成功


}





























