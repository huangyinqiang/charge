package net.inconnection.charge.log.action;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import net.inconnection.charge.log.plugin.ActiveMQ;
import net.inconnection.charge.log.plugin.Destination;
import net.inconnection.charge.log.plugin.JmsReceiver;

import javax.jms.JMSException;

public class IndexAction extends Controller {
    private static String flag = "no";

    public IndexAction() {
    }

    public void index() {
        this.setAttr("flag", flag);
        this.renderJsp("index.jsp");
    }

    public void receive() {
        try {
            ActiveMQ.addReceiver(new JmsReceiver("testReceiver1", ActiveMQ.getConnection(), Destination.Queue, PropKit.get("mqsubject")));
            flag = "yes";
        } catch (JMSException var2) {
            this.renderText("err..." + var2.getMessage());
        }

        this.renderText("open recever ok...");
    }

    public void stopreceive() {
        ActiveMQ.getReceiver("testReceiver1").close();
        flag = "no";
        this.renderText("close receiver ok...");
    }
}

