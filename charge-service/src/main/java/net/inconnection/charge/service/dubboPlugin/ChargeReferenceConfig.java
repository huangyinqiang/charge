package net.inconnection.charge.service.dubboPlugin;

import com.alibaba.dubbo.config.ReferenceConfig;

/**
 * 简化获取服务的繁琐
 * @param <T>
 */
public class ChargeReferenceConfig<T> extends ReferenceConfig<T> {

    public ChargeReferenceConfig() {
       super();
    }



    public ChargeReferenceConfig setServiceInterface(Class<?> interfaceClass) {
        super.setInterface(interfaceClass);
        return this;
    }

}
