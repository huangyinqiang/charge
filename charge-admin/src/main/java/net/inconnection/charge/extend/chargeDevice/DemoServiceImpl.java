package net.inconnection.charge.extend.chargeDevice;

import net.inconnection.charge.service.DemoService;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "ceshi demo service          " +name;
    }
}
