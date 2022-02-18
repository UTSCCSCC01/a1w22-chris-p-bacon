package ca.utoronto.utm.mcs;

import java.io.IOException;

public class App
{
    static int port = 8080;

    public static void main(String[] args) throws IOException {
        // Create Your Server Context Here, There Should Only Be One Context
        Server server = DaggerServerComponent.create().buildServer();
        ReqHandler reqHandler = DaggerReqHandlerComponent.create().buildHandler();
        server.server.createContext("/api/v1/", reqHandler);

        server.server.start();

        System.out.printf("Server started on port %d\n", port);
    }
}
