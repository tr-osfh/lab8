package connection;

import collection.ServerLogger;
import commands.CommandSerializer;
import seClasses.Dragon;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class Refresher {
    private static final ResponseSender responseSender = new ResponseSender();
    private static PriorityBlockingQueue<Dragon> dragons = new PriorityBlockingQueue<>();

    public static void refresh() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Socket> connectedUsers = Server.getConnectedUsers();

            Response response = new Response(ResponseStatus.REFRESH, dragons);
        byte[] serializedResponse = CommandSerializer.serialize(response);

        for (Socket clientSocket : connectedUsers) {
            try {
                if (clientSocket.isConnected() && !clientSocket.isClosed()) {
                    OutputStream output = clientSocket.getOutputStream();
                    responseSender.sendResponse(output, serializedResponse);
                }
            } catch (IOException e) {
                ServerLogger.getLogger().warning("Клиент" + clientSocket.getRemoteSocketAddress() + "не получил обновление");
            }
        }
    }

    public static void refresh(PriorityBlockingQueue<Dragon> dragons, Socket clientSocket) {
        Response response = new Response(ResponseStatus.REFRESH, dragons);
        byte[] serializedResponse = CommandSerializer.serialize(response);
        OutputStream output = null;
        try {
            output = clientSocket.getOutputStream();
        } catch (IOException e) {
            ServerLogger.getLogger().warning("Клиент" + clientSocket.getRemoteSocketAddress() + "не получил обновление");
        }
        responseSender.sendResponse(output, serializedResponse);
    }

    public static void setDragons(PriorityBlockingQueue<Dragon> dragons) {
        Refresher.dragons = dragons;
    }
}