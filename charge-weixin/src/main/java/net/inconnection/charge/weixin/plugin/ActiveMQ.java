package net.inconnection.charge.weixin.plugin;

import org.apache.activemq.pool.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class ActiveMQ {
    public static final ConcurrentHashMap<String, PooledConnection> pooledConnectionMap = new ConcurrentHashMap();
    public static final ConcurrentHashMap<String, JmsSender> senderMap = new ConcurrentHashMap();
    public static final String defaultName = "main";
    private static final Logger logger = LoggerFactory.getLogger(Thread.currentThread().getClass());

    public ActiveMQ() {
    }

    public static void addSender(JmsSender sender) {
        senderMap.put(sender.getName(), sender);
    }

    public static JmsSender getSender(String name) {
        return (JmsSender)senderMap.get(name);
    }

    public static void removeSender(String sender) {
        if (senderMap.get(sender) != null) {
            senderMap.remove(sender);
        }

    }

    public static void addConnection(String connectionName, PooledConnection connection) {
        pooledConnectionMap.put(connectionName, connection);
    }

    public static PooledConnection getConnection() {
        return (PooledConnection)pooledConnectionMap.get("main");
    }

    public static PooledConnection getConnection(String connectionName) {
        return (PooledConnection)pooledConnectionMap.get(connectionName);
    }
}

