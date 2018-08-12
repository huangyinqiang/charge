package net.inconnection.charge.log.plugin;

import com.jfinal.plugin.IPlugin;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

public class ActiveMQPlugin implements IPlugin {
    private static final Logger logger = LoggerFactory.getLogger(Thread.currentThread().getClass());
    private String url;
    private String name;
    private PooledConnection connection;

    public ActiveMQPlugin(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public ActiveMQPlugin(String url) {
        this.url = url;
        this.name = "main";
    }

    public boolean start() {
        logger.info("初始化activeMQ配置");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName(ActiveMQConnection.DEFAULT_USER);
        activeMQConnectionFactory.setPassword(ActiveMQConnection.DEFAULT_PASSWORD);
        activeMQConnectionFactory.setBrokerURL(this.url);
        activeMQConnectionFactory.setDispatchAsync(true);
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
        pooledConnectionFactory.setMaximumActiveSessionPerConnection(200);
        pooledConnectionFactory.setIdleTimeout(120);
        pooledConnectionFactory.setMaxConnections(5);
        pooledConnectionFactory.setBlockIfSessionPoolIsFull(true);

        try {
            this.connection = (PooledConnection)pooledConnectionFactory.createConnection();
            this.connection.start();
            ActiveMQ.pooledConnectionMap.put(this.name, this.connection);
        } catch (JMSException var4) {
            System.out.println(var4.getMessage());
        }

        return true;
    }

    public boolean stop() {
        if (this.connection != null) {
            try {
                this.connection.close();
                return true;
            } catch (JMSException var2) {
                System.out.println(var2.getMessage());
            }
        }

        return false;
    }
}

