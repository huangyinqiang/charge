package net.inconnection.charge.service.dubboPlugin;

public class Service<T> {

    private Class<T> clazz;

    public Service(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
