package net.inconnection.charge.service.dubboPlugin;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.jfinal.plugin.IPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static net.inconnection.charge.service.dubboPlugin.DubboConstant.DUBBOLOCALHOST;
import static net.inconnection.charge.service.dubboPlugin.DubboConstant.VERSION;


public class DubboClientPlugin implements IPlugin{

    private ApplicationConfig application;
    private RegistryConfig registry;
    private ProtocolConfig protocol;
    private String applicationName;
    private String userName;
    private String password;
    private int port;
    private Map<String,Object> serviceNameAndImpl;

    public DubboClientPlugin(String applicationName, int port) {
        this.applicationName = applicationName;
        this.port = port;
        this.serviceNameAndImpl=new HashMap<>();
        applicationConfig(applicationName);
    }

    /**
     * charge-admin-service
     * @param applicationName 设置应用名称
     */
    public void applicationConfig(String applicationName) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        this.application=applicationConfig;
    }

    /**
     *
     * @param <T>
     * @return
     */
    public <T>T getService(ReferenceConfig<T> reference){
        reference.setTimeout(60000);
        reference.setCheck(false);
        reference.setRetries(0);//不进行尝试
        reference.setApplication(application);
        reference.setVersion(VERSION);
        reference.setUrl(DUBBOLOCALHOST+":"+port);
        T t = reference.get();
        serviceNameAndImpl.put(reference.getInterface(),t);
        return  reference.get();
    }


    @Override
    public boolean start() {
        Set<String> strings = serviceNameAndImpl.keySet();
        for (String str:strings){
            DubboServiceContrain.getInstance().addServiceNameAndImpl(str,serviceNameAndImpl.get(str));
        }
        return true;
    }

    @Override
    public boolean stop() {
        DubboServiceContrain.getInstance().stop();
        return true;
    }
}
