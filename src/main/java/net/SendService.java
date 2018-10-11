package net;

public class SendService implements Runnable{
    private final NetworkService networkService;
    private final int index;
    public int getIndex() {
        return index;
    }
    public SendService(NetworkService networkService, int i) {
        this.networkService = networkService;
        this.index = i ;
    }

    @Override
    public void run() {
        networkService.onSendThread(this);
    }
}
