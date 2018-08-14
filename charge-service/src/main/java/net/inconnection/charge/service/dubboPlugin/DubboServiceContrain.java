package net.inconnection.charge.service.dubboPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * 对外提供添加服务和获取服务
 */
public class DubboServiceContrain {

    private Map<String,Object> serviceNameAndImpl;
    private volatile static DubboServiceContrain dubboServiceContrain;

    public static DubboServiceContrain getInstance(){
        if(dubboServiceContrain==null){
            synchronized (DubboServiceContrain.class){
                if (dubboServiceContrain==null){
                    dubboServiceContrain=new DubboServiceContrain();
                }
            }
        }
        return dubboServiceContrain;
    }

    /**
     * 提供从容器中获取service
     * @param serviceClass
     * @param <T>
     * @return
     */
    public <T>T getService(Class<T> serviceClass) {
        //return serviceNameAndImpl;
        String simpleName = serviceClass.getName();
        T t = (T)serviceNameAndImpl.get(simpleName);
        return t;
    }

    public void addServiceNameAndImpl(String serviceName, Object serviceImpl) {
        if (serviceNameAndImpl==null){
           this.serviceNameAndImpl= new HashMap<>();
        }
        if (!serviceNameAndImpl.containsKey(serviceName)){
            this.serviceNameAndImpl.put(serviceName,serviceImpl);
        }
    }


     void stop(){
        this.serviceNameAndImpl= new HashMap<>();
    }

}
