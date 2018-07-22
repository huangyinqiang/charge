package net.inconnection.charge.extend.chargeDevice.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class DataSourceUtils {

    private static Logger _log = LoggerFactory.getLogger(DataSourceUtils.class);


    private static ConnectionFactory connectionFactory ;
	private static Connection connection;
	private static Session session;

    private static Queue push2WebQueue ;
    private static MessageProducer push2WebProducer;

	private static DataSourceUtils dataSourceUtilsInstance  = new DataSourceUtils();

	private DataSourceUtils(){
		try {
			connectionFactory = new ActiveMQConnectionFactory(ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ);
			connection = connectionFactory.createConnection();

			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//			push2WebQueue = session.createQueue("insert-all-database");
//			push2WebProducer = session.createProducer(push2WebQueue);
//			push2WebProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


		}catch (Exception e) {
			_log.error("DataSourceUtils:连接出错!",e);
			e.printStackTrace();
		}

	}

	public static DataSourceUtils getInstance(){
		if (dataSourceUtilsInstance == null) {
			dataSourceUtilsInstance = new DataSourceUtils();
		}

		return dataSourceUtilsInstance;
	}

	public void pushToActiveMQ(String msg, String callBackQueueName){
		_log.info("DataSourceUtils.pushToActiveMQ : " + msg);
		try {
//			connectionFactory = new ActiveMQConnectionFactory(ActiveMQConstant.TCP_LOCALHOST_ACTIVEMQ);
//			connection = connectionFactory.createConnection();
//
//			connection.start();
//
//			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			push2WebQueue = session.createQueue(callBackQueueName);
			push2WebProducer = session.createProducer(push2WebQueue);
			push2WebProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			Topic topic = session.createTopic(callBackQueueName);
			TextMessage textMessage = session.createTextMessage(msg);
			push2WebProducer.send(textMessage);

		}catch (Exception e) {

			e.printStackTrace();
		}
	}



}
