package client;

import model.Data;
import net.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Send implements Runnable{
    static int[] SendPoint = new int[]{20001,20002,20003,20004,20005,20006,20007,20008,20009,20010};
    private final int point;

    public Send(int point) {
        this.point = point;
    }

    public static void main(String[] args) {
        //传入0表示让操作系统分配一个端口号
        for(int point : SendPoint){
            new Thread(new Send(point)).start();
        }

    }

    @Override
    public void run() {
        int i = 0;
        while (true){
            i++;
                try (DatagramSocket socket = new DatagramSocket(point)) {
                    socket.setSoTimeout(100000);
                    InetAddress host = InetAddress.getByName("localhost");
                    //为接受的数据包创建空间
                    Data d = new Data(i % 256 ,"point:"+point+",message:" +i);
                    //指定包要发送的目的地
                    DatagramPacket request = new DatagramPacket(d.getBytes(), d.getBytes().length, host, Constant.POINT[0]);
                    byte[] result = new byte[1024];
                    DatagramPacket response = new DatagramPacket(result, result.length);
                    socket.send(request);
                    socket.receive(response);
                    System.out.println(new Data(result).getSrc());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}
