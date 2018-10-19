package net.inconnection.charge.extend.chargeDevice.protocol;

public class ProtocolConstant {
//    public static final String MQTT_BROKER_IP = "tcp://120.227.8.223:1883";//云创服务器
    public static final String MQTT_BROKER_IP = "tcp://charge.inconnection.net:1883";//云创服务器

//    public static final String MQTT_BROKER_IP = "tcp://139.199.78.128:1883";
//    public static final String MQTT_BROKER_IP = "tcp://127.0.0.1:1883";

//    public static final String MQTT_BROKER_IP = "tcp://61.150.60.254:1883";

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
    public static final String TOPIC_WILL = "will";
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

    public static final String MSG_CHARGEVOLTAGE = "VOL_AC";//充电桩充电电压
    public static final String MSG_CHARGEPOWER = "POW";//充电桩充电功率

    public static final String MSG_BAT_VOL = "BAT";//充电桩后备电池电压
    public static final String MSG_CONTROLLER_VOL = "VOL";//充电桩后控制器供电电压


    public static final String MSG_REQUEST                                  = "REQUEST";     //REQUEST主题，请求
    public static final String MSG_RESPONSE                                 = "RESPONSE";    //响应
    public static final String MSG_COMMAND                                  = "COMMAND";     //控制
    public static final String MSG_CONFIG                                   = "CONFIG";      //GW入网请求


    public static final String MSG_DEVICESN = "DEVICESN";      //设备名称
    public static final String MSG_PVSN                                     = "PVSN";      //采集设备序列号
    public static final String MSG_ALARM                                    = "WARN";        //设备告警标志
    public static final String MSG_STATUS                                   = "STATUS";      //采集设备故障标志

    public static final String MSG_MAINTYPE_CHARGESOCKET = "1";//主类型 1， 代表充电插座

    public static final String MSG_INUSE = "USE";//充电插座占用情况
    public static final String MSG_STARTPOWER = "SP";//初始充电功率
    public static final String MSG_CHARGEINTENSITY = "CI";//充电电流
    public static final String MSG_CHARGETIME = "CT";//充电时长
    public static final String MSG_CHARGESTATE = "CS";//充电状态

    public static final String MSG_CHARGETIME_SET = "TIMESET";//充电状态



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
    public static final String MSG_REQUEST_CODE_DELETEIMAGE =   "6";        //6:删除照片
    public static final String MSG_REQUEST_CODE_SHOWIMAGE =   "7";        //7:展示照片
    public static final String MSG_REQUEST_CODE_SET_TICK =   "8";        //8:设置传输间隔
    public static final String MSG_REQUEST_CODE_CONFIRM_ONLINE =   "9";        //9:在线确认
    public static final String MSG_REQUEST_CODE_POSITION =   "10";        //10:传输基站位置
    public static final String MSG_REQUEST_CODE_TRANS_FILE =   "11";        //11:传输文件
    public static final String MSG_REQUEST_CODE_SET_MAXPOW =   "12";        //12:设置最大功率
    public static final String MSG_REQUEST_CODE_SET_BORAD_MAXPOW =   "13";        //13:设置强电板总共最大功率
    public static final String MSG_REQUEST_CODE_SET_TIME =   "14";        //14:请求对时
    public static final String MSG_REQUEST_CODE_SET_FINISHI =   "15";        //15:设置充电截止判断电流
    public static final String MSG_REQUEST_CODE_SET_LITTLEI_TIME =   "16";        //16:设置涓流时间


