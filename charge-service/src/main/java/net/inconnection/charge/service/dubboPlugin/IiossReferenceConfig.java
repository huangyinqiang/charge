package net.inconnection.charge.service.dubboPlugin;

import com.alibaba.dubbo.config.ReferenceConfig;

/**
 * 简化获取服务的繁琐
 * @param <T>
 */
public class IiossReferenceConfig<T> extends ReferenceConfig<T> {

    public IiossReferenceConfig() {
       super();
    }



    public IiossReferenceConfig setServiceInterface(Class<?> interfaceClass) {
        super.setInterface(interfaceClass);
        return this;
    }

}
