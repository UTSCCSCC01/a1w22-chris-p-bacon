package ca.utoronto.utm.mcs;

import com.sun.net.httpserver.HttpServer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Server {
    // TODO Complete This Class

    public HttpServer server;

    @Inject
    public Server(HttpServer server) {
        this.server = server;
    }
}
