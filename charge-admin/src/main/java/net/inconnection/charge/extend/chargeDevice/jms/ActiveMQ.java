package net.inconnection.charge.extend.chargeDevice.jms;
import org.apache.activemq.pool.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;

public class ActiveMQ {
    public static final ConcurrentHashMap<String, PooledConnection> pooledConnectionMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, JmsSender> senderMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, JmsReceiver> receiverMap = new ConcurrentHashMap<>();
    public static final String defaultName = "main";
    private static final Logger logger = LoggerFactory.getLogger(Thread.currentThread().getClass());
    public static void addSender(JmsSender sender) {
        senderMap.put(sender.getName(), sender);
    }
    public static JmsSender getSender(String name) {
        return senderMap.get(name);
    }
    public static void addReceiver(JmsReceiver receiver) {
        receiverMap.put(receiver.getName(), receiver);
    }
    public static JmsReceiver getReceiver(String name) {
        return receiverMap.get(name);
    }
    public static void addConnection(String connectionName,PooledConnection connection) {
        pooledConnectionMap.put(connectionName, connection);
    }
    public static PooledConnection getConnection() {
        return pooledConnectionMap.get(defaultName);
    }
    public static PooledConnection getConnection(String connectionName) {
        return pooledConnectionMap.get(connectionName);
    }
}
