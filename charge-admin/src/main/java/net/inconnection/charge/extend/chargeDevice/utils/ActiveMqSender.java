package net.inconnection.charge.extend.chargeDevice.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class ActiveMqSender {

    private static Logger _log = LoggerFactory.getLogger(ActiveMqSender.class);


    private static ConnectionFactory connectionFactory ;
	private static Connection connection;
	private static Session session;

    private static Queue push2WebQueue ;
    private static MessageProducer push2WebProducer;

	private static ActiveMqSender activeMqSenderInstance = new ActiveMqSender();

	private ActiveMqSender(){
		try {
			connectionFactory = new ActiveMQConnectionFactory(ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}catch (Exception e) {
			_log.error("ActiveMqSender:连接出错!",e);
			e.printStackTrace();
		}

	}

	public static ActiveMqSender getInstance(){
		if (activeMqSenderInstance == null) {
			activeMqSenderInstance = new ActiveMqSender();
		}
		return activeMqSenderInstance;
	}

	public void pushToActiveMQ(String msg, String callBackQueueName){
		_log.info("ActiveMqSender.pushToActiveMQ : " + msg);
		try {
			push2WebQueue = session.createQueue(callBackQueueName);
			push2WebProducer = session.createProducer(push2WebQueue);
			push2WebProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			TextMessage textMessage = session.createTextMessage(msg);
			push2WebProducer.send(textMessage);

		}catch (Exception e) {

			e.printStackTrace();
		}
	}

}
