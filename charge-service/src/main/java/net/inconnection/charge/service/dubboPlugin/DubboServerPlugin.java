package net.inconnection.charge.service.dubboPlugin;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.jfinal.plugin.IPlugin;

import java.util.ArrayList;
import java.util.List;

import static net.inconnection.charge.service.dubboPlugin.DubboConstant.*;


public class DubboServerPlugin implements IPlugin {

    private ApplicationConfig application;
    private RegistryConfig registry;
    private ProtocolConfig protocol;
    private String applicationName;
    private String userName;
    private String password;
    private int port;
    private List<ServiceConfig> serviceConfigList;

    public DubboServerPlugin(String applicationName, int port) {
        this.applicationName = applicationName;
        this.port = port;
        this.serviceConfigList=new ArrayList<>();
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
     * 注册中心
     */
    public  void setRegist(){
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(ADDRESS);
        this.registry=registry;
    }


    /**
     * 暴露服务的端口号
     * @param port
     */
    public void setProtocol(int port){
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName(DUBBONAME);
        protocol.setPort(port);
        protocol.setThreads(Integer.valueOf(threadNumber));
        this.protocol=protocol;
    }


    /**
     *
     * @param serviceClass  接口的class
     * @param implService   实现类
     */
    @SuppressWarnings("unchecked")
    public void addService(Class serviceClass,Object implService){
        ServiceConfig service = new ServiceConfig<>();
        service.setInterface(serviceClass);
        service.setRef(implService);
        service.setVersion(VERSION);
        serviceConfigList.add(service);
    }


    @Override
    public boolean start() {
        applicationConfig( applicationName);
        setRegist();
        setProtocol( port);


        for (ServiceConfig service:this.serviceConfigList){
            service.setApplication(application);
            service.setRegistry(registry);
            service.setProtocol(protocol);
            service.export();
        }
        return true;
    }

    @Override
    public boolean stop() {
        return false;
    }
}
