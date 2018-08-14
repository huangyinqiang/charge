package net.inconnection.charge.service.dubboPlugin;

import com.alibaba.dubbo.config.ReferenceConfig;

public class IiossReferenceConfig<T> extends ReferenceConfig<T> {

    public IiossReferenceConfig() {
       super();
    }



    public IiossReferenceConfig setServiceInterface(Class<?> interfaceClass) {
        super.setInterface(interfaceClass);
        return this;
    }

}
