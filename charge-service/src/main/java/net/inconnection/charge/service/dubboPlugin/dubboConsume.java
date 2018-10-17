package net.inconnection.charge.service.dubboPlugin;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import net.inconnection.charge.service.DeviceControlService;

/**
 * 测试使用类
 */
public class dubboConsume {



    public static void main(String[] args) {




        ReferenceConfig<DeviceControlService> reference = new ReferenceConfig<DeviceControlService>();
        reference.setInterface(DeviceControlService.class);





    }

    public static  <T>T getService(ReferenceConfig<T> reference){

        ApplicationConfig context = new ApplicationConfig();
        context.setName("FUWU");

        reference.setApplication(context);
        reference.setVersion("1.0.0");
        reference.setUrl("dubbo://127.0.0.1:20882");


        // reference.setRegistry(reg);



        return  reference.get();

    }


}
