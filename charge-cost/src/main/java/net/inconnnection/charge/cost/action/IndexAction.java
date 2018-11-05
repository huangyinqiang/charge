package net.inconnnection.charge.cost.action;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import net.inconnnection.charge.cost.model.Result;
import net.inconnnection.charge.cost.plugin.ActiveMQ;
import net.inconnnection.charge.cost.plugin.Destination;
import net.inconnnection.charge.cost.plugin.JmsReceiver;

import javax.jms.JMSException;

public class IndexAction  extends Controller {

    private static String flag = "yes";

    public IndexAction() {
    }

    public void index() {

        this.setAttr("flag", flag);
        this.renderJsp("index.jsp");
    }

    public void aaa() {
        Result result = new Result();
        result.setTitle("aa");
        result.setStatus(1);
        result.setResult("order is cost 1 do.");
        result.setMsg("数据依据完成");
        this.renderJson(result);
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
