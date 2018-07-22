package net.inconnection.charge.extend.chargeDevice.listener;


import net.inconnection.charge.extend.chargeDevice.deviceManage.MsgProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MqttMsgProcesserListener implements MessageListener {

    private static Logger _log = LoggerFactory.getLogger(MqttMsgProcesserListener.class);

//    @Autowired
//    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    public void onMessage(final Message message) {
//        threadPoolTaskExecutor.setQueueCapacity(200);
//        //线程池维护线程的最少数量
//        threadPoolTaskExecutor.setCorePoolSize(5);
//        //线程池维护线程的最大数量
//        threadPoolTaskExecutor.setMaxPoolSize(1000);
//        //线程池维护线程所允许的空闲时间
//        threadPoolTaskExecutor.setKeepAliveSeconds(30000);
//        threadPoolTaskExecutor.initialize();
//        // 使用线程池多线程处理
//        threadPoolTaskExecutor.execute(new Runnable() {
//            public void run() {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        MsgProcessor.getInstance().processIncomeMsg(textMessage.getText());
                    } catch (Exception e){
                        _log.error("消息处理异常:",e);
                    }
                }
//            }
//        });
    }
}
