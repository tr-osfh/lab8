package connection;

import collection.ServerLogger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResponseSender {
    private final ExecutorService responsePool;


    public ResponseSender() {
        this.responsePool = Executors.newCachedThreadPool();
    }

    public void sendResponse(OutputStream output, byte[] responseData) {
        responsePool.execute(() -> {
            try {
                output.write(responseData);
                output.flush();
            } catch (IOException e) {
                ServerLogger.getLogger().warning("Ошибка отправки ответа.");
            }
        });
    }
    public void shutdown(){
        responsePool.shutdown();
    }
}
