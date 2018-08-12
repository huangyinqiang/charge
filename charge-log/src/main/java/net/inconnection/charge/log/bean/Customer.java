package net.inconnection.charge.log.bean;

import com.jfinal.plugin.activerecord.Model;

public class Customer extends Model<Customer> {
    private static final long serialVersionUID = 1L;
    public static final Customer me = new Customer();

    public Customer() {
    }
}