    public static final String MSG_RESPONCE_CODE_REVERSE                    =   "0";        //0:保留
    public static final String MSG_RESPONCE_CODE_PERMISSIONONLINE             =   "1";        //1:响应上线
    public static final String MSG_RESPONCE_CODE_SHUTDOWNALLSOCKETS           =   "2";        //2:响应切断所有插座
    public static final String MSG_RESPONCE_CODE_SHUTDOWNSOCKET              =   "3";        //3:响应切断充电插座
    public static final String MSG_RESPONCE_CODE_TESTSOCKETPOWER         =   "4";        //4:响应测试充电功率
    public static final String MSG_RESPONCE_CODE_SOCKETSTARTCHARGE         =   "5";        //5:响应插座开始充电
    public static final String MSG_RESPONCE_CODE_DELETEIMAGE         =   "6";        //6:响应删除照片
    public static final String MSG_RESPONCE_CODE_SHOWIMAGE        =   "7";        //7:响应展示照片
    public static final String MSG_RESPONCE_CODE_SET_TICK =   "8";        //8:响应设置传输间隔
    public static final String MSG_RESPONCE_CODE_CONFIRM_ONLINE =   "9";        //9:响应在线确认
    public static final String MSG_RESPONCE_CODE_POSITION =   "10";        //10:响应传输基站位置
    public static final String MSG_RESPONCE_CODE_TRANS_FILE =   "11";        //11:响应传输文件
    public static final String MSG_RESPONCE_CODE_SET_MAXPOW =   "12";        //12:响应设置最大功率
    public static final String MSG_RESPONCE_CODE_SET_BORAD_MAXPOW =   "13";        //13:响应设置强电板总共最大功率
    public static final String MSG_RESPONCE_CODE_SET_TIME =   "14";        //14:响应请求对时
    public static final String MSG_RESPONCE_CODE_SET_FINISHI =   "15";        //15:响应设置充电截止判断电流
    public static final String MSG_RESPONCE_CODE_SET_LITTLE_ITIME =   "16";        //16:响应设置涓流时间

    public static final String MSG_RESPONCE_RESULT = "RESULT";



    public static final String MSG_UPDATE_UPDATE                           =   "UPDATE";   //升级文件的版本号
    public static final String MSG_UPDATE_OFFSET                           =   "OFFSET";   //本次发送的数据在总的升级文件中的偏移量,单位为字节
    public static final String MSG_UPDATE_LEN                              =   "LEN";      //本次发送的数据长度,单位为字节
    public static final String MSG_UPDATE_LEN_ALL                          =   "LEN_ALL";  //升级文件的总的长度
    public static final String MSG_UPDATE_CRC                              =   "CRC";      //校验,一个字节,对本次所发数据按字节异或操作所得到的结果
    public static final String MSG_UPDATE_STATUS                           =   "STATUS";   //升级状态,可选值为 0、1、3、4、5、10,0 代表该段数据校验有误;1 代表校验成功;3 代表子站网关处于电池供电,升级有可能会中断;4 代表内部 flash 损坏,不可升级;5代表已最新无需升级;10升级成功

    public static final String MSG_IMAGE_NAME                           =   "IMAGE";   //传输的图片的名称
    public static final String MSG_IMAGE_TIMELAST                           =   "TIMELAST";   //图片的单次显示持续时间

    public static final String MSG_IMAGE_XPOINT                           =   "X";   //图片的显示坐标X
    public static final String MSG_IMAGE_YPOINT                           =   "Y";   //图片的显示坐标Y
    public static final String MSG_IMAGE_STARTTIME                           =   "START";   //图片的显示时间段的开始时间
    public static final String MSG_IMAGE_ENDTIME                           =   "END";   //图片的显示时间段的结束时间
    public static final String MSG_IMAGE_DAYLAST                           =   "DAYLAST";   //图片的显示时间段的持续天数

    public static final String MSG_REQUEST_TICK = "TICK";
    public static final String MSG_REQUEST_MAX_PORTPOW = "PORTPOW";
    public static final String MSG_REQUEST_MAX_BOARDPOW = "BOARDPOW";
    public static final String MSG_REQUEST_FINISH_I = "FINISHI";
    public static final String MSG_REQUEST_JLTIME = "JLTIME";


}





























