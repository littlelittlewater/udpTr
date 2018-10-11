package net;

import model.Data;
import model.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProcessorService  implements Runnable {

    NetworkService networkService = null;
    public ProcessorService(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public void run() {
        BlockingQueue<Message> req = networkService.requests;
        while (true) {
            Message message  = null;
            try {
                message = req.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler(message);
        }
    }

    private void handler(Message message) {
        BlockingQueue<Message> res = networkService.responses;
        try {
            Thread.sleep(1000);
            res.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
