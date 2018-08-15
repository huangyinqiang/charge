package net.inconnection.charge.extend.chargeDevice.jms;
import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import net.inconnection.charge.extend.chargeDevice.deviceManage.ChargePileCommander;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jms.JMSException;

public class ActiveMQPlugin implements IPlugin {
    private static Log logger = Log.getLog(Thread.currentThread().getClass());
    private String url;
    private String name;
    public ActiveMQPlugin(String url,
                          String name) {
        this.url = url;
        this.name = name;
    }
    public ActiveMQPlugin(String url) {
        this.url = url;
        this.name = ActiveMQ.defaultName;
    }
    @Override
    public boolean start() {
        logger.info("初始化activeMQ配置");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName(ActiveMQConnection.DEFAULT_USER);
        activeMQConnectionFactory.setPassword(ActiveMQConnection.DEFAULT_PASSWORD);
        activeMQConnectionFactory.setBrokerURL(url);
        activeMQConnectionFactory.setDispatchAsync(true);//异步发送消息
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaximumActiveSessionPerConnection(200);
        pooledConnectionFactory.setIdleTimeout(120);
        pooledConnectionFactory.setMaxConnections(5);
        pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);
        try {
            PooledConnection connection = (PooledConnection) pooledConnectionFactory.createConnection();
            connection.start();
            ActiveMQ.pooledConnectionMap.put(name, connection);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public boolean stop() {
        return true;
    }

}
