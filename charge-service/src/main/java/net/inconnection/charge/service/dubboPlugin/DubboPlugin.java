package net.inconnection.charge.service.dubboPlugin;


import com.alibaba.dubbo.config.*;
import com.jfinal.plugin.IPlugin;

import java.util.ArrayList;
import java.util.List;

public class DubboPlugin  implements IPlugin {


   private static final String ADDRESS="N/A";
    private static final String VERSION="1.0.0";
    private static  int threadNumber=200;
    private static final String DUBBONAME="dubbo";

    private ApplicationConfig application;
    private RegistryConfig registry;
    private ProtocolConfig protocol;
    private String applicationName;
    private String userName;
    private String password;
    private int port;
    private List<ServiceConfig> serviceConfigList;


    public DubboPlugin(String applicationName, int port) {
        this.applicationName = applicationName;
        this.port = port;
        this.serviceConfigList=new ArrayList<>();
    }


    public DubboPlugin(String applicationName, int port, int threadNumber) {
        this.applicationName = applicationName;
        this.port = port;
        this.threadNumber = threadNumber;
        applicationConfig( applicationName);
        setRegist();
        setProtocol( port,threadNumber);
        this.serviceConfigList=new ArrayList<ServiceConfig>();
    }

    public DubboPlugin(String applicationName, String userName, String password, int port, int threadNumber) {
        this.applicationName = applicationName;
        this.userName = userName;
        this.password = password;
        this.port = port;
        this.threadNumber = threadNumber;
        this.serviceConfigList=new ArrayList<ServiceConfig>();
    }

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

    public boolean stop() {
        return true;
    }

    public void applicationConfig(String applicationName) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        this.application=applicationConfig;
    }

    public  void setRegist(String name,String password){
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(ADDRESS);
        registry.setUsername(name);
        registry.setPassword(password);
        this.registry=registry;
    }

    public  void setRegist(){
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(ADDRESS);
        this.registry=registry;
    }

    public void setProtocol(int port,int threads){
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setPort(port);
        protocol.setThreads(threads);
        this.protocol=protocol;
    }


    public void setProtocol(int port){
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName(DUBBONAME);
        protocol.setPort(port);
        protocol.setThreads(threadNumber);
        this.protocol=protocol;
    }


    public  void addService(Class serviceClass,Object implService) {
        ServiceConfig service = new ServiceConfig<>();
        service.setInterface(serviceClass);
        service.setRef(implService);
        service.setVersion(VERSION);
        serviceConfigList.add(service);
    }


    public <T>T getService(ReferenceConfig<T> reference, String url){
        reference.setApplication(application);
        reference.setInterface(reference.getInterfaceClass());
        reference.setVersion(VERSION);
        reference.setUrl(url);
        return reference.get();
    }


}
