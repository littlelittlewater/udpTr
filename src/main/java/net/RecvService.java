package net;

public class RecvService implements Runnable{
    private final NetworkService networkService;

    public int getIndex() {
        return index;
    }

    private final int index;

    public RecvService(NetworkService networkService, int i) {
        this.networkService = networkService;
        this.index = i ;
    }

    @Override
    public void run() {
        networkService.onRecvThread(this);
    }
}
