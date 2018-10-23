package net.inconnection.charge.extend.chargeDevice.protocol.update;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class UpdateDevice {
    private int offset;
    private byte[] data;
    private int length;
    private int lengthAll;
    private int crc;
    private byte[] bytes;

    public UpdateDevice(byte[] buff){
        this.data = buff;
        this.lengthAll = buff.length;
        this.crc = 0;
        this.offset = 0;
    }

    public byte[] getData() {
        return data;
    }

    public int getLength() {
        return length;
    }

    public int getLengthAll() {
        return lengthAll;
    }
    public byte[] getBytes() {
        return bytes;
    }
    public int getCrc() {
        return crc;
    }

    public void analysisFile(int start){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int tem = start+3072;
//        int tem = start+15360;
        if(tem>lengthAll){
            tem = lengthAll;
        }
        int crcTem = 0;
        for (int i=start;i<tem;i++){
            crcTem ^= (char)data[i];
            out.write(data[i]);
        }
        try {
            out.flush();
            this.bytes = out.toByteArray();
            this.length = this.bytes.length;
            this.crc = crcTem;
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "UpdateDevice{" +
                "offset=" + offset +
                ", data=" + Arrays.toString(data) +
                ", length=" + length +
                ", lengthAll=" + lengthAll +
                ", crc=" + crc +
                '}';
    }

}
