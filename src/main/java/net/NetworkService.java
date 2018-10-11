package net;


import model.Data;
import model.Message;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkService {
    List<SendService> sendServices = new ArrayList<>();
    List<RecvService> recvServices = new ArrayList<>();
    List<DatagramSocket> sockets = new ArrayList<>();
    BlockingQueue<Message> requests = new LinkedBlockingQueue<>();
    BlockingQueue<Message> responses = new LinkedBlockingQueue<>();
    /**
     * 初始化
     **/
    public boolean begin() {
        /**创建udp监控**/
        for (int port : Constant.POINT) {
            try {
                DatagramSocket socket = new DatagramSocket(port);
                /**初始化先关数据**/
                sockets.add(socket);
                sendServices.add(new SendService(this, sockets.size() - 1));
                recvServices.add(new RecvService(this, sockets.size() - 1));

            } catch (IOException e) {
                e.printStackTrace();
            } }
        /**启动**/
        for (SendService s : sendServices) {
            new Thread(s).start();
        }
        for (RecvService r : recvServices) {
            new Thread(r).start();
        }
        /**启动处理线程**/
        for(int i= 0 ; i< Constant.ProcessorNum ; i++){
            new Thread(new ProcessorService(this)).start();
        }

        return true;
    }

    public void onRecvThread(RecvService r) {
        while (true) {
            DatagramSocket socket = sockets.get(r.getIndex());
            byte[] recived = new byte[1024];
            DatagramPacket request = new DatagramPacket(recived, 1024);
            try {
                socket.receive(request);
                int point = request.getPort();
                InetAddress inter = request.getAddress();
                Data d = new Data(recived);
                if(!d.isvaild()){
                    System.out.println(d.getSrc());
                    requests.put(new Message(d,point,inter));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void onSendThread(SendService s) {
        InetAddress host = null;
        try {
            host = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        while (true) {
            DatagramSocket socket = sockets.get(s.getIndex());
            Message sendM = null;
            try {
                sendM = responses.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] send = sendM.getD().getBytes();
            DatagramPacket request = new DatagramPacket(send,send.length,sendM.getInter(),sendM.getPoint());
            try {
                socket.send(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
