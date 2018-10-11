package model;

import java.net.InetAddress;

public class Message {
    public void setInter(InetAddress inter) {
        this.inter = inter;
    }

    private  InetAddress inter;
    Data d ;

    public Message(Data d, int point, InetAddress inter) {
        this.d = d;
        this.point =point;
        this.inter = inter;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    int  point;
    String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Data getD() {
        return d;
    }

    public void setD(Data d) {
        this.d = d;
    }

    public Message(Data d) {
        this.d = d;
    }

    public InetAddress getInter() {
        return inter;
    }
}
