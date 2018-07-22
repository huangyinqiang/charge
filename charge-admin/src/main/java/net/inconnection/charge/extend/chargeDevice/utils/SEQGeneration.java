package net.inconnection.charge.extend.chargeDevice.utils;


/**
 * 来唯一的生成发送消息的时候的序列号,保护序列号的唯一性.
 */
public class SEQGeneration {
    private int total = 0;
    private static SEQGeneration SEQGeneration = null;
    private SEQGeneration(){
    }
    public static SEQGeneration getInstance(){
        if(SEQGeneration == null){
            SEQGeneration = new SEQGeneration();
        }else{
        }
        return SEQGeneration;
    }

    //写一个方法来生成序列号
    public synchronized Integer getSEQ() {
        this.total++;
        if (this.total > 65535) {//表示的是int的16位
            this.total = 1;//如果到达最大值,则重新开始
            return total;
        }
        return total;
    }
}
