package ca.utoronto.utm.mcs;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.inject.Inject;

public class ReqHandler implements HttpHandler {

    // Complete This Class
    public Neo4jDAO neo4jDAO;

    @Inject
    public ReqHandler(Neo4jDAO neo4j) {
        this.neo4jDAO = neo4j;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
//        try {
//            String method = exchange.getRequestMethod();
//        } catch (Exception e) {
//        }
    }
}